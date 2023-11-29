package dk.lyngby.controller.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.model.Plant;
import dk.lyngby.model.Reseller;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlantControllerATest {
    private static Plant p1, p2, p3;
    private static Reseller r1;
    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7777/api/v1";
    private static PlantControllerA plantController;
    private static EntityManagerFactory emfTest;

    @BeforeAll
    static void beforeAll()
    {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        plantController = new PlantControllerA();
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);
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
            ApplicationConfig.stopServer(app);
    }

    @Test
    void read() {
        given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/plantsa/" + p1.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body().as(PlantDTO.class);
    }

    @Test
    void readWith404() {
        given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/plantsa/" + 999)
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void readAll() {
        List<PlantDTO> plantDTOList =
        given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/plantsa")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body().jsonPath().getList("", PlantDTO.class);
        assertEquals(3, plantDTOList.size());
    }

    @Test
    void create() {
        PlantDTO newPlant = new PlantDTO("Rose", "Wild Rose", 99.50, 400);
        given()
                .contentType("application/json")
                .body(newPlant)
                .when()
                .post(BASE_URL + "/plantsa")
                .then()
                .assertThat()
                .statusCode(201)
                .body("id", equalTo(4))
                .body("plantType", equalTo("Rose"))
                .body("name", equalTo("Wild Rose"))
                .body("price", equalTo(99.50f)) //It is treated as a float even though it is a double because of the DTO
                .body("maxHeight", equalTo(400))
                .extract()
                .body().as(PlantDTO.class);
    }

    @Test
    void readByType() {
        List<PlantDTO> plantDTOList =
                given()
                        .contentType("application/json")
                        .when()
                        .get(BASE_URL + "/plantsa/type/" + p1.getPlantType())
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .body("plantType", hasItem(p1.getPlantType()))
                        .extract()
                        .body().jsonPath().getList("", PlantDTO.class);
        assertEquals(1, plantDTOList.size());
    }
    @Test
    void update() {
        PlantDTO updatedPlant = new PlantDTO("Bush", "Aronia", 99.50, 200);
        given()
                .contentType("application/json")
                .body(updatedPlant)
                .log().all()
                .when()
                .put(BASE_URL + "/plantsa/" + p2.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(p2.getId()))
                .body("plantType", equalTo("Bush"))
                .body("name", equalTo("Aronia"))
                .body("price", equalTo(99.50f)) //It is treated as a float even though it is a double because of the DTO
                .body("maxHeight", equalTo(200));


    }

    @Test
    void addPlantToReseller() {
        given()
                .contentType("application/json")
                .when()
                .put(BASE_URL + "/plantsa/addReseller/" + p3.getId() + "/" + 1)
                .then()
                .assertThat()
                .statusCode(200);

    }

}