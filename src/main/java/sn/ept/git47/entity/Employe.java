package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "employe")
@DiscriminatorValue("EMPLOYE")
public class Employe extends Personne implements Serializable {
    @Basic
    @Column(name = "ACTIF", nullable = false)
    private Byte actif;
    @Basic
    @Column(name = "MAGASIN_ID", nullable = false)
    private Integer magasinId;
    @Basic
    @Column(name = "MANAGER_ID")
    private Integer managerId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Employe manager;
    @ManyToOne
    @JoinColumn(name = "MAGASIN_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Magasin magasin;
    @OneToMany(mappedBy = "manager")
    @JoinColumn(name="ID", referencedColumnName = "MANAGER_ID", nullable = false, updatable = false, insertable = false)
    private Collection<Employe> employes_manager;
    @OneToMany(mappedBy = "vendeur")
    private Collection<Commande> commandes;

    public Employe() {
    }

    public Byte getActif() {
        return actif;
    }

    public void setActif(Byte actif) {
        this.actif = actif;
    }

    public Integer getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Integer magasinId) {
        this.magasinId = magasinId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Employe getManager() {
        return manager;
    }

    public void setManager(Employe manager) {
        this.manager = manager;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Collection<Employe> getEmployes_manager() {
        return employes_manager;
    }

    public void setEmployes_manager(Collection<Employe> employes_manager) {
        this.employes_manager = employes_manager;
    }

    public Collection<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(Collection<Commande> commandes) {
        this.commandes = commandes;
    }
}
