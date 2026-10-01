package pageObject;

import com.codeborne.selenide.Condition;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;

public class MainPageAssert extends AbstractAssert <MainPageAssert, MainPage> {


    protected MainPageAssert(MainPage mainPage, Class<?> selfType) {
        super(mainPage, selfType);
    }

    public MainPageAssert adminButtonIsVisible(){
        actual.adminButton.should(Condition.visible);
        return this;
    }
    public MainPageAssert openCartButtonIsVisible(){
        actual.openCartButton.should(Condition.visible);
        return this;
    }
    public MainPageAssert productCardNotEmpty(){
        actual.productCard.shouldHave(sizeGreaterThan(0));
        return this;
    }
    public MainPageAssert addToCartButtonNotEmpty(){
        actual.addToCartButton.shouldHave(sizeGreaterThan(0));
        return this;
    }
    public MainPageAssert makeOrderButtonIsVisible(){
        actual.makeOrderButton.should(Condition.visible);
        return this;
    }
}
