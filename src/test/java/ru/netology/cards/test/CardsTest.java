package ru.netology.cards.test;

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
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
//        $$(".heading").filterBy(text("Оплата по карте"));
    }

    @Test
    void shouldOpenCreditForm() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
//        $(".heading").shouldHave(text("Кредит по данным карты"));
    }

    @Test
    void shouldFillPaymentFormWithValidData() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        $("span.input").click();
        $("input.input__control").setValue(validCard.getCardNumber());
        $("input[placeholder='08']").setValue(validCard.getMonth());
        $("input[placeholder='22']").setValue(validCard.getYear());
        $("nameInput = input.get(3)").setValue(validCard.getName());
        $("input[placeholder='999']").setValue(validCard.getSecurityCode());
        $(byText("Продолжить")).click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotInputInvalidDataToFieldCardNumber() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        $("span.input").click();
        $("input.input__control").setValue(";,.:%");
        $("input.input__control").shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldYear() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        $("input[placeholder='22']").click();
        $("input[placeholder='22']").setValue("AB");
        $("input[placeholder='22']").shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldMonth() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        $("input[placeholder='08']").click();
        $("input[placeholder='08']").setValue("AB");
        $("input[placeholder='08']").shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldName() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        $("nameInput = input.get(3)").click();
        $("nameInput = input.get(3)").setValue("1234 56789");
        $("nameInput = input.get(3)").shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldSecurityCode() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        $("input[placeholder='999']").click();
        $("input[placeholder='999']").setValue("ASD");
        $("input[placeholder='999']").shouldBe(empty);
    }

    @Test
    void shouldGetErrorMessagesWhenFormEmpty() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        $(byText("Продолжить")).click();
        $("span.input__sub").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestIfCardNumberCanBeZero() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        $("span.input").click();
        $("input.input__control").setValue("0000 0000 0000 0000");
        $("input[placeholder='08']").setValue(validCard.getMonth());
        $("input[placeholder='22']").setValue(validCard.getYear());
        $("nameInput = input.get(3)").setValue(validCard.getName());
        $("input[placeholder='999']").setValue(validCard.getSecurityCode());
        $(byText("Продолжить")).click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestIfSecurityCodeCanBeZero() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        $("span.input").click();
        $("input.input__control").setValue(validCard.getCardNumber());
        $("input[placeholder='08']").setValue(validCard.getMonth());
        $("input[placeholder='22']").setValue(validCard.getYear());
        $("nameInput = input.get(3)").setValue(validCard.getName());
        $("input[placeholder='999']").setValue("000");
        $(byText("Продолжить")).click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldFillCreditFormWithValidData() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        $("span.input").click();
        $("input.input__control").setValue(validCard.getCardNumber());
        $("input[placeholder='08']").setValue(validCard.getMonth());
        $("input[placeholder='22']").setValue(validCard.getYear());
        $("nameInput = input.get(3)").setValue(validCard.getName());
        $("input[placeholder='999']").setValue(validCard.getSecurityCode());
        $(byText("Продолжить")).click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotInputInvalidDataToFieldCardNumberCredit() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
        $("span.input").click();
        $("input.input__control").setValue(";,.:%");
        $("input.input__control").shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldYearCredit() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
        $("input[placeholder='22']").click();
        $("input[placeholder='22']").setValue("AB");
        $("input[placeholder='22']").shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldMonthCredit() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
        $("input[placeholder='08']").click();
        $("input[placeholder='08']").setValue("AB");
        $("input[placeholder='08']").shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldNameCredit() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
        $("nameInput = input.get(3)").click();
        $("nameInput = input.get(3)").setValue("1234 56789");
        $("nameInput = input.get(3)").shouldBe(empty);
    }

    @Test
    void shouldNotInputInvalidDataToFieldSecurityCodeCredit() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
        $("input[placeholder='999']").click();
        $("input[placeholder='999']").setValue("ASD");
        $("input[placeholder='999']").shouldBe(empty);
    }

    @Test
    void shouldGetErrorMessagesWhenFormEmptyCredit() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
        $(byText("Продолжить")).click();
        $("span.input__sub").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestIfCardNumberCanBeZeroCredit() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        $("span.input").click();
        $("input.input__control").setValue("0000 0000 0000 0000");
        $("input[placeholder='08']").setValue(validCard.getMonth());
        $("input[placeholder='22']").setValue(validCard.getYear());
        $("nameInput = input.get(3)").setValue(validCard.getName());
        $("input[placeholder='999']").setValue(validCard.getSecurityCode());
        $(byText("Продолжить")).click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestIfSecurityCodeCanBeZeroCredit() {
        $(byText("Купить в кредит")).click();
        $(byText("Кредит по данным карты")).shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        $("span.input").click();
        $("input.input__control").setValue(validCard.getCardNumber());
        $("input[placeholder='08']").setValue(validCard.getMonth());
        $("input[placeholder='22']").setValue(validCard.getYear());
        $("nameInput = input.get(3)").setValue(validCard.getName());
        $("input[placeholder='999']").setValue("000");
        $(byText("Продолжить")).click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldGetErrorMessageIfDateIsInPast() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        $("span.input").click();
        $("input.input__control").setValue(validCard.getCardNumber());
        $("input[placeholder='08']").setValue(validCard.getMonth());
        $("input[placeholder='22']").setValue("22");
        $("nameInput = input.get(3)").setValue(validCard.getName());
        $("input[placeholder='999']").setValue(validCard.getSecurityCode());
        $(byText("Продолжить")).click();
        $(byText("Истек срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCloseMessageWithCross() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        $("span.input").click();
        $("input.input__control").setValue(validCard.getCardNumber());
        $("input[placeholder='08']").setValue(validCard.getMonth());
        $("input[placeholder='22']").setValue(validCard.getYear());
        $("nameInput = input.get(3)").setValue(validCard.getName());
        $("input[placeholder='999']").setValue(validCard.getSecurityCode());
        $(byText("Продолжить")).click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
        $("span.icon").click();
        $(".notification_status_ok").shouldNotBe(visible, Duration.ofSeconds(15));
    }
}
