/*
 *
 *  @author Sipek
 *
 */

package com.graph.unicorndemo;

import com.graph.unicorndemo.dto.GraphDto;

public class GraphCache
{
    private static GraphCache obj;

    private GraphDto graphDto;

    private GraphCache()
    {
    }

    protected static synchronized GraphCache getInstance()
    {
        if ( obj == null )
        {
            obj = new GraphCache();
        }
        return obj;
    }

    protected GraphDto getGraphDto()
    {
        return this.graphDto;
    }

    protected void setGraphDto(GraphDto graphDto)
    {
        this.graphDto = graphDto;
    }
}
