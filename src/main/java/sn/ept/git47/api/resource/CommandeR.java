
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
import sn.ept.git47.facade.CommandeFacade;
import sn.ept.git47.facade.ProduitFacade;

@Path("/commandes")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Tag(name = "Commande", description = "")
public class CommandeR {
    @EJB
    private CommandeFacade commandeFacade;

    @GET
    @Operation(
            summary = "List of Commandes",
            description = "Display the list of existing Commandes in the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of existing products(produits)"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response findAllCommands(){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(commandeFacade.findAll())
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
