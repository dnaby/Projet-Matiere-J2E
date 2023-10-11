package sn.ept.git47.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name = "employe")
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
}
