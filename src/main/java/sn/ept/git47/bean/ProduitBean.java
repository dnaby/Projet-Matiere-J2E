package sn.ept.git47.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.git47.entity.Categorie;
import sn.ept.git47.entity.Marque;
import sn.ept.git47.entity.Produit;
import sn.ept.git47.facade.CategorieFacade;
import sn.ept.git47.facade.MarqueFacade;
import sn.ept.git47.facade.ProduitFacade;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("produitBean")
@ViewScoped
public class ProduitBean implements Serializable {
    @EJB
    private ProduitFacade produitFacade;

    @EJB
    private MarqueFacade marqueFacade;

    @EJB
    private CategorieFacade categorieFacade;

    private List<Produit> produitList;
    private List<Produit> selectedProduitList;
    private Produit selectedProduit;

    @PostConstruct
    public void init() {produitList = produitFacade.findAll();}

    public List<Produit> getProduitList() {return produitList;}

    public void setProduitList(List<Produit> produitList) {this.produitList = produitList;}

    public List<Produit> getSelectedProduitList() {return selectedProduitList;}

    public void setSelectedProduitList(List<Produit> selectedProduitList) {
        this.selectedProduitList = selectedProduitList;
    }

    public Produit getSelectedProduit() {return selectedProduit;}

    public void setSelectedProduit(Produit selectedProduit) {this.selectedProduit = selectedProduit;}

    public void openNewProduit() {
        Produit produit = new Produit();

        produit.setMarque(new Marque());
        produit.getMarque().setNom("");

        produit.setCategorie(new Categorie());
        produit.getCategorie().setNom("");

        this.selectedProduit = produit;
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedProduitList()) {
            int size = this.selectedProduitList.size();
            return size > 1 ? size + " produits selectionnés" : "1 produit selectionné";
        }
        return "Supprimer";
    }

    public boolean hasSelectedProduitList() {
        return this.selectedProduitList != null && !this.selectedProduitList.isEmpty();
    }

    public void deleteProduit() {
        this.produitFacade.delete(selectedProduit);
        produitList.remove(selectedProduit);

        selectedProduit = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit supprimé"));
        PrimeFaces.current().ajax().update("form:produit", "form:dt-produit");
    }

    public void deleteSelectedProduitList() {
        produitFacade.deleteAll(this.selectedProduitList);
        this.produitList.removeAll(this.selectedProduitList);
        this.selectedProduitList = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produits supprimés"));
        PrimeFaces.current().ajax().update("form:produit", "form:dt-produit");
        PrimeFaces.current().executeScript("PF('dtProduits').clearFilters()");
    }

    public void saveProduit() {
        try {
            if (this.selectedProduit.getId() == null) {
                Integer newId = produitFacade.getNextId();
                this.selectedProduit.setId(newId);
                update();
                produitFacade.create(this.selectedProduit);
                this.produitList.add(this.selectedProduit);
                this.selectedProduit = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit ajouté", "Produit ajouté avec succés"));
            }
            else {
                update();
                produitFacade.update(this.selectedProduit);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit mis à jour", "Produit mis à jour avec succés"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement produit", "Une erreur s'est produite lors de l'enregistrement du produit."));
        }

        PrimeFaces.current().executeScript("PF('manageProduitDialog').hide()");
        PrimeFaces.current().ajax().update("form:produit", "form:dt-produit");
    }

    public List<String> completeMarque(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Marque> marques = marqueFacade.findAll();

        return marques.stream().map(Marque::getNom).filter(nom -> nom.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public List<String> completeCategorie(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Categorie> categories = categorieFacade.findAll();

        return categories.stream().map(Categorie::getNom).filter(nom -> nom.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public void update() throws Exception {
        Marque marque = marqueFacade.findMarqueByNom(this.selectedProduit.getMarque().getNom());
        System.out.println(marque);
        if (marque != null) {
            this.selectedProduit.setMarqueId(marque.getId());
            this.selectedProduit.setMarque(marque);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir parmi les noms des marques existants !"));
            throw new Exception("Nom de marque invalide !");
        }

        Categorie categorie = categorieFacade.findCategorieByNom(this.selectedProduit.getCategorie().getNom());
        System.out.println(categorie);
        if (categorie != null) {
            this.selectedProduit.setCategorieId(categorie.getId());
            this.selectedProduit.setCategorie(categorie);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir parmi les noms des catégories existants !"));
            throw new Exception("Nom de catégorie invalide !");
        }
    }
}
