package dk.lyngby.dao.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.IDAO;
import dk.lyngby.model.Reseller;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ResellerADAO extends ADAO<Reseller, Integer> {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    @Override
    Class<Reseller> getEntityClass() {
        return Reseller.class;
    }

    @Override
    String getEntityName() {
        return "Reseller";
    }

    @Override
    void mergeEntity(Reseller existingEntity, Reseller reseller) {
        existingEntity.setName(reseller.getName());
        existingEntity.setAddress(reseller.getAddress());
        existingEntity.setPhone(reseller.getPhone());
    }
}
