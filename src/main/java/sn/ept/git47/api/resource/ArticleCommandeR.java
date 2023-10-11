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
import sn.ept.git47.facade.ArticleCommandeFacade;
import sn.ept.git47.facade.StockFacade;

@Path("/article_commandes")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Tag(name = "Article Commandé", description = "")
public class ArticleCommandeR {
    @EJB
    private ArticleCommandeFacade articleCommandeFacade;

    @GET
    @Operation(
            summary = "List of ArticleCommande",
            description = "Display the list of existing ArticleCommande in the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of existing stocks"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response findAllArticleCommandes(){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(articleCommandeFacade.findAll())
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
