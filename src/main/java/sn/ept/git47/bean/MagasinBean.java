package sn.ept.git47.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.git47.entity.*;
import sn.ept.git47.facade.ClientFacade;
import sn.ept.git47.facade.CommandeFacade;
import sn.ept.git47.facade.EmployeFacade;
import sn.ept.git47.facade.MagasinFacade;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("magasinBean")
@ViewScoped
public class MagasinBean implements Serializable {
    @EJB
    private MagasinFacade magasinFacade;
    private List<Magasin> magasinList;
    private List<Magasin> selectedMagasinList;
    private Magasin selectedMagasin;

    @PostConstruct
    public void init() {
        magasinList = magasinFacade.findAll();
    }

    public List<Magasin> getMagasinList() {return magasinList;}

    public void setMagasinList(List<Magasin> magasinList) {this.magasinList = magasinList;}

    public List<Magasin> getSelectedMagasinList() {return selectedMagasinList;}

    public void setSelectedMagasinList(List<Magasin> selectedMagasinList) {this.selectedMagasinList = selectedMagasinList;}

    public Magasin getSelectedMagasin() {return selectedMagasin;}

    public void setSelectedMagasin(Magasin selectedMagasin) {this.selectedMagasin = selectedMagasin;}

    public void openNewMagasin() {
        this.selectedMagasin = new Magasin();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMagasinList()) {
            int size = this.selectedMagasinList.size();
            return size > 1 ? size + " magasins selectionnés" : "1 magasin selectionné";
        }
        return "Supprimer";
    }

    public boolean hasSelectedMagasinList() {
        return this.selectedMagasinList != null && !this.selectedMagasinList.isEmpty();
    }

    public void deleteMagasin() {
        magasinFacade.delete(selectedMagasin);
        magasinList.remove(selectedMagasin);
        selectedMagasin = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression", "Magasin supprimé avec succés !"));
        PrimeFaces.current().ajax().update("form:magasin", "form:dt-magasin");
    }

    public void deleteSelectedMagasinList() {
        magasinFacade.deleteAll(this.selectedMagasinList);
        this.magasinList.removeAll(this.selectedMagasinList);
        this.selectedMagasinList = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression", "Magasins supprimés avec succés !"));
        PrimeFaces.current().ajax().update("form:magasin", "form:dt-magasin");
        PrimeFaces.current().executeScript("PF('dtMagasins').clearFilters()");
    }

    public void saveMagasin() {
        if (this.selectedMagasin.getId() == null) {
            Integer newId = magasinFacade.getNextId();
            this.selectedMagasin.setId(newId);
            magasinFacade.create(this.selectedMagasin);
            this.magasinList.add(this.selectedMagasin);
            this.selectedMagasin = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajout", "Magasin ajouté avec succés !"));
        }
        else {
            magasinFacade.update(this.selectedMagasin);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mis à Jour", "Magasin mis à jour avec succés !"));
        }

        PrimeFaces.current().executeScript("PF('manageMagasinDialog').hide()");
        PrimeFaces.current().ajax().update("form:magasin", "form:dt-magasin");
    }
}
