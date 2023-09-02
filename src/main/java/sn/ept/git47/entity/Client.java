package sn.ept.git47.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessorType;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "client")
@DiscriminatorValue("CLIENT")
public class Client extends Personne implements Serializable {
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
    @OneToMany(mappedBy = "client")
    private Collection<Commande> commandes;

    public Client() {
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
}
