package sn.ept.git47.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Collection;

@XmlRootElement(name = "magasin")
@Entity
@Table(name = "magasin")
public class Magasin {
    @Id
    @Column(name = "ID", nullable = false)
    @Schema(description = "ID of the Store")
    private Integer id;
    @Basic
    @Column(name = "NOM", nullable = false)
    @Schema(description = "Name of the Store")
    private String nom;
    @Basic
    @Column(name = "TELEPHONE", length = 25)
    @Schema(description = "Call Number where to join the Store")
    private String telephone;
    @Basic
    @Column(name = "EMAIL")
    @Schema(description = "Email where to join the Store")
    private String email;
    @Basic
    @Column(name = "ADRESSE")
    @Schema(description = "Address of the Store")
    private String adresse;
    @Basic
    @Column(name = "VILLE")
    @Schema(description = "City location of the store")
    private String ville;
    @Basic
    @Column(name = "ETAT")
    @Schema(description = "State location of the Store")
    private String etat;
    @Basic
    @Column(name = "CODE_ZIP")
    @Schema(description = "Zip code of the Store")
    private String codeZip;
    @OneToMany(mappedBy = "magasin")
    private Collection<Stock> stocks;
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
                '}';
    }
}
