package sn.ept.git47.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.io.Serializable;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Personne implements Serializable {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "PRENOM", nullable = false)
    private String prenom;
    @Basic
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Basic
    @Column(name = "TELEPHONE", length = 25)
    private String telephone;
    @Basic
    @Column(name = "EMAIL")
    @Email(message = "doit être un email valide !")
    private String email;

    public Personne() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
