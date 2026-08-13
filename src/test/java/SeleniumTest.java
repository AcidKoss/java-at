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

    String ligin = "admin";
    String pass = "secret123";

    By adminButtonMain = By.xpath("//a[@href='/admin']");
    By basketButtonMain = By.xpath("//button[@id='open-cart-btn']");
    By productCardMain = By.xpath("//div[@class='product-card']");

    By inputLoginSignIn = By.xpath("//input[@id='username']");
    By inputPassSignIn = By.xpath("//input[@id='password']");
    By buttonSignIn = By.xpath("//button[text()='Sign in']");

    By nameProductAdmin = By.xpath("//input[@id='n-name']");
    By priceProductAdmin = By.xpath("//input[@id='n-price']");
    By buttonCreateAdmin = By.xpath("//button[@id='add-btn']");
    By buttonDeleteAdmin = By.xpath("//button[@data-action='delete']");
    By backToMain = By.xpath("//a[@href='/']");



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
        for (WebElement button : deleteButtons) {
            button.click();
            driver.switchTo().alert().accept();
        }
    }



    @Test
    @Tag("Selenium")
    public void Test() {
        String nameProduct = "Стакан";
        String priceProduct = "10";
        driver.findElement(adminButtonMain).click();
        driver.findElement(inputLoginSignIn).sendKeys(ligin);
        driver.findElement(inputPassSignIn).sendKeys(pass);
        driver.findElement(buttonSignIn).click();
        driver.findElement(nameProductAdmin).sendKeys(nameProduct);
        driver.findElement(priceProductAdmin).sendKeys(priceProduct);
        driver.findElement(buttonCreateAdmin).click();
        driver.findElement(backToMain).click();
        Assertions.assertThat(driver.findElement(By.xpath("//div[@class='product-card']/h4")).getText())
                .as("Товар не добавился в админке и не виден в магазине")
                .isEqualTo(nameProduct);

    }
}
