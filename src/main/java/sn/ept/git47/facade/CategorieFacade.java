package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sn.ept.git47.entity.Categorie;

@Stateless
public class CategorieFacade extends AbstractFacade<Categorie> {
    public CategorieFacade() {
        super(Categorie.class);
    }

    public Categorie findCategorieByNom(String nom) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Categorie> criteriaQuery = criteriaBuilder.createQuery(Categorie.class);
        Root<Categorie> root = criteriaQuery.from(Categorie.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("nom"), nom));

        TypedQuery<Categorie> query = entityManager.createQuery(criteriaQuery);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
