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
import sn.ept.git47.api.response.location.LocationRequest;
import sn.ept.git47.api.response.location.LocationResponse;
import sn.ept.git47.api.response.marque.MarqueResponse;
import sn.ept.git47.entity.Location;
import sn.ept.git47.entity.Marque;
import sn.ept.git47.facade.LocationFacade;
import sn.ept.git47.facade.MarqueFacade;

import java.util.stream.Collectors;

@Path("/locations")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Tag(name = "Location", description = "")
public class LocationR {
    @EJB
    private LocationFacade locationFacade;

    @GET
    @Operation(
            summary = "List of device Location",
            description = "Display the location of devices",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location of devices"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response findAllLocations(){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(locationFacade.findAll().stream().map(Location::convertToLocationRequest).collect(Collectors.toList()))
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
            summary = "Create or Update device location",
            description = "This endpoint creates or updates device location",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location Created or Updated Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response deviceLocation(LocationRequest locationRequest) {
        try {
            Location existingLocation  = locationFacade.find(locationRequest.getAndroidID());
            LocationResponse locationResponse = new LocationResponse();
            if (existingLocation != null) {
                locationFacade.update(locationRequest.convertToLocationEntity());
                locationResponse.setLocation(locationRequest);
                locationResponse.setMsg("Location updated successfully !");
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity(locationResponse)
                        .build();
            } else {
                locationFacade.create(locationRequest.convertToLocationEntity());

                locationResponse.setLocation(locationRequest);
                locationResponse.setMsg("Location created successfully!");
                return Response
                        .status(Response.Status.CREATED)
                        .entity(locationResponse)
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
}
