/*
 *
 *  @author Sipek
 *
 */

package com.graph.unicorndemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class GraphDto
{
    @JsonProperty("graph")
    List<List<Integer>> graph = new ArrayList<>();

    public List<List<Integer>> getGraph()
    {
        return graph;
    }

    public void setGraph(List<List<Integer>> graph)
    {
        this.graph = graph;
    }
}
