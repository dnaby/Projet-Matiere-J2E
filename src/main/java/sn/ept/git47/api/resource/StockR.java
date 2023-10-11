package sn.ept.git47.api.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sn.ept.git47.api.request.categorie.CategorieRequest;
import sn.ept.git47.api.request.stock.NewStockRequest;
import sn.ept.git47.api.request.stock.SyncStock;
import sn.ept.git47.api.request.stock.UpdateStockRequest;
import sn.ept.git47.api.response.CustomResponse;
import sn.ept.git47.api.response.categorie.CategorieResponse;
import sn.ept.git47.api.response.stock.StockResponse;
import sn.ept.git47.entity.*;
import sn.ept.git47.facade.MagasinFacade;
import sn.ept.git47.facade.ProduitFacade;
import sn.ept.git47.facade.StockFacade;

import java.util.List;

@Path("/stocks")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Tag(name = "Stock", description = "")
public class StockR {
    @EJB
    private StockFacade stockFacade;
    @EJB
    private ProduitFacade produitFacade;
    @EJB
    private MagasinFacade magasinFacade;

    @GET
    @Operation(
            summary = "List of Stocks",
            description = "Display the list of existing Stocks in the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of existing stocks"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response findAllStocks(){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(stockFacade.findAll())
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
            summary = "Create new Stock",
            description = "This endpoint create a new stock",
            responses = {
                    @ApiResponse(responseCode = "409", description = "Stock with given Produit and Magasin already Exist"),
                    @ApiResponse(responseCode = "404", description = "Stock with given Produit or Magasin do not Exist"),
                    @ApiResponse(responseCode = "201", description = "New Stock Created Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response createNewStock(NewStockRequest stockRequest) {
        try {
            Produit produit = produitFacade.findProduitByNom(stockRequest.getProductName());
            Magasin magasin = magasinFacade.findMagasinByNom(stockRequest.getMagasinName());
            if (produit == null || magasin == null) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setMsg("Stock with given Produit or Magasin do not Exist");
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(customResponse)
                        .build();
            }
            Stock existingStock = stockFacade.find(new StockId(produit.getId(), magasin.getId()));
            StockResponse stockResponse = new StockResponse();
            if (existingStock != null) {
                stockResponse.setStock(existingStock);
                stockResponse.setMsg("Stock already Exist!");
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity(stockResponse)
                        .build();
            } else {
                Stock newStock = new Stock();
                newStock.setId(new StockId(produit.getId(), magasin.getId()));
                newStock.setProduit(produit);
                newStock.setMagasin(magasin);
                newStock.setQuantite(stockRequest.getQuantite());

                stockResponse.setStock(newStock);
                stockResponse.setMsg("Stock created successfully!");
                return Response
                        .status(Response.Status.CREATED)
                        .entity(stockResponse)
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
    @Path("{produitId}/{magasinId}")
    @Operation(
            summary = "Update existing Stock",
            description = "This endpoint update quantity of existing stock",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Stock with the given ID no longer exists in the database."),
                    @ApiResponse(responseCode = "200", description = "Stock Updated Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response updateStock(@PathParam("produitId") int produitId, @PathParam("magasinId") int magasinId, UpdateStockRequest updateStockRequest) {
        try {
            Stock stock = stockFacade.find(new StockId(produitId, magasinId));
            if (stock == null) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setMsg("Stock with the given ID no longer Exists in the database.");
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(customResponse)
                        .build();
            }
            stock.setQuantite(updateStockRequest.getQuantite());
            stockFacade.update(stock);

            StockResponse stockResponse = new StockResponse();
            stockResponse.setStock(stock);
            stockResponse.setMsg("Stock Updated Successfully");

            return Response
                    .status(Response.Status.OK)
                    .entity(stockResponse)
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
    @Path("{produitId}/{magasinId}")
    @Operation(
            summary = "Delete Stock",
            description = "This endpoint will delete a Stock from the database",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Stock with the given ID no longer exists in the database."),
                    @ApiResponse(responseCode = "200", description = "Stock deleted Successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")

            }
    )
    public Response deleteStock(@PathParam("produitId") int produitId, @PathParam("magasinId") int magasinId){
        try {
            Stock stock = stockFacade.find(new StockId(produitId, magasinId));
            if (stock == null) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setMsg("Stock with the given ID no longer Exists in the database.");
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(customResponse)
                        .build();
            }
            stockFacade.delete(stock);
            StockResponse stockResponse = new StockResponse();
            stockResponse.setStock(stock);
            stockResponse.setMsg("Stock deleted successfully");
            return Response
                    .status(Response.Status.OK)
                    .entity(stockResponse)
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
    @Path("/sync")
    @Operation(
            summary = "Synchronize offline data",
            description = "This endpoint synchronize offline data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sync successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public Response syncStock(SyncStock syncStock) {
        try {
            List<Stock> remoteStockList = syncStock.getStocks();
            List<Stock> serverStockList = stockFacade.findAll();
            for (Stock serverStock: serverStockList) {
                boolean stocksHasBeenDeleted = true;

                for (Stock remoteStock:remoteStockList) {
                    remoteStock.setId(new StockId(remoteStock.getMagasin().getId(), remoteStock.getProduit().getId()));
                    if (new StockId(remoteStock.getMagasin().getId(), remoteStock.getProduit().getId()).equals(serverStock.getId())) {
                        stockFacade.update(remoteStock);
                        stocksHasBeenDeleted = false;
                    }
                    Stock stock = stockFacade.find(new StockId(remoteStock.getMagasin().getId(), remoteStock.getProduit().getId()));
                    if (stock == null) {
                        stockFacade.create(remoteStock);
                    }
                }

                if (stocksHasBeenDeleted) {
                    stockFacade.delete(serverStock);
                }
            }
            CustomResponse customResponse = new CustomResponse();
            customResponse.setMsg("Sync successfully !");
            return Response
                    .status(Response.Status.OK)
                    .entity(customResponse)
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
