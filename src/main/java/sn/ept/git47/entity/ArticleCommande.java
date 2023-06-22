package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
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
    @JoinColumn(name = "NUMERO_COMMANDE", referencedColumnName = "ID")
    private Commande commande;
    @ManyToOne
    @JoinColumn(name = "PRODUIT_ID", referencedColumnName = "ID")
    private Produit produit;
}
