import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

/*
1.1. Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
1.2. Добавить товар в корзину и проверить, что он отображается.
1.3. Попытаться войти в админку с неверным логином и паролем.
1.4. Проверить сохранение товаров в корзине после обновления страницы.
1.5. Инициализацию браузера и его закрытие необходимо вынести в отдельные методы для выполнения перед и после тестов.
*/
public class SeleniumTest {
    WebDriver driver;

    String login = "admin";
    String pass = "secret123";
    String loginInvalid = "abra";
    String passInvalid = "cadabra";

    By adminButtonMain = By.xpath("//a[@href='/admin']");
    By basketButtonMain = By.xpath("//button[@id='open-cart-btn']");
    By productCardMain = By.xpath("//div[@class='product-card']");
    By addToBasketMain = By.xpath("//div[@class='product-card']//button[@data-action='add-to-cart']");

    By inputLoginSignIn = By.xpath("//input[@id='username']");
    By inputPassSignIn = By.xpath("//input[@id='password']");
    By buttonSignIn = By.xpath("//button[text()='Sign in']");
    By errorAlertSignIn = By.xpath("//div[@class='alert alert-danger']");

    By nameProductAdmin = By.xpath("//input[@id='n-name']");
    By priceProductAdmin = By.xpath("//input[@id='n-price']");
    By buttonCreateAdmin = By.xpath("//button[@id='add-btn']");
    By buttonDeleteAdmin = By.xpath("//button[@data-action='delete']");
    By tableProductAdmin = By.xpath("//tbody[@id='tbody']");
    By backToMain = By.xpath("//a[@href='/']");

    String nameProduct = "Стакан";
    String priceProduct = "10";

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void last() {
        driver.get("http://localhost:8080/admin");
        List<WebElement> deleteButtons = driver.findElements(buttonDeleteAdmin);
        for (int i = deleteButtons.size(); i > 0 ; i--) {
            driver.findElement(buttonDeleteAdmin).click();
            driver.switchTo().alert().accept();
            driver.findElement(backToMain).isDisplayed();
        }
        driver.quit();
    }



    @Test
    @Tag("Selenium")
    public void displayNewProductTest() {

        driver.findElement(adminButtonMain).click();
        driver.findElement(inputLoginSignIn).sendKeys(login);
        driver.findElement(inputPassSignIn).sendKeys(pass);
        driver.findElement(buttonSignIn).click();
        driver.findElement(nameProductAdmin).sendKeys(nameProduct);
        driver.findElement(priceProductAdmin).sendKeys(priceProduct);
        driver.findElement(buttonCreateAdmin).click();
        driver.findElement(tableProductAdmin).isDisplayed();
        driver.findElement(backToMain).click();
        Assertions.assertThat(driver.findElement(By.xpath("//div[@class='product-card']/h4")).getText())
                .as("Товар не добавился в админке и не виден в магазине")
                .isEqualTo(nameProduct);

    }

    @Test
    @Tag("Selenium")
    public void addProductInBasketTest() {
        driver.findElement(adminButtonMain).click();
        driver.findElement(inputLoginSignIn).sendKeys(login);
        driver.findElement(inputPassSignIn).sendKeys(pass);
        driver.findElement(buttonSignIn).click();
        driver.findElement(nameProductAdmin).sendKeys(nameProduct);
        driver.findElement(priceProductAdmin).sendKeys(priceProduct);
        driver.findElement(buttonCreateAdmin).click();
        driver.findElement(tableProductAdmin).isDisplayed();
        driver.findElement(backToMain).click();
        driver.findElement(addToBasketMain).click();
        driver.findElement(basketButtonMain).click();
        Assertions.assertThat(driver.findElement(By.xpath("//div[@class='cart-item']//b")).getText())
                .as("Товар не добавился в корзину")
                .isEqualTo(nameProduct);
    }

    @Test
    @Tag("Selenium")
    public void invalidLoginPassTest() {
        driver.findElement(adminButtonMain).click();
        driver.findElement(inputLoginSignIn).sendKeys(loginInvalid);
        driver.findElement(inputPassSignIn).sendKeys(passInvalid);
        driver.findElement(buttonSignIn).click();
        Assertions.assertThat(driver.findElement(errorAlertSignIn).getText())
                .as("Не верно сработала проверка на корректность Логина и Пароля")
                .isEqualTo("Неверные учетные данные пользователя");
    }

    @Test
    @Tag("Selenium")
    public void addProductInBasketRebootPageTest() {
        driver.findElement(adminButtonMain).click();
        driver.findElement(inputLoginSignIn).sendKeys(login);
        driver.findElement(inputPassSignIn).sendKeys(pass);
        driver.findElement(buttonSignIn).click();
        driver.findElement(nameProductAdmin).sendKeys(nameProduct);
        driver.findElement(priceProductAdmin).sendKeys(priceProduct);
        driver.findElement(buttonCreateAdmin).click();
        driver.findElement(tableProductAdmin).isDisplayed();
        driver.findElement(backToMain).click();
        driver.findElement(addToBasketMain).click();
        driver.navigate().refresh();
        driver.findElement(basketButtonMain).click();

        int countProduct = driver.findElements(By.xpath("//div[@class='cart-item']")).size();
        Assertions.assertThat(countProduct)
                .as("Товар не сохранился в корзине после обновления страницы")
                .isPositive();
    }
}
