package sn.ept.git47.api.request.marque;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "createMarqueRequest")
public class MarqueRequest {
    private String nom;

    public MarqueRequest() {
    }

    public MarqueRequest(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
