package sn.ept.git47.api.response.location;

import jakarta.xml.bind.annotation.XmlRootElement;
import sn.ept.git47.entity.Categorie;
import sn.ept.git47.entity.Location;

@XmlRootElement(name = "locationResponse")
public class LocationResponse {
    private String msg;
    private LocationRequest locationRequest;

    public LocationResponse() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocationRequest getLocation() {
        return locationRequest;
    }

    public void setLocation(LocationRequest location) {
        this.locationRequest = location;
    }
}
