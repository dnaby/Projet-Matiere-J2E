package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "produit")
public class Produit implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Basic
    @Column(name = "MARQUE_ID", nullable = false)
    private Integer marqueId;
    @Basic
    @Column(name = "CATEGORIE_ID", nullable = false)
    private Integer categorieId;
    @Basic
    @Column(name = "ANNEE_MODEL", nullable = false)
    private Short anneeModel;
    @Basic
    @Column(name = "PRIX_DEPART", nullable = false, precision = 2)
    private BigDecimal prixDepart;
    @ManyToOne
    @JoinColumn(name="MARQUE_ID", referencedColumnName = "ID")
    private Marque marque;
    @ManyToOne
    @JoinColumn(name="CATEGORIE_ID", referencedColumnName = "ID")
    private Categorie categorie;
    @OneToMany(mappedBy = "produit")
    @JoinColumn(name = "ID", referencedColumnName = "PRODUIT_ID")
    private Collection<Stock> stocks;
    @OneToMany(mappedBy = "produit")
    @JoinColumn(name = "ID", referencedColumnName = "PRODUIT_ID")
    private Collection<ArticleCommande> articleCommandes;
}
