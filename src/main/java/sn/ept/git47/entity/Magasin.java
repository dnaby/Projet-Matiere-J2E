package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "magasin")
public class Magasin {
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
    private Collection<Stock> stocks;
    @OneToMany(mappedBy = "magasin")
    private Collection<Commande> commandes;
    @OneToMany(mappedBy = "magasin")
    private Collection<Employe> employes;

    public Magasin() {
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCodeZip() {
        return codeZip;
    }

    public void setCodeZip(String codeZip) {
        this.codeZip = codeZip;
    }

    @Override
    public String toString() {
        return "Magasin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", ville='" + ville + '\'' +
                ", etat='" + etat + '\'' +
                ", codeZip='" + codeZip + '\'' +
                ", stocks=" + stocks +
                ", commandes=" + commandes +
                '}';
    }
}
