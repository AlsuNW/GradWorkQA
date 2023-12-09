package ru.netology.cards.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.cards.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardsTest {
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
    void shouldFillFormWithValidData() {
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(visible);
        DataGenerator.CardInfo validCard = DataGenerator.Registration.generateCard("en");
        $("span.input").click();
        $("input.input__control").setValue(validCard.getCardNumber());
        $("input[placeholder='08']").setValue(validCard.getMonth());
        $("input[placeholder='22']").setValue(validCard.getYear());
        $("input:eq(3)").setValue(validCard.getName());
        $("input[placeholder='999']").setValue(validCard.getSecurityCode());
        $(byText("Продолжить")).click();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
    }
}
