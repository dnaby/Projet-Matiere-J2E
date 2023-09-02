package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "commande")
public class Commande implements Serializable {
    @Id
    @Column(name = "NUMERO", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "CLIENT_ID", nullable = false)
    private Integer clientId;
    @Basic
    @Column(name = "STATUT", nullable = false)
    private Byte statut;
    @Basic
    @Column(name = "DATE_COMMANDE", nullable = false)
    private Date dateCommande;
    @Basic
    @Column(name = "DATE_LIVRAISON_VOULUE", nullable = false)
    private Date dateLivraisonVoulue;
    @Basic
    @Column(name = "DATE_LIVRAISON", nullable = true)
    private Date dateLivraison;
    @Basic
    @Column(name = "MAGASIN_ID", nullable = false)
    private Integer magasinId;
    @Basic
    @Column(name = "VENDEUR_ID", nullable = false)
    private Integer vendeurId;
    @ManyToOne
    @JoinColumn(name = "MAGASIN_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Magasin magasin;
    @ManyToOne
    @JoinColumn(name = "VENDEUR_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Employe vendeur;
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Client client;
    @OneToMany(mappedBy = "commande")
    private Collection<ArticleCommande> article_commandes;

    public Commande() {
    }

    public Byte getStatut() {
        return statut;
    }

    public void setStatut(Byte statut) {
        this.statut = statut;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Date getDateLivraisonVoulue() {
        return dateLivraisonVoulue;
    }

    public void setDateLivraisonVoulue(Date dateLivraisonVoulue) {
        this.dateLivraisonVoulue = dateLivraisonVoulue;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Integer getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Integer magasinId) {
        this.magasinId = magasinId;
    }

    public Integer getVendeurId() {
        return vendeurId;
    }

    public void setVendeurId(Integer vendeurId) {
        this.vendeurId = vendeurId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Employe getVendeur() {
        return vendeur;
    }

    public void setVendeur(Employe vendeur) {
        this.vendeur = vendeur;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
