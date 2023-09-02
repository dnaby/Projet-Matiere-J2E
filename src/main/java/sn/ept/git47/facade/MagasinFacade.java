package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sn.ept.git47.entity.Magasin;
import sn.ept.git47.entity.Marque;

@Stateless
public class MagasinFacade extends AbstractFacade<Magasin> {
    public MagasinFacade() {
        super(Magasin.class);
    }

    public Magasin findMagasinByNom(String nom) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Magasin> criteriaQuery = criteriaBuilder.createQuery(Magasin.class);
        Root<Magasin> root = criteriaQuery.from(Magasin.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("nom"), nom));

        TypedQuery<Magasin> query = entityManager.createQuery(criteriaQuery);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
