package org.opennms.tquadro.plugin;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.opennms.tquadro.client.api.TQuadroApiException;

@Path("/tquadro")
public interface RestService {

    @GET
    @Path("/ping")
    Response ping();

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

}
