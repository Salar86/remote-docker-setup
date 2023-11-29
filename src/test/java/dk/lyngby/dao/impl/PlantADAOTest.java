package dk.lyngby.dao.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Plant;
import dk.lyngby.model.Reseller;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlantADAOTest {
    private static PlantADAO plantDAO;
    private static EntityManagerFactory emfTest;

    private static Plant p1, p2, p3;
    private static Reseller r1;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        plantDAO = new PlantADAO();
    }

    @BeforeEach
    void setUp() {
        try (var em = emfTest.createEntityManager())
        {
            em.getTransaction().begin();
            // Delete all rows
            em.createQuery("DELETE FROM Plant p").executeUpdate(); //Huske at sætte i rigtig rækkefølge således at den med foreign key bliver slettet først
            em.createQuery("DELETE FROM Reseller r").executeUpdate();
            // Reset sequence
            em.createNativeQuery("ALTER SEQUENCE plant_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE reseller_id_seq RESTART WITH 1").executeUpdate();
            // Insert test data
            p1 = new Plant("Rose", "Albertine", 199.50, 400);
            p2 = new Plant( "Bush", "Aronia", 169.50, 200);
            p3 = new Plant( "FruitAndBerries", "AromaApple", 399.50, 350);
            r1 = new Reseller("Lyngby Plantecenter", "Firskovvej 18", "33212334");
            p1.setReseller(r1);
            p2.setReseller(r1);
            p3.setReseller(r1);
            em.persist(r1);
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown() {

        HibernateConfig.setTest(false);
    }

    @Test
    void readAll() {
        List<Plant> expected = List.of(p1, p2, p3);
        List<Plant> actual = plantDAO.readAll();
        assertEquals(expected, actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void read() throws ApiException {
        Plant expected = new Plant("Rose", "Albertine", 199.50, 400);
        expected.setId(1);
        Plant actual = plantDAO.read(p1.getId());
        assertEquals(expected, actual);
    }
    @Test
    void readException() throws ApiException {
        assertThrows(ApiException.class, () -> plantDAO.read(999));
    }

    @Test
    void create() {
        Plant expected = new Plant("Rose", "Wild Rose", 99.50, 400);
        Plant actual = plantDAO.create(expected);
        assertEquals(expected, actual);
    }

    @Test
    void delete() throws ApiException {
        Plant actual = plantDAO.delete(p1.getId());
        assertThrows(ApiException.class, () -> plantDAO.read(p1.getId()));
        List<Plant> actualSize = plantDAO.readAll();
        assertEquals(2, actualSize.size());
    }

    @Test
    void update() {
        Plant expected = new Plant("Rose", "Wild Rose", 99.50, 400);
        expected.setId(p1.getId());
        Plant actual = plantDAO.update(p1.getId(), expected);
        assertEquals(expected, actual);
    }

    @Test
    void getByType() {
        List<Plant> expected = List.of(p1);
        List<Plant> actual = plantDAO.getByType("Rose");
        assertEquals(expected, actual);
    }

    @Test
    void addPlantToReseller() throws ApiException {
        Plant plant = new Plant("Rose", "Wild Rose", 99.50, 400);
        plantDAO.create(plant);
        Plant expected = plantDAO.read(plant.getId());
        plantDAO.addPlantToReseller(r1.getId(), expected.getId());
        Plant actual = plantDAO.read(plant.getId());
        assertEquals(expected, actual);

    }

    @Test
    void getPlantsByReseller() {
        List<Plant> expected = List.of(p1, p2, p3);
        List<Plant> actual = plantDAO.getPlantsByReseller(r1.getId());
        assertEquals(expected, actual);
    }

    @Test
    void add() {
        PlantDTO plantFromDTO = new PlantDTO("Rose", "Wild Rose", 99.50, 400);
        Plant expected = new Plant("Rose", "Wild Rose", 99.50, 400);
        expected.setId(4);
        Plant actual = plantDAO.add(plantFromDTO);
        assertEquals(expected, actual);
    }
}