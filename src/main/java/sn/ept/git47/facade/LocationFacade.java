package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sn.ept.git47.entity.Location;
import sn.ept.git47.entity.Marque;

@Stateless
public class LocationFacade extends AbstractFacade<Location> {
    public LocationFacade() {
        super(Location.class);
    }
}
