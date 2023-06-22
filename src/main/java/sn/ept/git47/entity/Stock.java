package sn.ept.git47.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stock")
public class Stock implements Serializable {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "produitId", column = @Column(name = "PRODUIT_ID")),
            @AttributeOverride(name = "magasinId", column = @Column(name = "MAGASIN_ID"))
    })
    private StockId id;
    @Basic
    @Column(name = "QUANTITE", nullable = false)
    private Integer quantite;
    @ManyToOne
    @JoinColumn(name = "PRODUIT_ID", referencedColumnName = "ID")
    private Produit produit;
    @ManyToOne
    @JoinColumn(name = "MAGASIN_ID", referencedColumnName = "ID")
    private Magasin magasin;
}
