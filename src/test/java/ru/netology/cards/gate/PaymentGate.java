package ru.netology.cards.gate;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentGate {

    private SelenideElement cardNumberField = $(byText("Номер карты"));
    private SelenideElement monthField = $(byText("Месяц"));
    private SelenideElement yearField = $(byText("Год"));
    private SelenideElement nameField = $(byText("Владелец"));
    private SelenideElement securityCodeField = $(byText("CVC/CVV"));
    private SelenideElement paymentHead = $(byText("Оплата по карте"));

    public PaymentGate() {
        paymentHead.shouldBe(visible);
    }

}
