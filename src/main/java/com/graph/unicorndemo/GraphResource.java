/*
 *
 *  @author Sipek
 *
 */

package com.graph.unicorndemo;

import com.graph.unicorndemo.dto.GraphDto;
import com.graph.unicorndemo.dto.PathDto;
import com.graph.unicorndemo.exception.GraphException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.logging.Logger;

@Path("/graph")
public class GraphResource
{
    private static final Logger LOGGER = Logger.getLogger(GraphResource.class.getName());

    private GraphHandler graphHandler;

    public GraphResource()
    {
        LOGGER.info(() -> "Initializing GraphResource.");
        graphHandler = new GraphHandler();
    }

    @POST
    @Path("/init")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response init(GraphDto graphDto)
    {
        try
        {
            LOGGER.info(() -> String.format("Executing 'path' request with param %s", graphDto));
            graphHandler.initGraph(graphDto);
            return Response.status(Status.OK)
                    .entity("The graph has been successfully initialized.")
                    .build();
        }
        catch ( GraphException ex )
        {
            return Response.status(Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/path")
    @Produces(MediaType.APPLICATION_JSON)
    public Response path(@QueryParam("targetNode") int targetNode)
    {
        LOGGER.info(() -> "Executing 'path' request with param " + targetNode);
        try
        {
            PathDto pathDto = graphHandler.getGraphPath(targetNode);
            LOGGER.info(() -> String.format(
                    "Response for 'path' request with param %d is as follows: distance=%d, path=%s.",
                    targetNode,
                    pathDto.getDistance(),
                    pathDto.getPath()));
            return Response.status(Status.OK)
                    .entity(pathDto)
                    .build();
        }
        catch ( GraphException ex )
        {
            return Response.status(Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }
    }
}