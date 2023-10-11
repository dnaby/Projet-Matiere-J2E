package sn.ept.git47.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "article_commande")
@Entity
@Table(name = "article_commande")
public class ArticleCommande implements Serializable {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "numero_commande", column = @Column(name = "NUMERO_COMMANDE")),
            @AttributeOverride(name = "ligne", column = @Column(name = "LIGNE"))
    })
    private ArticleCommandeId id;
    @Basic
    @Column(name = "PRODUIT_ID", nullable = false)
    private Integer produitId;
    @Basic
    @Column(name = "QUANTITE", nullable = false)
    private Integer quantite;
    @Basic
    @Column(name = "PRIX_DEPART", nullable = false, precision = 2)
    private BigDecimal prixDepart;
    @Basic
    @Column(name = "REMISE", nullable = false, precision = 2)
    private BigDecimal remise;
    @ManyToOne
    @JoinColumn(name = "NUMERO_COMMANDE", referencedColumnName = "NUMERO", nullable = false, insertable = false, updatable = false)
    private Commande commande;
    @ManyToOne
    @JoinColumn(name = "PRODUIT_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    private Produit produit;

    public ArticleCommande() {}

    public ArticleCommandeId getId() {
        return id;
    }

    public void setId(ArticleCommandeId id) {
        this.id = id;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixDepart() {
        return prixDepart;
    }

    public void setPrixDepart(BigDecimal prixDepart) {
        this.prixDepart = prixDepart;
    }

    public BigDecimal getRemise() {
        return remise;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }

    public Commande getCommande() {
        return commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ArticleCommande other = (ArticleCommande) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ArticleCommande{" +
                "id=" + id +
                ", produitId= '" + produitId + '\'' +
                ", quantite= '" + quantite + '\'' +
                ", prixDepart= '" + prixDepart + '\'' +
                ", remise= '" + remise + '\'' +
                '}';
    }

}
