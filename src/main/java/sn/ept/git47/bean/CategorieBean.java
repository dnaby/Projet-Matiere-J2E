package sn.ept.git47.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.git47.entity.Categorie;
import sn.ept.git47.facade.CategorieFacade;

import java.io.Serializable;
import java.util.List;

@Named("categorieBean")
@ViewScoped
public class CategorieBean implements Serializable {
    @EJB
    private CategorieFacade categorieFacade;

    private List<Categorie> categorieList;
    private List<Categorie> selectedCategorieList;
    private Categorie selectedCategorie;

    @PostConstruct
    public void init() {categorieList = categorieFacade.findAll();}

    public List<Categorie> getCategorieList() {return categorieList;}

    public void setCategorieList(List<Categorie> categorieList) {this.categorieList = categorieList;}

    public List<Categorie> getSelectedCategorieList() {return selectedCategorieList;}

    public void setSelectedCategorieList(List<Categorie> selectedCategorieList) {
        this.selectedCategorieList = selectedCategorieList;
    }

    public Categorie getSelectedCategorie() {return selectedCategorie;}

    public void setSelectedCategorie(Categorie selectedCategorie) {this.selectedCategorie = selectedCategorie;}

    public void openNewCategorie() {this.selectedCategorie = new Categorie();}

    public void deleteCategorie() {
        this.categorieFacade.delete(selectedCategorie);
        categorieList.remove(selectedCategorie);
        selectedCategorie = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Catégorie supprimée"));
        PrimeFaces.current().ajax().update("form:categorie", "form:dt-categorie");
    }

    public void saveCategorie() {
        if (this.selectedCategorie.getId() == null) {
            Integer newId = categorieFacade.getNextId();
            this.selectedCategorie.setId(newId);
            categorieFacade.create(this.selectedCategorie);
            this.categorieList.add(this.selectedCategorie);
            this.selectedCategorie = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Catégorie ajoutée"));
        }
        else {
            categorieFacade.update(this.selectedCategorie);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Catégorie mise à jour"));
        }

        PrimeFaces.current().executeScript("PF('manageCategorieDialog').hide()");
        PrimeFaces.current().ajax().update("form:categorie", "form:dt-categorie");
    }
}
