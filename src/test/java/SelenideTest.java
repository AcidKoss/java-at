import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.as;

public class SelenideTest {

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
    String priceProduct = "100";
    
    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:8080");
        
    }

    @AfterEach
    public void last() {
        Selenide.open("http://localhost:8080/admin");
        ElementsCollection deleteButtons = $$x(buttonDeleteAdmin);

        for (int i = deleteButtons.size(); i > 0 ; i--) {
            deleteButtons.first().click();
            Selenide.switchTo().alert().accept();
            $x(backToMain).should(Condition.visible);
        }
//        driver.quit();
    }

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
        $x(tableProductAdmin).should(Condition.visible);
        $x(backToMain).click();
        Assertions.assertThat($x("//div[@class='product-card']/h4").getText())
                .as("Товар не добавился в админке и не виден в магазине")
                .isEqualTo(nameProduct);

    }

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
        $x(tableProductAdmin).should(Condition.visible);
        $x(backToMain).click();
        $x(addToBasketMain).click();
        $x(basketButtonMain).click();
        Assertions.assertThat($x("//div[@class='cart-item']//b").getText())
                .as("Товар не добавился в корзину")
                .isEqualTo(nameProduct);
    }

    @Test
    @Tag("Selenide")
    public void invalidLoginPassTest() {
        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(loginInvalid);
        $x(inputPassSignIn).sendKeys(passInvalid);
        $x(buttonSignIn).click();
        Assertions.assertThat($x(errorAlertSignIn).getText())
                .as("Не верно сработала проверка на корректность Логина и Пароля")
                .isEqualTo("Неверные учетные данные пользователя");
    }

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
        $x(tableProductAdmin).should(Condition.visible);
        $x(backToMain).click();
        $x(addToBasketMain).click();
        Selenide.refresh();
        $x(basketButtonMain).click();

        int countProduct = $$x("//div[@class='cart-item']").size();
        Assertions.assertThat(countProduct)
                .as("Товар не сохранился в корзине после обновления страницы")
                .isPositive();
    }

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
        $x(tableProductAdmin).should(Condition.visible);
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
                .isEqualTo("[SmartShop]: Денег не хватает! Сумма 400 ₽ превышает лимит 300 ₽.");
    }
}
