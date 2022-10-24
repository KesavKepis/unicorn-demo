/*
 *
 *  @author Sipek
 *
 */

package com.graph.unicorndemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class PathDto
{
    @JsonProperty("distance")
    Integer distance;

    @JsonProperty("path")
    List<Integer> path = new ArrayList<>();

    public Integer getDistance()
    {
        return distance;
    }

    public void setDistance(Integer distance)
    {
        this.distance = distance;
    }

    public List<Integer> getPath()
    {
        return path;
    }

    public void setPath(List<Integer> path)
    {
        this.path = path;
    }
}
