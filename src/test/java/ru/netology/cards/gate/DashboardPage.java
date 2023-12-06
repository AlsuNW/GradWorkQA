package ru.netology.cards.gate;

import com.codeborne.selenide.SelenideElement;
import ru.netology.cards.data.DataGenerator;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private SelenideElement dashboardHead = $("App_appContainer__3jRx1");

    public DashboardPage() {
        dashboardHead.shouldBe(visible);
    }

    public PaymentGate selectPaymentOption(DataGenerator.CardInfo cardInfo) {
        dashboardHead.$(byText("Купить")).click();
        return new PaymentGate();
    }

    public CreditGate selectCreditOption(DataGenerator.CardInfo cardInfo) {
        dashboardHead.$(byText("Купить в кредит")).click();
        return new CreditGate();
    }
}
