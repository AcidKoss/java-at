import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)

@Config.Sources({"classpath:config.properties"})
public interface AppConfig extends Config {

    @Key("URL")
    public String url();

    @Key("TIME_OUT")
    public Long timeOut();

    @Key("LOGIN")
    public String login();

    @Key("PASS")
    public String pass();

    @Key("LOG_LEVEL")
    public String logLevel();

    @Key("NAME_PRODUCT")
    public String nameProduct();

    @Key("PRICE_PRODUCT")
    public String priceProduct();
}
