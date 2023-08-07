import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.TrySql;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {
    @BeforeAll
    public static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        Configuration.baseUrl = "https://www.w3schools.com";
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 50000;
    }

    @AfterAll
    public static void tearDown() {
        Selenide.closeWebDriver();
    }

    @BeforeEach
    public void openPageAndAcceptCookies() {
        TrySql trySql = new TrySql();
        open("/sql/trysql.asp?filename=trysql_select_all");
        if (trySql.acceptCookiesButton.exists()) {
            trySql.acceptCookiesButton.click();
        }
        trySql.restoreDatabaseButton.click();
    }
}
