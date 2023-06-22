package sn.ept.git47.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "marque")
public class Marque implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "NOM", nullable = false)
    private String nom;
    @OneToMany(mappedBy = "marque")
    @JoinColumn(name = "ID", referencedColumnName = "MARQUE_ID")
    private Collection<Produit> produits;
}
