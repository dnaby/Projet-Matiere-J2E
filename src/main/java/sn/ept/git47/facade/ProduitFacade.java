package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sn.ept.git47.entity.Magasin;
import sn.ept.git47.entity.Produit;

@Stateless
public class ProduitFacade extends AbstractFacade<Produit> {
    public ProduitFacade() {
        super(Produit.class);
    }

    public Produit findProduitByNom(String nom) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> criteriaQuery = criteriaBuilder.createQuery(Produit.class);
        Root<Produit> root = criteriaQuery.from(Produit.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("nom"), nom));

        TypedQuery<Produit> query = entityManager.createQuery(criteriaQuery);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
