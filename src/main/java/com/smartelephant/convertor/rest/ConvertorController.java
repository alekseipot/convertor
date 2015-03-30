package com.smartelephant.convertor.rest;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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

    @POST
    @Path("/upload/{requestedPage}")
    @Consumes("multipart/form-data")
    public Response uploadFile(MultipartFormDataInput input, @PathParam("requestedPage") final Integer requestedPage) {

        LOG.info("Got request for uploading source file.");
        LOG.info("Requested page: " + requestedPage);
        String fileName = "";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                LOG.info("Source file name: " + fileName);
                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LOG.info("Build file for response.");
        String fileContent = "Source file name: " + fileName + ". Requested page: " + requestedPage;
        Response.ResponseBuilder response = Response.ok(fileContent);
        response.header("Content-Disposition", String.format("attachment; filename=\"%s.txt\"", fileName.substring(0, fileName.indexOf("."))));
        return response.build();

    }

    /**
     * header sample
     * {
     * Content-Type=[image/png],
     * Content-Disposition=[form-data; name="file"; filename="filename.extension"]
     * }
     */
    //get uploaded filename, is there a easy way in RESTEasy?
    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }
}
