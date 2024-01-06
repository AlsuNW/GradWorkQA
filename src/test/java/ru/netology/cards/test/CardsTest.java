package ru.netology.cards.test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.cards.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardsTest {

    private SelenideElement buttonBuy = $(byText("Купить"));
    private SelenideElement headerPaymentByCard = $(byText("Оплата по карте"));
    private SelenideElement buttonCredit = $(byText("Купить в кредит"));
    private SelenideElement headerPaymentByCredit = $(byText("Кредит по данным карты"));
    private SelenideElement cardNumber = $("input[placeholder='0000 0000 0000 0000']");
    private SelenideElement cardMonth = $("input[placeholder='08']");
    private SelenideElement cardYear = $("input[placeholder='22']");
    private SelenideElement cardOwner = $(byText("Владелец")).parent().$("[class='input__control']");
    private SelenideElement cardSecurityCode = $("input[placeholder='999']");
    private SelenideElement buttonContinue = $(byText("Продолжить"));

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

    @Test
    void shouldOpenPaymentForm() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
    }

    @Test
    void shouldOpenCreditForm() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
    }

    @Test
    void shouldFillPaymentFormWithValidData() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue(validCard.getCardNumber());
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification_status_error").shouldNotBe(visible);
    }

    @Test
    void shouldGiveOkMessage() {
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
        $(".notification_status_error").shouldNotBe(visible);
    }

    @Test
    void shouldGiveErrorMessage() {
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
        $(".notification_status_error").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotInputInvalidDataToFieldCardNumber() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        cardNumber.click();
        cardNumber.setValue(";,.:%");
        cardNumber.shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldYear() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        cardYear.click();
        cardYear.setValue("AB");
        cardYear.shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldMonth() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        cardMonth.click();
        cardMonth.setValue("AB");
        cardMonth.shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldName() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        cardOwner.click();
        cardOwner.setValue("1234 56789");
        cardOwner.shouldBe(empty);
    }

    @Test
    void shouldNotInputSymbolsToFieldName() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        cardOwner.click();
        cardOwner.setValue(";'..,");
        cardOwner.shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldSecurityCode() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        cardSecurityCode.click();
        cardSecurityCode.setValue("ASD");
        cardSecurityCode.shouldBe(empty);
    }

    @Test
    void shouldGetErrorMessagesWhenFormEmpty() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        buttonContinue.click();
        $("span.input__sub").shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Неверный формат")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestIfCardNumberCanBeZero() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue("0000 0000 0000 0000");
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification_status_error").shouldNotBe(visible);
    }

    @Test
    void shouldTestIfSecurityCodeCanBeZero() {
        buttonBuy.click();
        headerPaymentByCard.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue(validCard.getCardNumber());
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue("000");
        buttonContinue.click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification_status_error").shouldNotBe(visible);
    }

    @Test
    void shouldFillCreditFormWithValidData() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue(validCard.getCardNumber());
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification_status_error").shouldNotBe(visible);
    }
    @Test
    void shouldGiveOkMessageCredit() {
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
        $(".notification_status_error").shouldNotBe(visible);
    }

    @Test
    void shouldGiveErrorMessageCredit() {
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
        $(".notification_status_error").shouldBe(visible, Duration.ofSeconds(15));
    }


    @Test
    void shouldNotInputInvalidDataToFieldCardNumberCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        cardNumber.click();
        cardNumber.setValue(";,.:%");
        cardNumber.shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldYearCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        cardYear.click();
        cardYear.setValue("AB");
        cardYear.shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldMonthCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        cardMonth.click();
        cardMonth.setValue("AB");
        cardMonth.shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldNameCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        cardOwner.click();
        cardOwner.setValue("1234 56789");
        cardOwner.shouldBe(empty);
    }

    @Test
    void shouldNotInputSymbolsToFieldNameCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        cardOwner.click();
        cardOwner.setValue(";'..,");
        cardOwner.shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldSecurityCodeCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        cardSecurityCode.click();
        cardSecurityCode.setValue("ASD");
        cardSecurityCode.shouldBe(empty);
    }

    @Test
    void shouldGetErrorMessagesWhenFormEmptyCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        buttonContinue.click();
        $("span.input__sub").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestIfCardNumberCanBeZeroCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue("0000 0000 0000 0000");
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification_status_error").shouldNotBe(visible);
    }

    @Test
    void shouldTestIfSecurityCodeCanBeZeroCredit() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue(validCard.getCardNumber());
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue(validCard.getYear());
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue("000");
        buttonContinue.click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification_status_error").shouldNotBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfYearIsInPast() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue(validCard.getCardNumber());
        cardMonth.setValue(validCard.getMonth());
        cardYear.setValue("22");
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();
        $(byText("Истёк срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldGetErrorMessageIfDateIsInPastThisYear() {
        buttonCredit.click();
        headerPaymentByCredit.shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        cardNumber.click();
        cardNumber.setValue(validCard.getCardNumber());
        cardMonth.setValue("07");
        cardYear.setValue("23");
        cardOwner.setValue(validCard.getName());
        cardSecurityCode.setValue(validCard.getSecurityCode());
        buttonContinue.click();
        $(byText("Истёк срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCloseMessageWithCross() {
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
        $(".notification_status_ok>button").click();
        $(".notification_status_ok").shouldNotBe(visible, Duration.ofSeconds(15));
    }


}
