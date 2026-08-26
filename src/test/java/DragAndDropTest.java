import com.codeborne.selenide.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

public class DragAndDropTest {
    /*
1 Перетащить элемент в корзину с помощью Drag-and-Drop.
2 Удалить добавленный элемент из корзины и проверить, что он там больше не отображается.
 */
    String login = "admin";
    String pass = "secret123";
    String loginInvalid = "abra";
    String passInvalid = "cadabra";

    String adminButtonMain = "//a[@href='/admin']";
    String basketButtonMain = "//button[@id='open-cart-btn']";
    String productCardMain = "//div[@class='product-card']";
    String addToBasketMain = "//div[@class='product-card']//button[@data-action='add-to-cart']";
    String makeOrderMain = "//button[@id='makeOrder']";
    String removeProductMainBasket = "//button[@data-action='remove']";

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
        $x(adminButtonMain).click();
        $x(inputLoginSignIn).sendKeys(login);
        $x(inputPassSignIn).sendKeys(pass);
        $x(buttonSignIn).click();
        $x(nameProductAdmin).sendKeys(nameProduct);
        $x(priceProductAdmin).sendKeys(priceProduct);
        $x(buttonCreateAdmin).click();
        $x(tableProductAdmin).should(visible);
        $x(backToMain).click();
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

    @Test
    @Tag("Selenide")
    public void DragAndDrop() {

        $x(productCardMain).dragAndDrop(DragAndDropOptions.to($x(basketButtonMain)));

    }

    @Test
    @Tag("Selenide")
    public void DragAndDropRemoveTest() {

        $x(productCardMain).dragAndDrop(DragAndDropOptions.to($x(basketButtonMain)));
        $x(basketButtonMain).click();
        ElementsCollection allProductInBasket = $$x("//div[@class='cart-item']");
        allProductInBasket.should(CollectionCondition.sizeGreaterThan(0));

        ElementsCollection deleteButtons = $$x(removeProductMainBasket);
        deleteButtons.first().click();
        allProductInBasket.shouldBe(CollectionCondition.empty);

    }
}
