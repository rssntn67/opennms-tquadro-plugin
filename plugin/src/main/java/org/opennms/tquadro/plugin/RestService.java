package org.opennms.tquadro.plugin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/tquadro")
public interface RestService {

    @GET
    @Path("/ping")
    Response ping();

    /*
    @GET
    @Path("/connections")
    @Produces(MediaType.APPLICATION_JSON)
    List<ConnectionListElementDTO> getConnectionList() throws TQuadroApiException;

    @POST
    @Path("/connections")
    @Consumes(MediaType.APPLICATION_JSON)
    Response addConnection(ConnectionDTO connectionDTO, @QueryParam("dryrun") boolean dryRun, @QueryParam("force") boolean force) throws TQuadroApiException;

    @PUT
    @Path("/connections/{alias}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response editConnection(@PathParam("alias") String alias, ConnectionDTO connection, @QueryParam("force") boolean force) throws TQuadroApiException;

    @GET
    @Path("/connections/{alias}")
    @Produces(MediaType.APPLICATION_JSON)
    Response validateConnection(@PathParam("alias") String alias);

    @DELETE
    @Path("/connections/{alias}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteConnection(@PathParam("alias") String alias);

     */
}
