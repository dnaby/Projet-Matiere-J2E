package sn.ept.git47.facade;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public abstract class AbstractFacade<T> {
    @PersistenceContext(name = "projet_matiere")
    private EntityManager entityManager;
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {this.entityClass = entityClass;}

    public EntityManager getEntityManager() {return entityManager;}

    public void create(T entity) {getEntityManager().persist(entity);}

    public void update(T entity) {getEntityManager().merge(entity);}

    public void delete(T entity) {getEntityManager().remove(getEntityManager().merge(entity));}

    public void deleteAll(List<T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    public List<T> findAll() {
        CriteriaQuery criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    public Integer getNextId() {
        CriteriaQuery<Integer> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(Integer.class);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(getEntityManager().getCriteriaBuilder().max(root.get("id")).as(Integer.class));
        Query query = getEntityManager().createQuery(criteriaQuery);
        Integer maxId = (Integer) query.getSingleResult();
        if (maxId == null) {
            return 1;
        } else {
            return maxId + 1;
        }
    }
}
