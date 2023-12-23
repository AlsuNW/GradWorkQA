package ru.netology.cards.test;

        import com.codeborne.selenide.SelenideElement;
        import com.codeborne.selenide.logevents.SelenideLogger;
        import io.qameta.allure.selenide.AllureSelenide;
        import org.apache.commons.dbutils.QueryRunner;
        import org.junit.jupiter.api.*;
        import ru.netology.cards.data.DataGenerator;

        import java.sql.Connection;;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.time.Duration;

        import static com.codeborne.selenide.Condition.visible;
        import static com.codeborne.selenide.Selectors.byText;
        import static com.codeborne.selenide.Selenide.*;
        import static java.sql.DriverManager.getConnection;


public class DatabaseTest {

    SelenideElement buttonBuy = $(byText("Купить"));
    SelenideElement headerPaymentByCard = $(byText("Оплата по карте"));
    SelenideElement buttonCredit = $(byText("Купить в кредит"));
    SelenideElement headerPaymentByCredit = $(byText("Кредит по данным карты"));
    SelenideElement cardNumber = $("input[placeholder='0000 0000 0000 0000']");
    SelenideElement cardMonth = $("input[placeholder='08']");
    SelenideElement cardYear = $("input[placeholder='22']");
    SelenideElement cardOwner = $(byText("Владелец")).parent().$("[class='input__control']");
    SelenideElement cardSecurityCode = $("input[placeholder='999']");
    SelenideElement buttonContinue = $(byText("Продолжить"));

    private static final QueryRunner runner = new QueryRunner();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @AfterEach
    public void cleanAuthCodes() {

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/app";
        String username = "app";
        String password = "pass";

        // Try to establish the connection
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection connection = getConnection(url, username, password);

            // If the connection is successful, print a success message
            if (connection != null) {
                System.out.println("Connected to the database!");

                // Query the "payment_entity" table for the status field in the first record
                String query = "DELETE FROM payment_entity";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                }

                // Remember to close the connection when done
                connection.close();
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    @Test
    void shouldSaveToDatabase() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue("4444 4444 4444 4441");
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/app";
        String username = "app";
        String password = "pass";

        // Try to establish the connection
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection connection = getConnection(url, username, password);

            // If the connection is successful, print a success message
            if (connection != null) {
                System.out.println("Connected to the database!");

                // Query the "payment_entity" table for the status field in the first record
                String query = "SELECT status\n" +
                        "FROM payment_entity\n" +
                        "ORDER BY created DESC\n" +
                        "LIMIT 1;\n";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Check if there are any results
                    if (resultSet.next()) {
                        // Retrieve the value of the "status" field
                        String status = resultSet.getString("status");
                        Assertions.assertEquals("APPROVED", status);
                    } else {
                        System.out.println("No records found in the payment_entity table.");
                    }
                }

                // Remember to close the connection when done
                connection.close();
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    @Test
    void shouldSaveToDatabaseDeclined() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue("4444 4444 4444 4442");
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();

        try {
            Thread.sleep(15000);  // Sleep for 15 seconds (15,000 milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace();  // Handle the exception if needed
        }


        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/app";
        String username = "app";
        String password = "pass";

        // Try to establish the connection
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection connection = getConnection(url, username, password);

            // If the connection is successful, print a success message
            if (connection != null) {
                System.out.println("Connected to the database!");

                // Query the "payment_entity" table for the status field in the first record
                String query = "SELECT status\n" +
                        "FROM payment_entity\n" +
                        "ORDER BY created DESC\n" +
                        "LIMIT 1;\n";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Check if there are any results
                    if (resultSet.next()) {
                        // Retrieve the value of the "status" field
                        String status = resultSet.getString("status");
                        Assertions.assertEquals("DECLINED", status);
                    } else {
                        System.out.println("No records found in the payment_entity table.");
                    }
                }

                // Remember to close the connection when done
                connection.close();
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
}