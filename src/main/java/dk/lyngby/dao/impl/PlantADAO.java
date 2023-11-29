package dk.lyngby.dao.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.model.Plant;
import dk.lyngby.model.Reseller;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PlantADAO extends ADAO<Plant, Integer> {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    @Override
    Class<Plant> getEntityClass() {
        return Plant.class;
    }

    @Override
    String getEntityName() {
        return "Plant";
    }

    @Override
    void mergeEntity(Plant existingEntity, Plant plant) {
        existingEntity.setName(plant.getName());
        existingEntity.setPlantType(plant.getPlantType());
        existingEntity.setPrice(plant.getPrice());
        existingEntity.setMaxHeight(plant.getMaxHeight());

    }
    public List<Plant> getByType(String type) {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT p FROM Plant p WHERE p.plantType = :type", Plant.class);
            query.setParameter("type", type);
            return query.getResultList();
        }
    }

    public Reseller addPlantToReseller(int resellerId, int plantId) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            var reseller = em.find(Reseller.class, resellerId);
            var plant = em.find(Plant.class, plantId);
            reseller.addPlant(plant);
            em.persist(plant);
            Reseller merge = em.merge(reseller);
            em.getTransaction().commit();
            return merge;
        }
    }

    public List<Plant> getPlantsByReseller(int resellerId) {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT p FROM Plant p WHERE p.reseller.id = :id", Plant.class);
            query.setParameter("id", resellerId);
            return query.getResultList();
        }
    }

    public Plant add(PlantDTO plant) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            var ent = new Plant();
            ent.setPlantType(plant.getPlantType());
            ent.setName(plant.getName());
            ent.setPrice(plant.getPrice());
            ent.setMaxHeight(plant.getMaxHeight());
            em.persist(ent);
            em.getTransaction().commit();
            return ent;
        }
    }
}
