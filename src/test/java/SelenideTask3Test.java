
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

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class SelenideTask3Test {

    String login = "admin";
    String pass = "secret123";

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

    String nameProductAdmin = "//input[@id='n-name']";
    String priceProductAdmin = "//input[@id='n-price']";
    String existsNameProductAdmin = "//tbody[@id='tbody']//input[@type='text']";

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
            $x(backToMain).should(visible);
            sleep(500);
        }
    }


    //Добавить три единицы товара в корзину и оплатить их (общая стоимость не должна превышать 300 рублей). Проверить уведомление об обработке заказа.
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
        $x(tableProductAdmin).should(visible);
        $x(backToMain).click();
        for (int i = 0; i < 3 ; i++) {
            $x(addToBasketMain).click();
        }
        $x(basketButtonMain).click();
        $x(makeOrderMain).click();

        ElementsCollection allToast = $$x(toastMain);

        allToast.last().should(text("Заказ принят в обработку!"));
        allToast.last().should(visible);

        Assertions.assertThat(allToast.last().getText())
                .as("Не сработала проверка на успешное создание заказа")
                .isEqualTo("Заказ принят в обработку!");
    }

    //Добавить в корзину несколько разных товаров и проверить, что общая цена в корзине считается корректно.
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
        $x(tableProductAdmin).should(visible);
        $x(nameProductAdmin).sendKeys(nameProduct1);
        $x(priceProductAdmin).sendKeys(priceProduct1);
        $x(buttonCreateAdmin).click();
        $x(tableProductAdmin).should(visible);
        $x(backToMain).click();
        ElementsCollection allButtonToBasket = $$x(addToBasketMain);
        for (SelenideElement button: allButtonToBasket){
            button.click();
        }
        $x(basketButtonMain).click();

        SelenideElement totalPrice = $x(totalPriceMain);
        totalPrice.should(visible).should(text("153"));

        Assertions.assertThat(totalPrice.getText())
                .as("Не сработала проверка на общую сумму")
                .isEqualTo("153");
    }

    //Войти в админку и добавить товар. Проверить уведомление после добавления товара.
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

        allToast.last().should(visible).should(text("Товар успешно добавлен!"));

        Assertions.assertThat(allToast.last().getText())
                .as("Не сработала проверка на успешное создание товара")
                .isEqualTo("Товар успешно добавлен!");
    }

    //Войти в админку и отредактировать товар. Выйти на список товаров и проверить, что изменения применились.
    @Test
    @Tag("Selenide")
    public void editProductInAdminTest() {

        Response response = given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(new Request("Ручка", 45.0))
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        Selenide.open("http://localhost:8080");

        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(existsNameProductAdmin).sendKeys("NEW");
        $x(buttonUpdateAdmin).click();
        $x(buttonCreateAdmin).click();
        $x(backToMain).click();

        SelenideElement productCard = $x(productCardMain);

        productCard.should(visible).should(text("РучкаNEW"));

        Assertions.assertThat(productCard.getAttribute("data-name"))
                .as("Не сработала проверка на редактирование товара товара")
                .isEqualTo("РучкаNEW");
    }
}
