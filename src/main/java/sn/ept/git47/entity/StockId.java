package sn.ept.git47.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StockId implements Serializable {
    private Integer produitId;
    private Integer magasinId;

    public StockId() {
    }

    public StockId(Integer produitId, Integer magasinId) {
        this.produitId = produitId;
        this.magasinId = magasinId;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public Integer getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Integer magasinId) {
        this.magasinId = magasinId;
    }
}
