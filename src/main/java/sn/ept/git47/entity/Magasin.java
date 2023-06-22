package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "magasin")
public class Magasin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Basic
    @Column(name = "TELEPHONE", length = 25)
    private String telephone;
    @Basic
    @Column(name = "EMAIL")
    private String email;
    @Basic
    @Column(name = "ADRESSE")
    private String adresse;
    @Basic
    @Column(name = "VILLE")
    private String ville;
    @Basic
    @Column(name = "ETAT")
    private String etat;
    @Basic
    @Column(name = "CODE_ZIP")
    private String codeZip;
    @OneToMany(mappedBy = "magasin")
    @JoinColumn(name = "ID", referencedColumnName = "MAGASIN_ID")
    private Collection<Stock> stocks;
    @OneToMany(mappedBy = "magasin")
    @JoinColumn(name = "ID", referencedColumnName = "MAGASIN_ID")
    private Collection<Commande> commandes;
}
