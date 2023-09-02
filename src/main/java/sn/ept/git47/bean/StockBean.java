package sn.ept.git47.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.git47.entity.*;
import sn.ept.git47.facade.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("stockBean")
@ViewScoped
public class StockBean implements Serializable {
    @EJB
    private StockFacade stockFacade;

    @EJB
    private MagasinFacade magasinFacade;

    @EJB
    private ProduitFacade produitFacade;

    private List<Stock> stockList;
    private List<Stock> selectedStockList;
    private Stock selectedStock;

    @PostConstruct
    public void init() {stockList = stockFacade.findAll();}

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public List<Stock> getSelectedStockList() {
        return selectedStockList;
    }

    public void setSelectedStockList(List<Stock> selectedStockList) {
        this.selectedStockList = selectedStockList;
    }

    public Stock getSelectedStock() {
        return selectedStock;
    }

    public void setSelectedStock(Stock selectedStock) {
        this.selectedStock = selectedStock;
    }

    public void openNewStock() {
        Stock stock = new Stock();

        stock.setId(new StockId());

        stock.setMagasin(new Magasin());
        stock.getMagasin().setNom("");

        stock.setProduit(new Produit());
        stock.getProduit().setNom("");

        this.selectedStock = stock;
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedStockList()) {
            int size = this.selectedStockList.size();
            return size > 1 ? size + " stocks selectionnés" : "1 stock selectionné";
        }
        return "Supprimer";
    }

    public boolean hasSelectedStockList() {
        return this.selectedStockList != null && !this.selectedStockList.isEmpty();
    }

    public void deleteStock() {
        this.stockFacade.delete(selectedStock);
        stockList.remove(selectedStock);
        selectedStock = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Stock supprimé", "Stock supprimé avec succés !"));
        PrimeFaces.current().ajax().update("form:stock", "form:dt-stock");
    }

    public void saveStock() {
        try {
            if (this.selectedStock.getId().getMagasinId() == null && this.selectedStock.getId().getProduitId() == null) {
                newStock();
                stockFacade.create(this.selectedStock);
                this.stockList.add(this.selectedStock);
                this.selectedStock = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Stock ajouté", "Stock ajouté avec succés !"));
            } else {
                stockFacade.update(this.selectedStock);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Stock mis à jour", "Stock mis à jour avec succés !"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement stock", "Une erreur s'est produite lors de l'enregistrement du stock. Assurez-vous qu'un tel stock n'est pas déjà disponible dans la base de données ?"));
        }

        PrimeFaces.current().executeScript("PF('manageStockDialog').hide()");
        PrimeFaces.current().ajax().update("form:stock", "form:dt-stock");
    }

    public List<String> completeMagasin(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Magasin> magasins = magasinFacade.findAll();

        return magasins.stream().map(Magasin::getNom).filter(nom -> nom.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public List<String> completeProduit(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Produit> produits = produitFacade.findAll();

        return produits.stream().map(Produit::getNom).filter(nom -> nom.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public void newStock() {
        Magasin magasin = magasinFacade.findMagasinByNom(this.selectedStock.getMagasin().getNom());
        Produit produit = produitFacade.findProduitByNom(this.selectedStock.getProduit().getNom());
        if (magasin != null && produit != null) {
            this.selectedStock.getId().setProduitId(produit.getId());
            this.selectedStock.getId().setMagasinId(magasin.getId());
            this.selectedStock.setMagasin(magasin);
            this.selectedStock.setProduit(produit);
        } else if (magasin == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir parmi les noms des magasins existants !"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir parmi les noms des produits existants !"));
        }
    }
}
