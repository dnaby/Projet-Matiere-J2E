package sn.ept.git47.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ArticleCommandeId implements Serializable {
    private Integer numero_commande;
    private Integer ligne;

    public ArticleCommandeId() {
    }

    public ArticleCommandeId(Integer numero_commande, Integer ligne) {
        this.numero_commande = numero_commande;
        this.ligne = ligne;
    }

    public Integer getNumero_commande() {
        return numero_commande;
    }

    public void setNumero_commande(Integer numero_commande) {
        this.numero_commande = numero_commande;
    }

    public Integer getLigne() {
        return ligne;
    }

    public void setLigne(Integer ligne) {
        this.ligne = ligne;
    }
}
