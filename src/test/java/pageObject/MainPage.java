package pageObject;



import com.codeborne.selenide.*;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    public SelenideElement adminButton = $x("//a[@href='/admin']");
    public SelenideElement  openCartButton = $x("//button[@id='open-cart-btn']");
    public ElementsCollection  productCard = $$x("//div[@class='product-card']");
    public ElementsCollection addToCartButton = $$x("//div[@class='product-card']//button[@data-action='add-to-cart']");
    public SelenideElement makeOrderButton = $x("//button[@id='makeOrder']");



    public MainPage clickAdminButton (){
        adminButton.click();
        return this;
    }

    public MainPage clickOpenCartButton (){
        openCartButton.click();
        return this;
    }

    public MainPage DragAndDropProductCard (int numberCard){
        productCard.get(numberCard).dragAndDrop(DragAndDropOptions.to(openCartButton));
        return this;
    }

    public MainPage clickAddToCartButton (int numberCard){
        addToCartButton.get(numberCard).click();
        return this;
    }

    public MainPage clickMakeOrderButton (){
        makeOrderButton.click();
        return this;
    }


}
