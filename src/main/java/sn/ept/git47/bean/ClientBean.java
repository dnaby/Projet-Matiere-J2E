package sn.ept.git47.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.git47.entity.Client;
import sn.ept.git47.facade.ClientFacade;

import java.io.Serializable;
import java.util.List;

@Named("clientBean")
@ViewScoped
public class ClientBean implements Serializable {
    @EJB
    private ClientFacade clientFacade;

    private List<Client> clientList;
    private List<Client> selectedClientList;
    private Client selectedClient;

    @PostConstruct
    public void init() {clientList = clientFacade.findAll();}

    public List<Client> getClientList() {return clientList;}

    public void setClientList(List<Client> clientList) {this.clientList = clientList;}

    public List<Client> getSelectedClientList() {return selectedClientList;}

    public void setSelectedClientList(List<Client> selectedClientList) {this.selectedClientList = selectedClientList;}

    public Client getSelectedClient() {return selectedClient;}

    public void setSelectedClient(Client selectedClient) {this.selectedClient = selectedClient;}

    public void openNewClient() {this.selectedClient = new Client();}

    public String getDeleteButtonMessage() {
        if (hasSelectedClientList()) {
            int size = this.selectedClientList.size();
            return size > 1 ? size + " clients selectionnés" : "1 client selectionné";
        }
        return "Supprimer";
    }

    public boolean hasSelectedClientList() {
        return this.selectedClientList != null && !this.selectedClientList.isEmpty();
    }

    public void deleteClient() {
        clientFacade.delete(selectedClient);
        clientList.remove(selectedClient);
        selectedClient = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client supprimé", "Client supprimé avec succés !"));
        PrimeFaces.current().ajax().update("form:client", "form:dt-client");
    }

    public void deleteSelectedClientList() {
        clientFacade.deleteAll(this.selectedClientList);
        this.clientList.removeAll(this.selectedClientList);
        this.selectedClientList = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Clients supprimés", "Clients supprimés avec succés !"));
        PrimeFaces.current().ajax().update("form:client", "form:dt-client");
        PrimeFaces.current().executeScript("PF('dtClients').clearFilters()");
    }

    public void saveClient() {
        if (this.selectedClient.getId() == null) {
            Integer newId = clientFacade.getNextId();
            this.selectedClient.setId(newId);
            clientFacade.create(this.selectedClient);
            this.clientList.add(this.selectedClient);
            this.selectedClient = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client ajouté", "Client ajouté avec succés !"));
        }
        else {
            clientFacade.update(this.selectedClient);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mis à Jour", "Client mis à jour avec succés !"));
        }

        PrimeFaces.current().executeScript("PF('manageClientDialog').hide()");
        PrimeFaces.current().ajax().update("form:client", "form:dt-client");
    }
}
