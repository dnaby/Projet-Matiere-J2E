package sn.ept.git47.api.response.categorie;

import jakarta.xml.bind.annotation.XmlRootElement;
import sn.ept.git47.entity.Categorie;
import sn.ept.git47.entity.Marque;

@XmlRootElement(name = "createCategorieResponse")
public class CategorieResponse {
    private String msg;
    private Categorie categorie;

    public CategorieResponse() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
