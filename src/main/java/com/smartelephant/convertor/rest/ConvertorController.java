package com.smartelephant.convertor.rest;

import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/convert")
public class ConvertorController {

    private static Logger LOG = Logger.getLogger(ConvertorController.class.getName());

    @GET
    @Path("/{param}")
    public Response printMessage(@PathParam("param") String msg) {

        LOG.info("Got param in request: " + msg);

        String result = "Restful example : " + msg;

        return Response.status(200).entity(result).build();

    }

}
