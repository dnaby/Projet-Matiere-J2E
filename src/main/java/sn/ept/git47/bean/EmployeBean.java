package sn.ept.git47.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.git47.entity.*;
import sn.ept.git47.facade.EmployeFacade;
import sn.ept.git47.facade.MagasinFacade;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("employeBean")
@ViewScoped
public class EmployeBean implements Serializable {
    @EJB
    private EmployeFacade employeFacade;
    @EJB
    private MagasinFacade magasinFacade;
    private List<Employe> employeList;
    private List<Employe> selectedEmployeList;
    private Employe selectedEmploye;

    @PostConstruct
    public void init() {
        employeList = employeFacade.findAll();

        for (Employe employe : employeList) {
            if (employe.getManager() == null) {
                Employe manager = new Employe();
                manager.setEmail("");
                employe.setManager(manager);
            }
        }
    }

    public List<Employe> getEmployeList() {
        return employeList;
    }

    public void setEmployeList(List<Employe> employeList) {
        this.employeList = employeList;
    }

    public List<Employe> getSelectedEmployeList() {
        return selectedEmployeList;
    }

    public void setSelectedEmployeList(List<Employe> selectedEmployeList) {
        this.selectedEmployeList = selectedEmployeList;
    }

    public Employe getSelectedEmploye() {
        return selectedEmploye;
    }

    public void setSelectedEmploye(Employe selectedEmploye) {
        this.selectedEmploye = selectedEmploye;
    }

    public void openNewEmploye() {
        Employe employe = new Employe();
        Magasin magasin = new Magasin();
        magasin.setNom("");
        employe.setMagasin(magasin);
        Employe manager = new Employe();
        manager.setNom("");
        employe.setManager(manager);
        this.selectedEmploye = employe;
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedEmployeList()) {
            int size = this.selectedEmployeList.size();
            return size > 1 ? size + " employé selectionnés" : "1 employé selectionné";
        }
        return "Supprimer";
    }

    public boolean hasSelectedEmployeList() {
        return this.selectedEmployeList != null && !this.selectedEmployeList.isEmpty();
    }

    public void deleteEmploye() {
        employeFacade.delete(selectedEmploye);
        employeList.remove(selectedEmploye);
        selectedEmploye = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression", "Employé supprimé avec succés !"));
        PrimeFaces.current().ajax().update("form:employe", "form:dt-employe");
    }

    public void deleteSelectedEmployeList() {
        employeFacade.deleteAll(this.selectedEmployeList);
        this.employeList.removeAll(this.selectedEmployeList);
        this.selectedEmployeList = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression", "Employés supprimés avec succés !"));
        PrimeFaces.current().ajax().update("form:employe", "form:dt-employe");
        PrimeFaces.current().executeScript("PF('dtEmployes').clearFilters()");
    }

    public void saveEmploye() {
        if (this.selectedEmploye.getId() == null) {
            Integer newId = employeFacade.getNextId();
            this.selectedEmploye.setId(newId);
            updateEmploye();
            employeFacade.create(this.selectedEmploye);
            this.employeList.add(this.selectedEmploye);
            this.selectedEmploye = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajout", "Employé ajouté avec succés !"));
        } else {
            updateEmploye();
            employeFacade.update(this.selectedEmploye);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mis à Jour", "Employé mis à jour avec succés !"));
        }

        PrimeFaces.current().executeScript("PF('manageEmployeDialog').hide()");
        PrimeFaces.current().ajax().update("form:employe", "form:dt-employe");
    }

    public void updateEmploye() {
        Magasin magasin = magasinFacade.findMagasinByNom(this.selectedEmploye.getMagasin().getNom());
        Employe manager = employeFacade.findEmployeByEmail(this.selectedEmploye.getManager().getEmail());
        if (magasin != null) {
            this.selectedEmploye.setMagasin(magasin);
            this.selectedEmploye.setMagasinId(magasin.getId());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir parmi les noms des magasins existants !"));
        }
        if (manager != null) {
            this.selectedEmploye.setManagerId(manager.getId());
            this.selectedEmploye.setManager(manager);
        } else {
            this.selectedEmploye.setManagerId(null);
            this.selectedEmploye.setManager(null);
        }
    }

    public List<String> completeMagasin(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Magasin> magasins = magasinFacade.findAll();

        return magasins.stream().map(Magasin::getNom).filter(nom -> nom.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public List<String> completeManagers(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Employe> managers = employeFacade.findAllEmployes();

        return managers.stream()
                .map(Personne::getEmail)
                .filter(email -> email.toLowerCase().startsWith(queryLowerCase))
                .collect(Collectors.toList());
    }
}
