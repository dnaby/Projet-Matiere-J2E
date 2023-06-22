package sn.ept.git47.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Client extends Personne {
    @Basic
    @Column(name = "ADRESSE")
    private String adresse;
    @Basic
    @Column(name = "VILLE")
    private String ville;
    @Basic
    @Column(name = "ETAT")
    private String etat;
    @Basic
    @Column(name = "CODE_ZIP")
    private String codeZip;
    @OneToMany(mappedBy = "client")
    @JoinColumn(name = "ID", referencedColumnName = "CLIENT_ID")
    private Collection<Commande> commandes;
}
