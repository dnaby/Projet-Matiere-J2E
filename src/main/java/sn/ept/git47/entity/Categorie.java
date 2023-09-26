package sn.ept.git47.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@XmlRootElement(name = "categorie")
@Entity
@Table(name = "categorie")
public class Categorie implements Serializable {
    @Id
    @Column(name = "ID", nullable = false)
    @Schema(description = "ID of the Categorie")
    private Integer id;
    @Basic
    @Column(name = "NOM", nullable = false)
    @Schema(description = "Name of the Categorie")
    private String nom;
    @OneToMany(mappedBy = "categorie")
    private Collection<Produit> produits;

    public Categorie() {
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
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", produits=" + produits +
                '}';
    }
}
