package sn.ept.git47.bean;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import sn.ept.git47.entity.*;
import sn.ept.git47.facade.ArticleCommandeFacade;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.ept.git47.facade.ProduitFacade;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Named("articleCommandeBean")
@ViewScoped
public class ArticleCommandeBean implements Serializable {
    @EJB
    private ArticleCommandeFacade articleCommandeFacade;
    @EJB
    private ProduitFacade produitFacade;
    private List<ArticleCommande> articleCommandeList;
    private List<ArticleCommande> selectedArticleCommandeList;
    private ArticleCommande selectedArticleCommande;

    @PostConstruct
    public void init() {articleCommandeList = articleCommandeFacade.findAll();}

    public List<ArticleCommande> getArticleCommandeList() {return articleCommandeList;}

    public void setArticleCommandeList(List<ArticleCommande> articleCommandeList) {
        this.articleCommandeList = articleCommandeList;
    }

    public ArticleCommande getSelectedArticleCommande() {return selectedArticleCommande;}

    public void setSelectedArticleCommande(ArticleCommande selectedArticleCommande) {
        this.selectedArticleCommande = selectedArticleCommande;
    }

    public List<ArticleCommande> getSelectedArticleCommandeList() {return selectedArticleCommandeList;}

    public void setSelectedArticleCommandeList(List<ArticleCommande> selectedArticleCommandeList) {
        this.selectedArticleCommandeList = selectedArticleCommandeList;
    }

    public void openNewArticleCommande() {
        ArticleCommande articleCommande = new ArticleCommande();
        ArticleCommandeId articleCommandeId = new ArticleCommandeId();
        articleCommandeId.setNumero_commande(0);
        articleCommandeId.setLigne(0);
        articleCommande.setId(articleCommandeId);
        Produit produit = new Produit();
        produit.setNom("");
        articleCommande.setProduit(produit);
        this.selectedArticleCommande = articleCommande;
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedArticleCommandeList()) {
            int size = this.selectedArticleCommandeList.size();
            return size > 1 ? size + " produits selectionnés" : "1 product selectionnés";
        }
        return "Supprimer";
    }

    public boolean hasSelectedArticleCommandeList() {
        return this.selectedArticleCommandeList != null && !this.selectedArticleCommandeList.isEmpty();
    }

    public void deleteArticleCommande() {
        articleCommandeFacade.delete(selectedArticleCommande);
        articleCommandeList.remove(selectedArticleCommande);
        selectedArticleCommande = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression", "Article Commandé supprimé avec succés !"));
        PrimeFaces.current().ajax().update("form:article", "form:dt-articleCommande");
    }

    public void deleteSelectedArticleCommandeList() {
        this.articleCommandeList.removeAll(this.selectedArticleCommandeList);
        this.selectedArticleCommandeList = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression", "Articles Commandés supprimés avec succés !"));
        PrimeFaces.current().ajax().update("form:article", "form:dt-articleCommande");
        PrimeFaces.current().executeScript("PF('dtarticleCommandes').clearFilters()");
    }

    public void saveArticleCommande() {
        try {
            if (articleCommandeFacade.findById(this.selectedArticleCommande.getId()) == null) {
                newArticleCommande();
                articleCommandeFacade.create(this.selectedArticleCommande);
                this.articleCommandeList = articleCommandeFacade.findAll();
                this.selectedArticleCommande = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout", "Article Commandé ajouté avec succés !"));
            } else {
                Produit produit = produitFacade.findProduitByNom(this.selectedArticleCommande.getProduit().getNom());
                if (produit != null) {
                    this.selectedArticleCommande.setProduitId(produit.getId());
                    this.selectedArticleCommande.setProduit(produit);
                    articleCommandeFacade.update(this.selectedArticleCommande);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mis à Jour", "Article Commandé mis à jour avec succés !"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir parmi les noms des articles commandés existants !"));
                }
            }
        } catch (Exception e) {
            this.articleCommandeList = articleCommandeFacade.findAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement", "Une erreur s'est produite lors de l'enregistrement du l'article commandé. Assurez-vous qu'un tel article commandé n'est pas déjà disponible dans la base de données ?"));
        }

        PrimeFaces.current().executeScript("PF('manageArticleCommandeDialog').hide()");
        PrimeFaces.current().ajax().update("form:article", "form:dt-articleCommande");
    }

    public void newArticleCommande() {
        Produit produit = produitFacade.findProduitByNom(this.selectedArticleCommande.getProduit().getNom());
        if (produit != null) {
            this.selectedArticleCommande.setProduit(produit);
            this.selectedArticleCommande.setProduitId(produit.getId());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir parmi les noms des articles commandés existants !"));
        }
    }

    public List<String> completeProduit(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Produit> produits = produitFacade.findAll();

        return produits.stream().map(Produit::getNom).filter(nom -> nom.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
}
