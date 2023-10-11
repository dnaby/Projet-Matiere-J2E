
package sn.ept.git47.api.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sn.ept.git47.api.response.CustomResponse;
import sn.ept.git47.facade.ClientFacade;
import sn.ept.git47.facade.CommandeFacade;

@Path("/clients")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Tag(name = "Client", description = "")
public class ClientR {
    @EJB
    private ClientFacade clientFacade;

    @GET
    @Operation(
            summary = "List of Clients",
            description = "Display the list of existing Clients in the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of existing clients"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response findAllClients(){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(clientFacade.findAll())
                    .build();
        } catch (Exception e) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setMsg("There must certainly be a problem with the internal server");
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(customResponse)
                    .build();
        }
    }
}
