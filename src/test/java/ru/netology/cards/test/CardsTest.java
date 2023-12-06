package ru.netology.cards.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardsTest {
    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @Test
    void shouldOpenPaymentForm() {
        $(byText("Купить")).click();
        $(".heading").shouldHave(text("Оплата по карте"));
    }

    @Test
    void shouldOpenCreditForm() {
        $(byText("Купить в кредит")).click();
        $(".heading").shouldHave(text("Кредит по данным карты"));
    }
}
