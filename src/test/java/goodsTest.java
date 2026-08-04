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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

    public String randomName() {
        List<String> koncelyrList = new ArrayList<>(List.of(
                "Ручка",
                "Карандаш",
                "Ластик",
                "Линейка",
                "Тетрадь",
                "Маркер",
                "Скрепки",
                "Степлер",
                "Ножницы",
                "Клей"
        ));
        return koncelyrList.get(random.nextInt(0, 10)) + String.valueOf(random.nextInt(100, 10000));
    }

    public Double randomPrice() {

        return BigDecimal.valueOf(random.nextDouble(0, 10000))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public void searchAndDeleteGoods() {

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

        searchAndDeleteGoods();
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

    @Test
    @Tag("RestAssured")
    public void addGoods_200_Test() {


        Response response = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request(randomName(), randomPrice()))
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.jsonPath().getString("message"))
                .as("Новый товар не добавился")
                .contains("success");
    }

    @Test
    @Tag("RestAssured")
    public void addGoods_400_Test() {


        Response response = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request(randomName(), -1 - randomPrice()))
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.jsonPath().getString("price"))
                .as("Не сработало ограничение на создание товара с отрицательной ценой")
                .contains("Price can not be less, than 0");
    }

    @Test
    @Tag("RestAssured")
    public void getGoodsId_200_Test() {
        String name = randomName();
        double price = randomPrice();

        Response responsePost = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request(name, price))
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();

        int idGoods = responsePost.jsonPath().getInt("data.id");

        Response responseGet = given()
                .spec(basicRQ)
                .pathParam("id", idGoods)
                .get("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        assertThat(responseGet.jsonPath().getInt("id"))
                .as("Не верно вернулся id товара")
                .isEqualTo(idGoods);
        assertThat(responseGet.jsonPath().getString("name"))
                .as("Не верно вернулся name товара")
                .isEqualTo(name);
        assertThat(responseGet.jsonPath().getDouble("price"))
                .as("Не верно вернулся price товара")
                .isEqualTo(price);
    }

    @Test
    @Tag("RestAssured")
    public void getGoodsId_404_Test() {

        int idGoods = random.nextInt(0, 100);
        Response responseGet = given()
                .spec(basicRQ)
                .pathParam("id", idGoods)
                .get("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        assertThat(responseGet.jsonPath().getString("message"))
                .as("Не верно вывелось сообщение для отсутствующего товара с и: " + idGoods)
                .isEqualTo("Good with id '" +idGoods+ "' is not found!");
        assertThat(responseGet.statusCode())
                .as("Не верный статус код для отсутствующего товара")
                .isEqualTo(404);
    }

    @Test
    @Tag("RestAssured")
    public void deleteGoodsId_200_Test() {
        String name = randomName();
        double price = randomPrice();

        Response responsePost = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request(name, price))
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();

        int idGoods = responsePost.jsonPath().getInt("data.id");

        Response responseGet = given()
                .spec(basicRQ)
                .pathParam("id", idGoods)
                .delete("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        assertThat(responseGet.asString())
                .as("Не верно сработало удаление для ид: " + idGoods)
                .isEqualTo("Good with id '" +idGoods+ "' has been deleted successfully!");
    }

    @Test
    @Tag("RestAssured")
    public void deleteGoodsId_404_Test() {

        int idGoods = random.nextInt(0, 100);
        Response responseGet = given()
                .spec(basicRQ)
                .pathParam("id", idGoods)
                .delete("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        assertThat(responseGet.asString())
                .as("Не верно вывелось сообщение для удаления отсутствующего товара с и: " + idGoods)
                .isEqualTo("Good with id '" +idGoods+ "' is not found");
        assertThat(responseGet.statusCode())
                .as("Не верный статус код для удаления отсутствующего товара")
                .isEqualTo(404);
    }

    @Test
    @Tag("RestAssured")
    public void patchGoodsId_200_Test() {
        String name = randomName();
        double price = randomPrice();

        Response responsePost = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request(name, price))
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();

        int idGoods = responsePost.jsonPath().getInt("data.id");
         String newName = randomName();
         double newPrice = randomPrice();
        Response responseGet = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .pathParam("id", idGoods)
                .body(new Request(newName, newPrice))
                .patch("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        assertThat(responseGet.jsonPath().getInt("id"))
                .as("Не верно вернулся id товара")
                .isEqualTo(idGoods);
        assertThat(responseGet.jsonPath().getString("name"))
                .as("Не верно вернулся name товара")
                .isEqualTo(newName);
        assertThat(responseGet.jsonPath().getDouble("price"))
                .as("Не верно вернулся price товара")
                .isEqualTo(newPrice);
    }

    @Test
    @Tag("RestAssured")
    public void patchGoodsId_400_Test() {
        String name = randomName();
        double price = randomPrice();

        Response responsePost = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request(name, price))
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();

        int idGoods = responsePost.jsonPath().getInt("data.id");
        String newName = randomName();
        double newPrice = randomPrice();
        Response responseGet = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .pathParam("id", idGoods)
                .body(new Request(newName, -1 - newPrice))
                .patch("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        assertThat(responseGet.jsonPath().getString("price"))
                .as("Не сработало ограничение на изменение товара с отрицательной ценой")
                .contains("Price can not be less, than 0");
    }

    @Test
    @Tag("RestAssured")
    public void patchGoodsId_404_Test() {


        int idGoods = random.nextInt(0, 100);
        String Name = randomName();
        double Price = randomPrice();
        Response responseGet = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .pathParam("id", idGoods)
                .body(new Request(Name, Price))
                .patch("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        assertThat(responseGet.asString())
                .as("Не верно вывелось сообщение для изменения отсутствующего товара с и: " + idGoods)
                .isEqualTo("Good with id '" +idGoods+ "' is not found");
        assertThat(responseGet.statusCode())
                .as("Не верный статус код для изменения отсутствующего товара")
                .isEqualTo(404);
    }

    @Test
    @Tag("RestAssured")
    public void getGoodsList_200_Test() {
        String name = randomName();
        double price = randomPrice();

        Response responsePost = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request(name, price))
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();

        int idGoods = responsePost.jsonPath().getInt("data.id");

        Response responseGet = given()
                .spec(basicRQ)
                .get("/goods/list")
                .then()
                .log().all()
                .extract().response();

        assertThat(responseGet.jsonPath().getList("goods.id",Integer.class))
                .as("не вернулся объект с Ид: " + idGoods)
                .contains(idGoods);
        assertThat(responseGet.jsonPath().getList("goods.name", String.class))
                .as("не вернулся объект с name: " + name)
                .contains(name);
        assertThat(responseGet.jsonPath().getList("goods.price", Double.class))
                .as("не вернулся объект с price: " + price)
                .contains(price);
    }
}
