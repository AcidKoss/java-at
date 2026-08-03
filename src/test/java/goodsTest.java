import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class goodsTest {


    static final Random random = new Random();
    static final int repeatedTest = 10;
    private RequestSpecification basicRQ;

    public record Request(String name, Double price) {
    }

    @BeforeEach
    public void setUp() {
        System.out.println("========================\n" +
                "\n" +
                "Test method start");
        basicRQ = new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080")
                .log(LogDetail.ALL)
                .build();

        basicRQ.auth()
                .basic("admin", "secret123");

    }

    @AfterEach
    public void last() {
        System.out.println("Test method end\n" +
                "\n" +
                "========================");

        Response response = given()
                .spec(basicRQ)
                .get("/goods/list")
                .then()
                .log().all()
                .extract().response();

        List<Integer> ids = response.jsonPath()
                .getList("goods.id");
        if (!ids.isEmpty()) {
            System.out.println("Список ИД для удаления: " + ids);

            for (Integer id : ids) {
                given()
                        .spec(basicRQ)
                        .pathParam("id", id)
                        .delete("/goods/{id}")
                        .then()
                        .log().all();
            }
        }
    }

    @Test
    @Tag("RestAssured")
    public void variant_1_Test() {
        given()
                .baseUri("http://localhost:8080")
                .auth()
                .basic("admin", "secret123")
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    @Tag("RestAssured")
    public void variant_2_Test() {
        given()
                .spec(basicRQ)
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods", empty());
        ;
    }

    @Test
    @Tag("RestAssured")
    public void variant_3_Test() {


        given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request("Ручка", 45.10))
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);

        given()
                .spec(basicRQ)
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem("Ручка"));
    }

    @Test
    @Tag("RestAssured")
    public void variant_4_Test() {


        given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request("Ручка", 45.10))
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);

        Response response = given()
                .spec(basicRQ)
                .get("/goods/list")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.jsonPath().getList("goods.name"))
                .as("Объект с названием Ручка не создался")
                .contains("Ручка");
    }
}
