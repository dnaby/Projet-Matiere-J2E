package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sn.ept.git47.entity.Employe;

import java.util.List;

@Stateless
public class EmployeFacade extends AbstractFacade<Employe> {
    public EmployeFacade() {
        super(Employe.class);
    }

    public List<Employe> findAllEmployes() {
        TypedQuery<Employe> query = getEntityManager().createQuery("SELECT e FROM Employe e", Employe.class);
        return query.getResultList();
    }

    public Employe findEmployeByEmail(String email) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employe> criteriaQuery = criteriaBuilder.createQuery(Employe.class);
        Root<Employe> root = criteriaQuery.from(Employe.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("email"), email));

        TypedQuery<Employe> query = entityManager.createQuery(criteriaQuery);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
