package ru.netology.cards.test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.*;
import ru.netology.cards.data.DataGenerator;
import ru.netology.cards.data.SQLHelper;

import java.sql.ResultSet;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.cards.data.SQLHelper.cleanTables;


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
    void tearDownData() {
        cleanTables();
    }

    @SneakyThrows
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

        ResultSet resultSet = SQLHelper.sortThroughPaymentEntity();
        if (resultSet.next()) {
            String status = resultSet.getString("status");
            Assertions.assertEquals("APPROVED", status);
        }
    }

    @SneakyThrows
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

        ResultSet resultSet = SQLHelper.sortThroughPaymentEntity();
        if (resultSet.next()) {
            String status = resultSet.getString("status");
            Assertions.assertEquals("DECLINED", status);
        }
    }

    @SneakyThrows
    @Test
    void shouldSaveToDatabaseCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue("4444 4444 4444 4441");
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));

        ResultSet resultSet = SQLHelper.sortThroughCreditEntity();
        if (resultSet.next()) {
            String status = resultSet.getString("status");
            Assertions.assertEquals("APPROVED", status);
        }

    }

    @SneakyThrows
    @Test
    void shouldSaveToDatabaseDeclinedCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue("4444 4444 4444 4442");
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();

        ResultSet resultSet = SQLHelper.sortThroughCreditEntity();
        if (resultSet.next()) {
            String status = resultSet.getString("status");
            Assertions.assertEquals("DECLINED", status);
        }
    }
}