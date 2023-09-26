package sn.ept.git47.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@XmlRootElement(name = "marque")
@Entity
@Table(name = "marque")
public class Marque implements Serializable {
    @Id
    @Column(name = "ID", nullable = false)
    @Schema(description = "ID of the Marque")
    private Integer id;
    @Basic
    @Column(name = "NOM", nullable = false)
    @Schema(description = "Name of the Marque")
    private String nom;
    @OneToMany(mappedBy = "marque")
    @JoinColumn(insertable = false, updatable = false)
    private Collection<Produit> produits;

    public Marque() {}

    public Marque(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
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

    @Override
    public String toString() {
        return "Marque{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", produits=" + produits +
                '}';
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
        Marque other = (Marque) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
