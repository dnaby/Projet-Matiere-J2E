package sn.ept.git47.carousel;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class PhotoService {

    private List<Photo> photos;

    @PostConstruct
    public void init() {
        photos = new ArrayList<>();

        photos.add(new Photo("images/galleria1.jpg", "images/galleria1s.jpg",
                "Description for Image 1", "Title 1"));
        photos.add(new Photo("images/galleria2.jpg", "images/galleria2s.jpg",
                "Description for Image 2", "Title 2"));
        photos.add(new Photo("images/galleria3.jpg", "images/galleria3s.jpg",
                "Description for Image 3", "Title 3"));
        photos.add(new Photo("images/galleria4.jpg", "images/galleria4s.jpg",
                "Description for Image 4", "Title 4"));
        photos.add(new Photo("images/galleria5.jpg", "images/galleria5s.jpg",
                "Description for Image 5", "Title 5"));
        photos.add(new Photo("images/galleria6.jpg", "images/galleria6s.jpg",
                "Description for Image 6", "Title 6"));
        photos.add(new Photo("images/galleria7.jpg", "images/galleria7s.jpg",
                "Description for Image 7", "Title 7"));
        photos.add(new Photo("images/galleria8.jpg", "images/galleria8s.jpg",
                "Description for Image 8", "Title 8"));
        photos.add(new Photo("images/galleria9.jpg", "images/galleria9s.jpg",
                "Description for Image 9", "Title 9"));
        photos.add(new Photo("images/galleria10.jpg", "images/galleria10s.jpg",
                "Description for Image 10", "Title 10"));
        photos.add(new Photo("images/galleria11.jpg", "images/galleria11s.jpg",
                "Description for Image 11", "Title 11"));
        photos.add(new Photo("images/galleria12.jpg", "images/galleria12s.jpg",
                "Description for Image 12", "Title 12"));
        photos.add(new Photo("images/galleria13.jpg", "images/galleria13s.jpg",
                "Description for Image 13", "Title 13"));
        photos.add(new Photo("images/galleria14.jpg", "images/galleria14s.jpg",
                "Description for Image 14", "Title 14"));
        photos.add(new Photo("images/galleria15.jpg", "images/galleria15s.jpg", "Description for Image 15", "Title 15"));
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
