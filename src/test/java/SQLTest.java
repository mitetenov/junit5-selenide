import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.TrySql;

import static com.codeborne.selenide.Selenide.$;

public class SQLTest extends BaseTest {
    @Test
    @DisplayName("check Giovanni Rovelli address")
    @Description("Вывести все строки таблицы Customers и убедиться, что запись с ContactName равной 'Giovanni Rovelli' " +
            "имеет Address = 'Via Ludovico il Moro 22'")
    void address() {
        TrySql trySql = new TrySql();
        trySql.setSQLInput("SELECT * FROM Customers;");
        trySql.runSQLButton.click();
        $(By.xpath("//*[text()='Giovanni Rovelli']/following-sibling::td[text()='Via Ludovico il Moro 22']"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Customers in London")
    @Description("Вывести только те строки таблицы Customers, где city='London'. Проверить, что в таблице ровно 6 записей")
    void city() {
        TrySql trySql = new TrySql();
        trySql.setSQLInput("SELECT * FROM Customers WHERE city = \\'London\\';");
        trySql.runSQLButton.click();
        trySql.allTableRows.shouldHave(CollectionCondition.size(7)); //6 elements + 1 header
    }

    @Test
    @DisplayName("add new row")
    @Description("Добавить новую запись в таблицу Customers и проверить, что эта запись добавилась")
    void addNewRow() {
        TrySql trySql = new TrySql();
        trySql.setSQLInput("INSERT INTO CUSTOMERS VALUES (139,\\' qwe qe\\', \\'qe\\', \\'address\\', \\'city\\'," +
                " \\'zip\\', \\'us\\');");
        trySql.runSQLButton.click();
        trySql.SQLResultOutput.shouldHave(Condition.text("You have made changes to the database. Rows affected: 1"));
        trySql.setSQLInput("SELECT * FROM Customers where CustomerID = 139;");
        trySql.runSQLButton.click();
        trySql.allTableRows.shouldHave(CollectionCondition.size(2)); // 1 element + 1 header
    }

    @Test
    @DisplayName("update row")
    @Description("Обновить все поля (кроме CustomerID) в любой записи таблицы Customersи проверить, что изменения записались в базу.")
    void updateRow(){
        TrySql trySql = new TrySql();
        trySql.setSQLInput("UPDATE Customers SET CustomerName = \\'Ivanov Ivan\\'," +
                " ContactName = \\'Ivan Ivanov\\'," +
                " Address = \\'Kavtaradze St.\\'," +
                " PostalCode = \\'6020\\', " +
                " City= \\'Tbilisi\\' " +
                " WHERE CustomerID = 1;");
        trySql.runSQLButton.click();
        trySql.SQLResultOutput.shouldHave(Condition.text("You have made changes to the database. Rows affected: 1"));
        trySql.setSQLInput("SELECT * FROM Customers WHERE CustomerName = \\'Ivanov Ivan\\'AND" +
                " ContactName = \\'Ivan Ivanov\\' AND" +
                " Address = \\'Kavtaradze St.\\' AND" +
                " PostalCode = \\'6020\\' AND" +
                " City= \\'Tbilisi\\' AND" +
                " CustomerID = 1;");
        trySql.runSQLButton.click();
        trySql.allTableRows.shouldHave(CollectionCondition.size(2));
    }

    @Test
    @DisplayName("drop table")
    @Description("проверяем, что после дропа таблицы она пропала в правом списке всех таблиц")
    public void dropTable(){
        TrySql trySql = new TrySql();
        trySql.restoreDatabaseButton.click();
        trySql.customersTable.shouldBe(Condition.enabled);
        trySql.setSQLInput("DROP TABLE Customers");
        trySql.runSQLButton.click();
        trySql.customersTable.shouldNotBe(Condition.visible);
    }
}
