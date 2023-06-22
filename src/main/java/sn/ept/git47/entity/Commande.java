package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "commande")
public class Commande implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "NUMERO", nullable = false)
    private Integer numero;
    @Basic
    @Column(name = "CLIENT_ID")
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
    @JoinColumn(name = "MAGASIN_ID", referencedColumnName = "ID")
    private Magasin magasin;
    @ManyToOne
    @JoinColumn(name = "VENDEUR_ID", referencedColumnName = "ID")
    private Employe vendeur;
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
    private Client client;
    @OneToMany(mappedBy = "commande")
    @JoinColumn(name = "ID", referencedColumnName = "NUMERO_COMMANDE")
    private Collection<ArticleCommande> article_commandes;
}
