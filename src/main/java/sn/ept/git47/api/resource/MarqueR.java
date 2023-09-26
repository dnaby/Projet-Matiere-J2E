package sn.ept.git47.api.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sn.ept.git47.api.request.marque.MarqueRequest;
import sn.ept.git47.api.response.CustomResponse;
import sn.ept.git47.api.response.marque.MarqueResponse;
import sn.ept.git47.entity.Marque;
import sn.ept.git47.facade.MarqueFacade;

import java.util.List;

@Path("/marques")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Tag(name = "Marque", description = "")
public class MarqueR {
    @EJB
    private MarqueFacade marqueFacade;

    @GET
    @Operation(
            summary = "List of Marques",
            description = "Display the list of existing bicycle marques in the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of existing bicycle marques"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response findAllMarques(){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(marqueFacade.findAll())
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
            summary = "Create new Marque",
            description = "This endpoint create a new marque given its name.",
            responses = {
                    @ApiResponse(responseCode = "409", description = "Marque with the given name already exists"),
                    @ApiResponse(responseCode = "200", description = "New Marque Created Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response createNewMarque(MarqueRequest createMarque) {
        try {
            Marque existingMarque = marqueFacade.findMarqueByNom(createMarque.getNom());
            MarqueResponse marqueResponse = new MarqueResponse();
            if (existingMarque != null) {
                marqueResponse.setMarque(existingMarque);
                marqueResponse.setMsg("Marque with the name " + createMarque.getNom() + " already exists!");
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity(marqueResponse)
                        .build();
            } else {
                Marque newMarque = new Marque();
                Integer newId = marqueFacade.getNextId();
                newMarque.setId(newId);
                newMarque.setNom(createMarque.getNom());
                marqueFacade.create(newMarque);

                marqueResponse.setMarque(newMarque);
                marqueResponse.setMsg("Marque with the name " + newMarque.getNom() + " created successfully!");
                return Response
                        .status(Response.Status.OK)
                        .entity(marqueResponse)
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
            summary = "Update existing Marque",
            description = "This endpoint create a new marque given its ID.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Marque with the given ID no longer exists in the database."),
                    @ApiResponse(responseCode = "409", description = "Marque with the updated Name already exist."),
                    @ApiResponse(responseCode = "200", description = "Marque Updated Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response updateMarque(@PathParam("id") Integer id, MarqueRequest marqueRequest) {
        try {
            Marque marque = marqueFacade.find(id);
            MarqueResponse marqueResponse = new MarqueResponse();
            if(marque == null){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setMsg("No Marque with the given ID " + id + " was found.");
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(customResponse)
                        .build();
            }
            Marque existingMarque = marqueFacade.findMarqueByNom(marqueRequest.getNom());
            if (existingMarque != null) {
                marqueResponse.setMarque(existingMarque);
                marqueResponse.setMsg("Marque with the name " + marqueRequest.getNom() + " already exists!");
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity(marqueResponse)
                        .build();
            }
            marque.setNom(marqueRequest.getNom());
            marqueFacade.update(marque);
            marqueResponse.setMarque(marque);
            marqueResponse.setMsg("Marque with ID " + id + " updated successfully.");
            return Response
                    .status(Response.Status.OK)
                    .entity(marqueResponse)
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
            summary = "Delete Marque",
            description = "This endpoint will delete the Marque from the database",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Marque with the given ID no longer exists in the database."),
                    @ApiResponse(responseCode = "200", description = "Marque deleted Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")

            }
    )
    public Response deleteMarque(@PathParam("id") Integer id){
        try {
            Marque marque = marqueFacade.find(id);
            if(marque == null){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setMsg("No Marque with the given ID " + id + " was found.");
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(customResponse)
                        .build();
            }
            marqueFacade.delete(marque);
            MarqueResponse marqueResponse = new MarqueResponse();
            marqueResponse.setMarque(marque);
            marqueResponse.setMsg("Marque with ID " + id + " delete successfully.");
            return Response
                    .status(Response.Status.OK)
                    .entity(marqueResponse)
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

        /*
    @GET
    @Path("/byname/{nom : .+}")
    @Operation(
            summary = "Get Marque by ID",
            description = "This endpoint aims to find Marque given its Name.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "No Marque with the given Name not found"),
                    @ApiResponse(responseCode = "200", description = "Found Marque with the given Name"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response findMarqueByNom(@PathParam("nom") String nom) {
        try {
            Marque existingMarque = marqueFacade.findMarqueByNom(nom);
            if (existingMarque != null) {
                MarqueResponse marqueResponse = new MarqueResponse();
                marqueResponse.setMarque(existingMarque);
                marqueResponse.setMsg("Found Marque with Name = " + nom + "!");
                return Response
                        .status(Response.Status.OK)
                        .entity(marqueResponse)
                        .build();
            } else {
                MarqueResponse marqueResponse = new MarqueResponse();
                marqueResponse.setMarque(new Marque());
                marqueResponse.setMsg("No Marque with Name = " + nom + " found !");
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(marqueResponse)
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
    */

    /*
    @DELETE
    @Operation(
            summary = "Delete a list of Marques",
            description = "This endpoint delete a set of Marques.",
            responses = {
                    //@ApiResponse(responseCode = "404", description = "Marque with the given id no longer exists in the database."),
                    @ApiResponse(responseCode = "200", description = "Marques deleted Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response deleteListOfMarques() {
        try {

        } catch (Exception e) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setMsg("There must certainly be a problem with the internal server");
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(customResponse)
                    .build();
        }
    }
     */
}
