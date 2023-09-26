package sn.ept.git47.api.response.marque;

import jakarta.xml.bind.annotation.XmlRootElement;
import sn.ept.git47.entity.Marque;

@XmlRootElement(name = "createMarqueResponse")
public class MarqueResponse {
    private String msg;
    private Marque marque;

    public MarqueResponse() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }
}
