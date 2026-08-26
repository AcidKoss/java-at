
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTask2Test {

    String login = "admin";
    String pass = "secret123";
    String loginInvalid = "abra";
    String passInvalid = "cadabra";

    String adminButtonMain = "//a[@href='/admin']";
    String basketButtonMain = "//button[@id='open-cart-btn']";
    String productCardMain = "//div[@class='product-card']";
    String addToBasketMain = "//div[@class='product-card']//button[@data-action='add-to-cart']";
    String makeOrderMain = "//button[@id='makeOrder']";

    String inputLoginSignIn = "//input[@id='username']";
    String inputPassSignIn = "//input[@id='password']";
    String buttonSignIn = "//button[text()='Sign in']";
    String errorAlertSignIn = "//div[@class='alert alert-danger']";

    String nameProductAdmin = "//input[@id='n-name']";
    String priceProductAdmin = "//input[@id='n-price']";

    String buttonCreateAdmin = "//button[@id='add-btn']";
    String buttonDeleteAdmin = "//button[@data-action='delete']";
    String tableProductAdmin = "//tbody[@id='tbody']";
    String backToMain = "//a[@href='/']";

    String nameProduct = "Стакан";
    String priceProduct = "99";



    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:8080");
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

    //Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
    @Test
    @Tag("Selenide")
    public void displayNewProductTest() {

        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(nameProductAdmin).sendKeys(nameProduct);
        $x(priceProductAdmin).sendKeys(priceProduct);
        $x(buttonCreateAdmin).click();
        $x(tableProductAdmin).should(visible);
        $x(backToMain).click();

        SelenideElement productCard = $x(productCardMain);
        SelenideElement productName = $x("//div[@class='product-card']/h4");

        productCard.should(visible);

        Assertions.assertThat(productName.getText())
                .as("Товар не добавился в админке и не виден в магазине")
                .isEqualTo(nameProduct);

    }

    //Добавить товар в корзину и проверить, что он отображается.
    @Test
    @Tag("Selenide")
    public void addProductInBasketTest() {
        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(nameProductAdmin).sendKeys(nameProduct);
        $x(priceProductAdmin).sendKeys(priceProduct);
        $x(buttonCreateAdmin).click();
        $x(tableProductAdmin).should(visible);
        $x(backToMain).click();
        $x(addToBasketMain).click();
        $x(basketButtonMain).click();

        SelenideElement productCardBasket = $x("//div[@class='cart-item']");
        SelenideElement productName = $x("//div[@class='cart-item']//b");

        productCardBasket.should(visible);
        productCardBasket.should(text(nameProduct));

        Assertions.assertThat(productName.getText())
                .as("Товар не добавился в корзину")
                .isEqualTo(nameProduct);
    }

    //Попытаться войти в админку с неверным логином и паролем
    @Test
    @Tag("Selenide")
    public void invalidLoginPassTest() {
        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(loginInvalid);
        $x(inputPassSignIn).sendKeys(passInvalid);
        $x(buttonSignIn).click();

        SelenideElement errorAlert = $x(errorAlertSignIn);

        errorAlert.should(visible);
        errorAlert.should(text("Неверные учетные данные пользователя"));

        Assertions.assertThat(errorAlert.getText())
                .as("Не верно сработала проверка на корректность Логина и Пароля")
                .isEqualTo("Неверные учетные данные пользователя");
    }

    //Проверить сохранение товаров в корзине после обновления страницы.
    @Test
    @Tag("Selenide")
    public void addProductInBasketRebootPageTest() {
        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(nameProductAdmin).sendKeys(nameProduct);
        $x(priceProductAdmin).sendKeys(priceProduct);
        $x(buttonCreateAdmin).click();
        $x(tableProductAdmin).should(visible);
        $x(backToMain).click();
        $x(addToBasketMain).click();
        Selenide.refresh();
        $x(basketButtonMain).click();

        SelenideElement productCardBasket = $x("//div[@class='cart-item']");

        productCardBasket.should(visible);
        productCardBasket.should(text(nameProduct));

        int countProduct = $$x("//div[@class='cart-item']").size();
        Assertions.assertThat(countProduct)
                .as("Товар не сохранился в корзине после обновления страницы")
                .isPositive();
    }

    //Добавить в корзину товаров более чем на 300 рублей и нажать на кнопку «Оформить заказ». Проверить, что отображается JS Alert.
    @Test
    @Tag("Selenide")
    public void addProductInBasketMore300Test() {
        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(nameProductAdmin).sendKeys(nameProduct);
        $x(priceProductAdmin).sendKeys(priceProduct);
        $x(buttonCreateAdmin).click();
        $x(tableProductAdmin).should(visible);
        $x(backToMain).click();
        for (int i = 0; i < 4 ; i++) {
            $x(addToBasketMain).click();
        }
        $x(basketButtonMain).click();
        $x(makeOrderMain).click();



        String alertText = Selenide.switchTo().alert().getText();

        Selenide.switchTo().alert().accept();
        Assertions.assertThat(alertText)
                .as("Не сработала проверка на предупреждение суммы заказа больше 300 руб.")
                .isEqualTo("[SmartShop]: Денег не хватает! Сумма 396 ₽ превышает лимит 300 ₽.");
    }
}
