package sn.ept.git47.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "categorie")
public class Categorie implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "NOM", nullable = false)
    private String nom;
    @OneToMany(mappedBy = "categorie")
    @JoinColumn(name = "ID", referencedColumnName = "CATEGORIE_ID")
    private Collection<Produit> produits;
}
