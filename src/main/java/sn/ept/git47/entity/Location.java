package sn.ept.git47.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import sn.ept.git47.api.response.location.LocationRequest;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@XmlRootElement(name = "location")
@Entity
public class Location implements Serializable {
    @Id
    private String androidID;
    private double latitude;
    private double longitude;
    private Date timestamp;

    public Location() {}

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public LocationRequest convertToLocationRequest() {
        LocationRequest location = new LocationRequest();
        location.setAndroidID(androidID);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setTimestamp(timestamp.toString());
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
