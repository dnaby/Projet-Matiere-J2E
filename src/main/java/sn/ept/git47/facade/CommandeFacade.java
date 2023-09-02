package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import sn.ept.git47.entity.Commande;

@Stateless
public class CommandeFacade extends AbstractFacade<Commande> {
    public CommandeFacade() {
        super(Commande.class);
    }
}
