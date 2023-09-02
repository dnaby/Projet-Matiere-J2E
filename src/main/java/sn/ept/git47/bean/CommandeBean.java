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

@Named("commandeBean")
@ViewScoped
public class CommandeBean implements Serializable {
    @EJB
    private CommandeFacade commandeFacade;
    @EJB
    private MagasinFacade magasinFacade;
    @EJB
    private ClientFacade clientFacade;
    @EJB
    private EmployeFacade employeFacade;
    private List<Commande> commandeList;
    private List<Commande> selectedCommandeList;
    private Commande selectedCommande;

    @PostConstruct
    public void init() {
        commandeList = commandeFacade.findAll();
    }

    public List<Commande> getCommandeList() {return commandeList;}

    public void setCommandeList(List<Commande> commandeList) {this.commandeList = commandeList;}

    public List<Commande> getSelectedCommandeList() {return selectedCommandeList;}

    public void setSelectedCommandeList(List<Commande> selectedCommandeList) {this.selectedCommandeList = selectedCommandeList;}

    public Commande getSelectedCommande() {return selectedCommande;}

    public void setSelectedCommande(Commande selectedCommande) {this.selectedCommande = selectedCommande;}

    public void openNewCommande() {
        selectedCommande = new Commande();
        Client client = new Client();
        client.setEmail("");
        selectedCommande.setClient(client);
        Employe vendeur = new Employe();
        vendeur.setEmail("");
        selectedCommande.setVendeur(vendeur);
        Magasin magasin = new Magasin();
        magasin.setNom("");
        selectedCommande.setMagasin(magasin);
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCommandeList()) {
            int size = this.selectedCommandeList.size();
            return size > 1 ? size + " commandes selectionnées" : "1 commande selectionnée";
        }
        return "Supprimer";
    }

    public boolean hasSelectedCommandeList() {
        return this.selectedCommandeList != null && !this.selectedCommandeList.isEmpty();
    }

    public void deleteCommande() {
        commandeFacade.delete(selectedCommande);
        commandeList.remove(selectedCommande);
        selectedCommande = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression", "Commande supprimée avec succés !"));
        PrimeFaces.current().ajax().update("form:commande", "form:dt-commande");
    }

    public void deleteSelectedCommandeList() {
        commandeFacade.deleteAll(this.selectedCommandeList);
        this.commandeList.removeAll(this.selectedCommandeList);
        this.selectedCommandeList = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression", "Commandes supprimées avec succés !"));
        PrimeFaces.current().ajax().update("form:commande", "form:dt-commande");
        PrimeFaces.current().executeScript("PF('dtCommandes').clearFilters()");
    }

    public void saveCommande() {
        if (this.selectedCommande.getId() == null) {
            Integer newId = commandeFacade.getNextId();
            this.selectedCommande.setId(newId);
            boolean isUpdated = updateCommande();

            if (isUpdated) {
                commandeFacade.create(this.selectedCommande);
                this.commandeList.add(this.selectedCommande);
                this.selectedCommande = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajout", "Commande ajoutée avec succés !"));
            }
        } else {
            boolean isUpdated = updateCommande();

            if (isUpdated) {
                commandeFacade.update(this.selectedCommande);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mis à Jour", "Commande mise à jour avec succés !"));
            }
        }

        PrimeFaces.current().executeScript("PF('manageCommandeDialog').hide()");
        PrimeFaces.current().ajax().update("form:commande", "form:dt-commande");
    }

    public boolean updateCommande() {
        Magasin magasin = magasinFacade.findMagasinByNom(this.selectedCommande.getMagasin().getNom());
        Client client = clientFacade.findClientByEmail(this.selectedCommande.getClient().getEmail());
        Employe vendeur = employeFacade.findEmployeByEmail(this.selectedCommande.getVendeur().getEmail());

        if (magasin == null || client == null || vendeur == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir parmi les noms des magasins et/ou clients et/ou vendeurs existants !"));
            return false;
        } else {
            this.selectedCommande.setMagasin(magasin);
            this.selectedCommande.setMagasinId(magasin.getId());

            this.selectedCommande.setClient(client);
            this.selectedCommande.setClientId(client.getId());

            this.selectedCommande.setVendeur(vendeur);
            this.selectedCommande.setVendeurId(vendeur.getId());

            return true;
        }
    }

    public List<String> completeMagasin(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Magasin> magasins = magasinFacade.findAll();

        return magasins.stream().map(Magasin::getNom).filter(nom -> nom.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public List<String> completeVendeurs(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Employe> vendeurs = employeFacade.findAllEmployes();

        return vendeurs.stream()
                .map(Personne::getEmail)
                .filter(email -> email.toLowerCase().startsWith(queryLowerCase))
                .collect(Collectors.toList());
    }

    public List<String> completeClients(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Client> clients = clientFacade.findAll();

        return clients.stream()
                .map(Personne::getEmail)
                .filter(email -> email.toLowerCase().startsWith(queryLowerCase))
                .collect(Collectors.toList());
    }
}
