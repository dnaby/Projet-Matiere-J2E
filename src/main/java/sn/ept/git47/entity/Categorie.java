package sn.ept.git47.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "categorie")
public class Categorie implements Serializable {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "NOM", nullable = false)
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
