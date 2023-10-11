package sn.ept.git47.api.response.location;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlRootElement;
import sn.ept.git47.entity.Location;

import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "locationRequest")
@Entity
public class LocationRequest implements Serializable {
    @Id
    private String androidID;
    private double latitude;
    private double longitude;
    private String timestamp;

    public LocationRequest() {}

    public String getAndroidID() {
        return androidID;
    }

    public void setAndroidID(String androidID) {
        this.androidID = androidID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Location convertToLocationEntity() {
        Location location = new Location();
        location.setAndroidID(androidID);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setTimestamp(new Date(timestamp));
        return location;
    }

    @Override
    public String toString() {
        return "Location{" +
                "androidID='" + androidID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp=" + timestamp +
                '}';
    }
}
