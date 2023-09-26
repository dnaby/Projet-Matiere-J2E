package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sn.ept.git47.entity.Categorie;
import sn.ept.git47.entity.Marque;

@Stateless
public class MarqueFacade extends AbstractFacade<Marque> {
    public MarqueFacade() {
        super(Marque.class);
    }

    public Marque findMarqueByNom(String nom) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Marque> criteriaQuery = criteriaBuilder.createQuery(Marque.class);
        Root<Marque> root = criteriaQuery.from(Marque.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("nom"), nom));

        TypedQuery<Marque> query = entityManager.createQuery(criteriaQuery);

        try {
            System.out.println(query.getResultList().toString());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
