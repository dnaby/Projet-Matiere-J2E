package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Personne implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "PRENOM", nullable = false)
    private String prenom;
    @Basic
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Basic
    @Column(name = "TELEPHONE", length = 25)
    private String telephone;
    @Basic
    @Column(name = "EMAIL")
    private String email;

}
