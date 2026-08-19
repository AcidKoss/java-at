import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class SelenideTask3Test {

    String login = "admin";
    String pass = "secret123";
    String loginInvalid = "abra";
    String passInvalid = "cadabra";

    String adminButtonMain = "//a[@href='/admin']";
    String basketButtonMain = "//button[@id='open-cart-btn']";
    String productCardMain = "//div[@class='product-card']";
    String addToBasketMain = "//div[@class='product-card']//button[@data-action='add-to-cart']";
    String makeOrderMain = "//button[@id='makeOrder']";
    String totalPriceMain = "//span[@id='total-price']";
    String toastMain = "//div[@class='toast']";

    String inputLoginSignIn = "//input[@id='username']";
    String inputPassSignIn = "//input[@id='password']";
    String buttonSignIn = "//button[text()='Sign in']";
    String errorAlertSignIn = "//div[@class='alert alert-danger']";

    String nameProductAdmin = "//input[@id='n-name']";
    String priceProductAdmin = "//input[@id='n-price']";
    String existsNameProductAdmin = "//tbody[@id='tbody']//input[@type='text']";
    String existsPriceProductAdmin = "//tbody[@id='tbody']//input[@type='number']";

    String buttonCreateAdmin = "//button[@id='add-btn']";
    String buttonDeleteAdmin = "//button[@data-action='delete']";
    String buttonUpdateAdmin = "//button[@data-action='update']";
    String tableProductAdmin = "//tbody[@id='tbody']";
    String backToMain = "//a[@href='/']";

    String nameProduct = "Стакан";
    String priceProduct = "99";
    String nameProduct1 = "Кружка";
    String priceProduct1 = "54";

    private RequestSpecification basicRQ;

    public record Request(String name, Double price) {
    }

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:8080");
        basicRQ = new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080")
                .log(LogDetail.ALL)
                .build();

        basicRQ.auth()
                .basic("admin", "secret123");
    }

    @AfterEach
    public void last() {
        Selenide.open("http://localhost:8080/admin");
        sleep(1000);
        ElementsCollection deleteButtons = $$x(buttonDeleteAdmin);

        for (int i = deleteButtons.size(); i > 0 ; i--) {
            deleteButtons.first().click();
            Selenide.switchTo().alert().accept();
            $x(backToMain).should(Condition.visible);
            sleep(500);
        }
    }


    @Test
    @Tag("Selenide")
    public void notificOrderProcessTest() {
        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(nameProductAdmin).sendKeys(nameProduct);
        $x(priceProductAdmin).sendKeys(priceProduct);
        $x(buttonCreateAdmin).click();
        $x(tableProductAdmin).should(Condition.visible);
        $x(backToMain).click();
        for (int i = 0; i < 3 ; i++) {
            $x(addToBasketMain).click();
        }
        $x(basketButtonMain).click();
        $x(makeOrderMain).click();

        ElementsCollection allToast = $$x(toastMain);

        Assertions.assertThat(allToast.last().getText())
                .as("Не сработала проверка на успешное создание заказа")
                .isEqualTo("Заказ принят в обработку!");
    }

    @Test
    @Tag("Selenide")
    public void totalPriceBasketTest() {
        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(nameProductAdmin).sendKeys(nameProduct);
        $x(priceProductAdmin).sendKeys(priceProduct);
        $x(buttonCreateAdmin).click();
        $x(tableProductAdmin).should(Condition.visible);
        $x(nameProductAdmin).sendKeys(nameProduct1);
        $x(priceProductAdmin).sendKeys(priceProduct1);
        $x(buttonCreateAdmin).click();
        $x(tableProductAdmin).should(Condition.visible);
        $x(backToMain).click();
        ElementsCollection allButtonToBasket = $$x(addToBasketMain);
        for (SelenideElement button: allButtonToBasket){
            button.click();
        }
        $x(basketButtonMain).click();

        Assertions.assertThat($x(totalPriceMain).getText())
                .as("Не сработала проверка на общую сумму")
                .isEqualTo("153");
    }

    @Test
    @Tag("Selenide")
    public void notificAddProductInAdminTest() {
        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(nameProductAdmin).sendKeys(nameProduct);
        $x(priceProductAdmin).sendKeys(priceProduct);
        $x(buttonCreateAdmin).click();

        ElementsCollection allToast = $$x(toastMain);

        Assertions.assertThat(allToast.last().getText())
                .as("Не сработала проверка на успешное создание товара")
                .isEqualTo("Товар успешно добавлен!");
    }

    @Test
    @Tag("Selenide")
    public void editProductInAdminTest() {

        Response response = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new goodsTest.Request("Ручка", 45.0))
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        Selenide.open("http://localhost:8080");

        String productName = $x(productCardMain).getAttribute("data-name");

        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(existsNameProductAdmin).sendKeys("NEW");
        $x(buttonUpdateAdmin).click();
        $x(buttonCreateAdmin).click();
        $x(backToMain).click();

        Assertions.assertThat($x(productCardMain).getAttribute("data-name"))
                .as("Не сработала проверка на редактирование товара товара")
                .isEqualTo("РучкаNEW");
    }
}
