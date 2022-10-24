/*
 *
 *  @author Sipek
 *
 */

package com.graph.unicorndemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.graph.unicorndemo.dto.GraphDto;
import com.graph.unicorndemo.dto.PathDto;
import com.graph.unicorndemo.exception.GraphException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphHandlerTest
{

    private GraphHandler graphHandler;

    @BeforeEach
    void setUp()
    {
        graphHandler = new GraphHandler();
    }

    @Test
    void initGraph_Ok()
    {
        GraphDto graphDto = getSimpleGraph_Valid();
        Assertions.assertDoesNotThrow(() -> graphHandler.initGraph(graphDto));
    }

    @Test
    void initGraph_ValidationError_ZeroGraph()
    {
        GraphDto zeroGraphDto = new GraphDto();
        assertThrows(GraphException.class, () -> graphHandler.initGraph(zeroGraphDto));

        List<List<Integer>> vertexes = new ArrayList<>();
        zeroGraphDto.setGraph(vertexes);
        assertThrows(GraphException.class, () -> graphHandler.initGraph(zeroGraphDto));

        ArrayList<Integer> vertexA = new ArrayList<>();
        vertexes.add(vertexA);
        assertThrows(GraphException.class, () -> graphHandler.initGraph(zeroGraphDto));
    }

    @Test
    void initGraph_ValidationError_Invalid_MatrixDimension()
    {
        GraphDto graphDto = getSimpleGraph_Invalid_MatrixDimension();
        GraphDto finalGraphDto = graphDto;
        assertThrows(GraphException.class, () -> graphHandler.initGraph(finalGraphDto));
    }

    @Test
    void initGraph_ValidationError_Invalid_NegativeValue()
    {
        GraphDto graphDto = getSimpleGraph_Invalid_NegativeValue();
        GraphDto finalGraphDto = graphDto;
        assertThrows(GraphException.class, () -> graphHandler.initGraph(finalGraphDto));
    }

    @Test
    void getGraphPath_SimpleGraph_Invalid_TargetNodes()
    {
        GraphDto graphDto = getSimpleGraph_Valid();

        graphHandler.initGraph(graphDto);
        assertThrows(GraphException.class, () -> graphHandler.getGraphPath(-1));
        assertThrows(GraphException.class, () -> graphHandler.getGraphPath(3));
    }

    @Test
    void getGraphPath_SimpleGraph_TargetNode_0()
    {
        GraphDto graphDto = getSimpleGraph_Valid();

        graphHandler.initGraph(graphDto);
        PathDto pathDto = graphHandler.getGraphPath(0);
        assertEquals(0, pathDto.getDistance());
        assertNotNull(pathDto.getPath());
        assertEquals(0,
                     pathDto.getPath()
                             .size());
    }

    @Test
    void getGraphPath_SimpleGraph_TargetNode_1()
    {
        GraphDto graphDto = getSimpleGraph_Valid();

        graphHandler.initGraph(graphDto);
        PathDto pathDto = graphHandler.getGraphPath(1);
        assertEquals(3, pathDto.getDistance());
        assertNotNull(pathDto.getPath());
        assertEquals(2,
                     pathDto.getPath()
                             .size());
        assertEquals(0,
                     pathDto.getPath()
                             .get(0));
        assertEquals(2,
                     pathDto.getPath()
                             .get(1));
    }

    @Test
    void getGraphPath_SimpleGraph_TargetNode_2()
    {
        GraphDto graphDto = getSimpleGraph_Valid();

        graphHandler.initGraph(graphDto);
        PathDto pathDto = graphHandler.getGraphPath(2);
        assertEquals(2, pathDto.getDistance());
        assertNotNull(pathDto.getPath());
        assertEquals(1,
                     pathDto.getPath()
                             .size());
        assertEquals(0,
                     pathDto.getPath()
                             .get(0));
    }

    @Test
    void getGraphPath_AdvancedGraph_TargetNode_0()
    {
        GraphDto graphDto = getAdvancedGraph_Valid();

        graphHandler.initGraph(graphDto);
        PathDto pathDto = graphHandler.getGraphPath(0);
        assertNotNull(pathDto);
        assertEquals(0, pathDto.getDistance());
        assertEquals(0,
                     pathDto.getPath()
                             .size());
    }

    @Test
    void getGraphPath_AdvancedGraph_TargetNode_1()
    {
        GraphDto graphDto = getAdvancedGraph_Valid();

        graphHandler.initGraph(graphDto);
        PathDto pathDto = graphHandler.getGraphPath(1);
        assertNotNull(pathDto);
        assertEquals(3, pathDto.getDistance());
        assertEquals(2,
                     pathDto.getPath()
                             .size());
        assertEquals(0,
                     pathDto.getPath()
                             .get(0));
        assertEquals(3,
                     pathDto.getPath()
                             .get(1));
    }

    @Test
    void getGraphPath_AdvancedGraph_TargetNode_2()
    {
        GraphDto graphDto = getAdvancedGraph_Valid();

        graphHandler.initGraph(graphDto);
        PathDto pathDto = graphHandler.getGraphPath(2);
        assertNotNull(pathDto);
        assertEquals(7, pathDto.getDistance());
        assertEquals(3,
                     pathDto.getPath()
                             .size());
        assertEquals(0,
                     pathDto.getPath()
                             .get(0));
        assertEquals(3,
                     pathDto.getPath()
                             .get(1));
        assertEquals(4,
                     pathDto.getPath()
                             .get(2));
    }

    @Test
    void getGraphPath_AdvancedGraph_TargetNode_3()
    {
        GraphDto graphDto = getAdvancedGraph_Valid();

        graphHandler.initGraph(graphDto);
        PathDto pathDto = graphHandler.getGraphPath(3);
        assertNotNull(pathDto);
        assertEquals(1, pathDto.getDistance());
        assertEquals(1,
                     pathDto.getPath()
                             .size());
        assertEquals(0,
                     pathDto.getPath()
                             .get(0));
    }

    private GraphDto getAdvancedGraph_Valid()
    {
        GraphDto graphDto = new GraphDto();
        List<List<Integer>> vertexes = new ArrayList<>();

        ArrayList<Integer> vertexA = new ArrayList<>();
        vertexA.add(0);
        vertexA.add(6);
        vertexA.add(0);
        vertexA.add(1);
        vertexA.add(0);
        vertexes.add(vertexA);

        ArrayList<Integer> vertexB = new ArrayList<>();
        vertexB.add(6);
        vertexB.add(0);
        vertexB.add(5);
        vertexB.add(2);
        vertexB.add(2);
        vertexes.add(vertexB);

        ArrayList<Integer> vertexC = new ArrayList<>();
        vertexC.add(0);
        vertexC.add(5);
        vertexC.add(0);
        vertexC.add(0);
        vertexC.add(5);
        vertexes.add(vertexC);

        ArrayList<Integer> vertexD = new ArrayList<>();
        vertexD.add(1);
        vertexD.add(2);
        vertexD.add(0);
        vertexD.add(0);
        vertexD.add(1);
        vertexes.add(vertexD);

        ArrayList<Integer> vertexE = new ArrayList<>();
        vertexE.add(0);
        vertexE.add(2);
        vertexE.add(5);
        vertexE.add(1);
        vertexE.add(0);
        vertexes.add(vertexE);

        graphDto.setGraph(vertexes);
        return graphDto;
    }

    private GraphDto getSimpleGraph_Valid()
    {
        GraphDto graphDto = new GraphDto();
        List<List<Integer>> vertexes = new ArrayList<>();
        ArrayList<Integer> vertexA = new ArrayList<>();
        vertexA.add(0);
        vertexA.add(4);
        vertexA.add(2);

        vertexes.add(vertexA);

        ArrayList<Integer> vertexB = new ArrayList<>();
        vertexB.add(4);
        vertexB.add(0);
        vertexB.add(1);
        vertexes.add(vertexB);

        ArrayList<Integer> vertexC = new ArrayList<>();
        vertexC.add(2);
        vertexC.add(1);
        vertexC.add(0);
        vertexes.add(vertexC);

        graphDto.setGraph(vertexes);
        return graphDto;
    }

    private GraphDto getSimpleGraph_Invalid_NegativeValue()
    {
        GraphDto graphDto = new GraphDto();
        List<List<Integer>> vertexes = new ArrayList<>();
        ArrayList<Integer> vertexA = new ArrayList<>();
        vertexA.add(0);
        vertexA.add(4);
        vertexA.add(2);

        vertexes.add(vertexA);

        ArrayList<Integer> vertexB = new ArrayList<>();
        vertexB.add(4);
        vertexB.add(0);
        vertexB.add(-1);
        vertexes.add(vertexB);

        ArrayList<Integer> vertexC = new ArrayList<>();
        vertexC.add(2);
        vertexC.add(1);
        vertexC.add(0);
        vertexes.add(vertexC);

        graphDto.setGraph(vertexes);
        return graphDto;
    }

    private GraphDto getSimpleGraph_Invalid_MatrixDimension()
    {
        GraphDto graphDto = new GraphDto();
        List<List<Integer>> vertexes = new ArrayList<>();
        ArrayList<Integer> vertexA = new ArrayList<>();
        vertexA.add(0);
        vertexA.add(4);
        vertexA.add(2);

        vertexes.add(vertexA);

        ArrayList<Integer> vertexB = new ArrayList<>();
        vertexB.add(4);
        vertexB.add(0);
        vertexB.add(1);
        vertexes.add(vertexB);

        graphDto.setGraph(vertexes);
        return graphDto;
    }
}