package sn.ept.git47.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@XmlRootElement(name = "produit")
@Entity
@Table(name = "produit")
public class Produit implements Serializable {
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
    @JoinColumn(name="MARQUE_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Marque marque;
    @ManyToOne
    @JoinColumn(name="CATEGORIE_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Categorie categorie;
    @JsonbTransient
    @OneToMany(mappedBy = "produit")
    @JoinColumn(updatable = false, insertable = false)
    private Collection<Stock> stocks;
    @JsonbTransient
    @OneToMany(mappedBy = "produit")
    @JoinColumn(updatable = false, insertable = false)
    private Collection<ArticleCommande> articleCommandes;

    public Produit() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getMarqueId() {
        return marqueId;
    }

    public void setMarqueId(Integer marqueId) {
        this.marqueId = marqueId;
    }

    public Integer getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }

    public Short getAnneeModel() {
        return anneeModel;
    }

    public void setAnneeModel(Short anneeModel) {
        this.anneeModel = anneeModel;
    }

    public BigDecimal getPrixDepart() {
        return prixDepart;
    }

    public void setPrixDepart(BigDecimal prixDepart) {
        this.prixDepart = prixDepart;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Collection<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Collection<Stock> stocks) {
        this.stocks = stocks;
    }

    public Collection<ArticleCommande> getArticleCommandes() {
        return articleCommandes;
    }

    public void setArticleCommandes(Collection<ArticleCommande> articleCommandes) {
        this.articleCommandes = articleCommandes;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", marqueId=" + marqueId +
                ", categorieId=" + categorieId +
                ", anneeModel=" + anneeModel +
                ", prixDepart=" + prixDepart +
                ", marque=" + marque +
                ", categorie=" + categorie +
                ", stocks=" + stocks +
                ", articleCommandes=" + articleCommandes +
                '}';
    }
}
