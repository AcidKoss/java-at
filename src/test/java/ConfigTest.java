import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

public class ConfigTest {


    String URL = ConfigProvider.appConfig.url();
    Long timeOut = ConfigProvider.appConfig.timeOut();
    String login = ConfigProvider.appConfig.login();
    String pass = ConfigProvider.appConfig.pass();
    String logLevel = ConfigProvider.appConfig.logLevel();
    String nameProduct = ConfigProvider.appConfig.nameProduct();
    String priceProduct = ConfigProvider.appConfig.priceProduct();

    String buttonDeleteAdmin = "//button[@data-action='delete']";

    String backToMain = "//a[@href='/']";

    private RequestSpecification basicRQ;

    public record Request(String name, Double price) {
    }

    @BeforeEach
    void setup() {

        System.out.println("УРЛ из конфига: " + URL);
        System.out.println("timeOut из конфига: " + timeOut);
        System.out.println("logLevel из конфига: " + logLevel);
        System.out.println("nameProduct из конфига: " + nameProduct);
        System.out.println("priceProduct из конфига: " + priceProduct);

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

    @Test
    @Tag("Aeonbits")
    public void Test() {

    }
}
