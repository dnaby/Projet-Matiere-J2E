package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employe extends Personne {
    @Basic
    @Column(name = "ACTIF", nullable = false)
    private Byte actif;
    @Basic
    @Column(name = "MAGASIN_ID", nullable = false)
    private Integer magasinId;
    @Basic
    @Column(name = "MANAGER_ID")
    private Integer managerId;
    @ManyToOne
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "ID")
    private Employe manager;
    @OneToMany(mappedBy = "manager")
    @JoinColumn(name="ID", referencedColumnName = "MANAGER_ID")
    private Collection<Employe> employes_manager;
    @OneToMany(mappedBy = "vendeur")
    @JoinColumn(name = "ID", referencedColumnName = "VENDEUR_ID")
    private Collection<Commande> commandes;
}
