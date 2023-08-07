package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class TrySql {
    public final SelenideElement runSQLButton = $(".ws-btn");
    public ElementsCollection allTableRows = $$(".notranslate.ws-table-all > tbody > tr");
    public final SelenideElement SQLResultOutput = $("#resultSQL >  #divResultSQL > *");
    public final SelenideElement acceptCookiesButton = $("#accept-choices");
    public final SelenideElement customersTable = $("[title='Click to see the content of the Customers table']");
    public final SelenideElement restoreDatabaseButton = $("#restoreDBBtn");

    public void setSQLInput(String SQLStatement) {
        executeJavaScript("window.editor.setValue('" + SQLStatement + " ')");
    }
}