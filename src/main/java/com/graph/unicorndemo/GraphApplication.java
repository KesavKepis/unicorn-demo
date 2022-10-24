/*
 *
 *  @author Sipek
 *
 */

package com.graph.unicorndemo;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/ws")
public class GraphApplication
        extends Application
{

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(GraphResource.class);
        return resources;
    }
}