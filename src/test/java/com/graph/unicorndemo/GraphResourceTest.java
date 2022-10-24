/*
 *
 *  @author Sipek
 *
 */

package com.graph.unicorndemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.graph.unicorndemo.dto.GraphDto;
import com.graph.unicorndemo.dto.PathDto;
import com.graph.unicorndemo.exception.GraphException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.FieldSetter;

class GraphResourceTest
{
    @Mock
    private GraphHandler mockedGraphHandler;

    private GraphResource graphResource;


    @BeforeEach
    void setUp()
            throws NoSuchFieldException, SecurityException
    {
        MockitoAnnotations.initMocks(this);
        graphResource = new GraphResource();
        FieldSetter.setField(graphResource,
                             graphResource.getClass()
                                     .getDeclaredField("graphHandler"),
                             mockedGraphHandler);
    }

    @Test
    void init_RequestOk()
    {
        GraphDto graphDto = new GraphDto();

        Response result = graphResource.init(graphDto);
        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals("The graph has been successfully initialized.", result.getEntity());
    }

    @Test
    void init_BadRequest()
    {
        GraphDto graphDto = new GraphDto();
        doThrow(new GraphException("Error message")).when(mockedGraphHandler)
                .initGraph(graphDto);

        Response result = graphResource.init(graphDto);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), result.getStatus());
        assertEquals("Error message", result.getEntity());
    }

    @Test
    void path_RequestOk()
    {
        Integer targetNode = Integer.valueOf(2);

        PathDto pathDto = new PathDto();
        pathDto.setDistance(2);
        pathDto.setPath(new ArrayList<>());

        when(mockedGraphHandler.getGraphPath(targetNode)).thenReturn(pathDto);

        Response result = graphResource.path(targetNode);
        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertNotNull(result.getEntity());
    }

    @Test
    void path_BadRequest()
    {
        Integer targetNode = Integer.valueOf(2);
        doThrow(new GraphException("Error message")).when(mockedGraphHandler)
                .getGraphPath(targetNode);

        Response result = graphResource.path(targetNode);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), result.getStatus());
        assertEquals("Error message", result.getEntity());
    }
}