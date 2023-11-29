package dk.lyngby.dao.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.IDAO;
import dk.lyngby.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.List;

public abstract class ADAO<Entity, ID> implements IDAO<Entity, ID> {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public Entity read(ID id) throws ApiException {

        try (EntityManager em = emf.createEntityManager()) {
            Entity entity = em.find(getEntityClass(), id);
            if (entity == null) {
                throw new ApiException(404, getEntityName() + " not found", LocalDateTime.now().toString());
            }
            return entity;
        }
    }


    public List<Entity> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT e FROM " + getEntityName() + " e", getEntityClass());
            return (query.getResultList());
        }
    }

    public Entity create(Entity entity) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        }
    }

    public Entity update(ID id, Entity entity) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Entity existingEntity = em.find(getEntityClass(), id);
            if (existingEntity != null) {
                // Update entity properties here
                mergeEntity(existingEntity, entity);
                em.merge(existingEntity);
            }
            em.getTransaction().commit();
            return existingEntity;
        }
    }


    public Entity delete(ID id) {
        Entity entity;
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            entity = em.find(getEntityClass(), id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        }
        return entity;
    }

    abstract Class<Entity> getEntityClass();

    abstract String getEntityName();

    abstract void mergeEntity(Entity existingEntity, Entity entity);

}
