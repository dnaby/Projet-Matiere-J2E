package sn.ept.git47.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stock")
@NamedQuery(
        name = "Stock.getStockStatus",
        query = "SELECT s.produit.nom, s.quantite FROM Stock s GROUP BY s.produit.nom"
)
public class Stock implements Serializable {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "produitId", column = @Column(name = "PRODUIT_ID", insertable = true, updatable = true)),
            @AttributeOverride(name = "magasinId", column = @Column(name = "MAGASIN_ID", insertable = true, updatable = true))
    })
    private StockId id;
    @Basic
    @Column(name = "QUANTITE", nullable = false)
    private Integer quantite;
    @ManyToOne
    @JoinColumn(name = "PRODUIT_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Produit produit;
    @ManyToOne
    @JoinColumn(name = "MAGASIN_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Magasin magasin;

    public Stock() {
    }

    public StockId getId() {
        return id;
    }

    public void setId(StockId id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {return produit;}

    public void setProduit(Produit produit) {this.produit = produit;}

    public Magasin getMagasin() {return magasin;}

    public void setMagasin(Magasin magasin) {this.magasin = magasin;}

    @Override
    public String toString() {
        return "Stock{" +
                "quantite=" + quantite +
                ", produit=" + produit.getNom() +
                ", magasin=" + magasin.getNom() +
                '}';
    }
}
