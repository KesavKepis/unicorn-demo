/*
 *
 *  @author Sipek
 *
 */

package com.graph.unicorndemo;

import com.graph.unicorndemo.dto.GraphDto;
import com.graph.unicorndemo.dto.PathDto;
import com.graph.unicorndemo.exception.GraphException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphHandler
{
    private static final int PATH_NOT_DEFINED = 0;
    private static final int NODE_NO_PARENT = -1; // NODE 'from' has no parent

    private static final int START_NODE = 0;

    private GraphCache graphCache = GraphCache.getInstance();

    protected void initGraph(GraphDto graph)
    {
        validateGraph(graph);
        graphCache.setGraphDto(graph);
    }

    protected PathDto getGraphPath(Integer targetNode)
    {
        GraphDto graphDto = graphCache.getGraphDto();

        if ( targetNode == null )
        {
            throw new GraphException("Validation error - Paremeter targetNode not defined.");
        }

        if ( graphDto == null )
        {
            throw new GraphException("Validation error - Graph is not defined. Please send valid graph definition.");
        }

        if ( targetNode < PATH_NOT_DEFINED || graphDto.getGraph()
                .size() - 1 < targetNode )
        {
            throw new GraphException(String.format(
                    "Validation error - Parameter targetNode is not defined in the graph. Count of the nodes in the graph is %d.",
                    graphDto.getGraph()
                            .size()));
        }
        return calculatePath(graphDto, START_NODE, targetNode);
    }

    /**
     *
     */
    private PathDto calculatePath(GraphDto graphDto, int from, int targetNode)
    {

        int[] shortestDistances = new int[graphDto.getGraph()
                .size()];
        boolean[] added = new boolean[graphDto.getGraph()
                .size()];
        int[] parents = new int[graphDto.getGraph()
                .size()];

        // initialization
        // of array with distances not yet processed -> Integer.MAX_VALUE
        // and array with added vertexes -> false
        for ( int vertexIndex = 0; vertexIndex < graphDto.getGraph()
                .size(); vertexIndex++ )
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // set defaults for vertex 'from'
        // distance from is always 0
        // starting vertex has no parent
        shortestDistances[from] = 0;
        parents[from] = NODE_NO_PARENT;

        // process all (not processed) nodes
        for ( int i = 1; i < graphDto.getGraph()
                .size(); i++ )
        {

            // process the vertex with minimum distance
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for ( int vertexIndex = 0; vertexIndex < graphDto.getGraph()
                    .size(); vertexIndex++ )
            {

                if ( !added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance )
                {
                    // vertex has not been processed yet
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];

                }
            }

            added[nearestVertex] = true;    // mark vertex as processed

            // update of the distance
            for ( int vertexIndex = 0; vertexIndex < graphDto.getGraph()
                    .size(); vertexIndex++ )
            {
                int edgeDistance = graphDto.getGraph()
                        .get(nearestVertex)
                        .get(vertexIndex);

                if ( edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex]) )
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }

        }
        return extractPathDtoForResponse(targetNode, shortestDistances, parents);
    }

    private PathDto extractPathDtoForResponse(Integer targetNode, int[] distances, int[] parents)
    {
        PathDto pathDto = new PathDto();
        pathDto.setDistance(distances[targetNode]);
        pathDto.setPath(getPathForTarget(parents, targetNode));
        return pathDto;
    }

    private List<Integer> getPathForTarget(int[] parents, Integer targetNode)
    {
        List<Integer> path = new ArrayList<>();

        Integer previousNode = targetNode;
        boolean previousNodeExists = true;
        while ( previousNodeExists )
        {

            if ( previousNode < parents.length && previousNode > 0 )
            {
                path.add(parents[previousNode]);
                previousNode = parents[previousNode];
                previousNodeExists = true;
            }
            else
            {
                previousNodeExists = false;
            }
        }
        Collections.reverse(path);
        return path;
    }

    private void validateGraph(GraphDto graph)
    {

        if ( graph == null || graph.getGraph()
                .isEmpty() )
        {
            throw new GraphException("Validation error - Graph is null or empty. Please send valid graph definition.");
        }

        if ( !graph.getGraph()
                .stream()
                .allMatch(s -> s.size() == graph.getGraph()
                        .size()) )
        {
            throw new GraphException("Validation error - Graph dimensions differs. Please send valid graph definition.");
        }

        if ( graph.getGraph()
                .stream()
                .anyMatch(s -> s.stream()
                        .anyMatch(item -> (item == null || item < 0))) )
        {
            throw new GraphException(
                    "Validation error - Graph contains null or negative value(s). Please send valid graph definition.");
        }
    }
}
