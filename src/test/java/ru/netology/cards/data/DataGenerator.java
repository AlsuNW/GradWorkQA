package ru.netology.cards.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DataGenerator {
    private DataGenerator(){

    }

    public static String generateCardNumber() {
        var faker = new Faker();
        return faker.finance().creditCard();
    }

    public static String generateMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        var faker = new Faker();
        return sdf.format(faker.date().birthday());
    }

    public static String generateYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy");

        var faker = new Faker();
        return sdf.format(faker.date().future(1, TimeUnit.DAYS));
    }

    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateSecurityCode() {
        var faker = new Faker();
        return faker.number().digits(3);
    }

    public static class Registration {
        private Registration() {
        }

        public static CardInfo generateCard(String locale) {
            return new CardInfo(generateCardNumber(), generateMonth(), generateYear(), generateName(locale), generateSecurityCode());
        }
    }

    @Value
    public static class CardInfo{
        String cardNumber;
        String month;
        String year;
        String name;
        String securityCode;
    }
}
