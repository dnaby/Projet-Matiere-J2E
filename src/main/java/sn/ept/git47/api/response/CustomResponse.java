package sn.ept.git47.api.response;

import jakarta.xml.bind.annotation.XmlRootElement;
import sn.ept.git47.entity.Marque;

@XmlRootElement(name = "customResponse")
public class CustomResponse {
    private String msg;

    public CustomResponse() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
