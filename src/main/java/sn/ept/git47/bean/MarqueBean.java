package sn.ept.git47.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.git47.entity.Marque;
import sn.ept.git47.facade.MarqueFacade;

import java.io.Serializable;
import java.util.List;

@Named("marqueBean")
@ViewScoped
public class MarqueBean implements Serializable {
    @EJB
    private MarqueFacade marqueFacade;

    private List<Marque> marqueList;
    private List<Marque> selectedMarqueList;
    private Marque selectedMarque;

    @PostConstruct
    public void init() {marqueList = marqueFacade.findAll();}

    public List<Marque> getMarqueList() {return marqueList;}

    public void setMarqueList(List<Marque> marqueList) {this.marqueList = marqueList;}

    public List<Marque> getSelectedMarqueList() {return selectedMarqueList;}

    public void setSelectedMarqueList(List<Marque> selectedMarqueList) {this.selectedMarqueList = selectedMarqueList;}

    public Marque getSelectedMarque() {return selectedMarque;}

    public void setSelectedMarque(Marque selectedMarque) {this.selectedMarque = selectedMarque;}

    public void openNewMarque() {this.selectedMarque = new Marque();}

    public void deleteMarque() {
        this.marqueFacade.delete(selectedMarque);
        marqueList.remove(selectedMarque);
        selectedMarque = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque supprimée"));
        PrimeFaces.current().ajax().update("form:marque", "form:dt-marque");
    }

    public void saveMarque() {
        if (this.selectedMarque.getId() == null) {
            Integer newId = marqueFacade.getNextId();
            this.selectedMarque.setId(newId);
            marqueFacade.create(this.selectedMarque);
            this.marqueList.add(this.selectedMarque);
            this.selectedMarque = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque ajoutée"));
        }
        else {
            marqueFacade.update(this.selectedMarque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque mise à jour"));
        }

        PrimeFaces.current().executeScript("PF('manageMarqueDialog').hide()");
        PrimeFaces.current().ajax().update("form:marque", "form:dt-marque");
    }
}
