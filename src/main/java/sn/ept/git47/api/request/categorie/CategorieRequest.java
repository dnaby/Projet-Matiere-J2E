package sn.ept.git47.api.request.categorie;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "createCategorieRequest")
public class CategorieRequest {
    private String nom;

    public CategorieRequest() {
    }

    public CategorieRequest(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
