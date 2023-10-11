package sn.ept.git47.api.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sn.ept.git47.api.request.categorie.CategorieRequest;
import sn.ept.git47.api.request.marque.MarqueRequest;
import sn.ept.git47.api.response.CustomResponse;
import sn.ept.git47.api.response.categorie.CategorieResponse;
import sn.ept.git47.api.response.marque.MarqueResponse;
import sn.ept.git47.entity.Categorie;
import sn.ept.git47.entity.Marque;
import sn.ept.git47.facade.CategorieFacade;

import java.util.List;

@Path("/categories")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Tag(name = "Categorie", description = "")
public class CategorieR {
    @EJB
    private CategorieFacade categorieFacade;

    @GET
    @Operation(
            summary = "List of Categories",
            description = "Display the list of existing bicycles categories in the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of existing bicycle categories"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response findAllCategories(){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(categorieFacade.findAll())
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

    @POST
    @Operation(
            summary = "Create new Categorie",
            description = "This endpoint create a new categorie given its name.",
            responses = {
                    @ApiResponse(responseCode = "409", description = "Categorie with the given name already exists"),
                    @ApiResponse(responseCode = "201", description = "New Categorie Created Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response createNewCategorie(CategorieRequest categorieRequest) {
        try {
            Categorie existingCategorie = categorieFacade.findCategorieByNom(categorieRequest.getNom());
            CategorieResponse categorieResponse = new CategorieResponse();
            if (existingCategorie != null) {
                categorieResponse.setCategorie(existingCategorie);
                categorieResponse.setMsg("Categorie with the name " + categorieRequest.getNom() + " already exists!");
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity(categorieResponse)
                        .build();
            } else {
                Categorie newCategorie = new Categorie();
                Integer newId = categorieFacade.getNextId();
                newCategorie.setId(newId);
                newCategorie.setNom(categorieRequest.getNom());
                categorieFacade.create(newCategorie);

                categorieResponse.setCategorie(newCategorie);
                categorieResponse.setMsg("Categorie with the name " + newCategorie.getNom() + " created successfully!");
                return Response
                        .status(Response.Status.CREATED)
                        .entity(categorieResponse)
                        .build();
            }
        } catch (Exception e) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setMsg("There must certainly be a problem with the internal server");
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(customResponse)
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    @Operation(
            summary = "Update existing Categorie",
            description = "This endpoint update a categorie given its ID.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Categorie with the given ID no longer exists in the database."),
                    @ApiResponse(responseCode = "409", description = "Categorie with the updated Name already exist."),
                    @ApiResponse(responseCode = "200", description = "Categorie Updated Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response updateCategorie(@PathParam("id") Integer id, CategorieRequest categorieRequest) {
        try {
            Categorie categorie = categorieFacade.find(id);
            CategorieResponse categorieResponse = new CategorieResponse();
            if(categorie == null){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setMsg("No Categorie with the given ID " + id + " was found.");
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(customResponse)
                        .build();
            }
            Categorie existingCategorie = categorieFacade.findCategorieByNom(categorieRequest.getNom());
            if (existingCategorie != null) {
                categorieResponse.setCategorie(existingCategorie);
                categorieResponse.setMsg("Categorie with the name " + categorieRequest.getNom() + " already exists!");
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity(categorieResponse)
                        .build();
            }
            categorie.setNom(categorieRequest.getNom());
            categorieFacade.update(categorie);
            categorieResponse.setCategorie(categorie);
            categorieResponse.setMsg("Categorie with ID " + id + " updated successfully.");
            return Response
                    .status(Response.Status.OK)
                    .entity(categorieResponse)
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

    @DELETE
    @Path("{id}")
    @Operation(
            summary = "Delete Categorie",
            description = "This endpoint will delete the Categorie from the database",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Categorie with the given ID no longer exists in the database."),
                    @ApiResponse(responseCode = "200", description = "Categorie deleted Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")

            }
    )
    public Response deleteCategorie(@PathParam("id") Integer id){
        try {
            Categorie categorie = categorieFacade.find(id);
            if(categorie == null){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setMsg("No Categorie with the given ID " + id + " was found.");
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(customResponse)
                        .build();
            }
            categorieFacade.delete(categorie);
            CategorieResponse categorieResponse = new CategorieResponse();
            categorieResponse.setCategorie(categorie);
            categorieResponse.setMsg("Categorie with ID " + id + " delete successfully.");
            return Response
                    .status(Response.Status.OK)
                    .entity(categorieResponse)
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
