package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sn.ept.git47.entity.Client;
import sn.ept.git47.entity.Employe;

import java.util.List;

@Stateless
public class ClientFacade extends AbstractFacade<Client> {
    public ClientFacade() {
        super(Client.class);
    }

    public List<Client> findAll() {
        TypedQuery<Client> query = getEntityManager().createQuery("SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }

    public Client findClientByEmail(String email) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("email"), email));

        TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
