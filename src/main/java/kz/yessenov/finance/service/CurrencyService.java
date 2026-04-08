package kz.yessenov.finance.service;

public class CurrencyService {

    // Болашақта бұл жерге НацБанк API-ін қосуға болады
    public static void showExchangeRates() {
        System.out.println("\n--- ВАЛЮТА БАҒАМДАРЫ ---");
        System.out.println("Жасанды деректер (API қосылғанға дейін):");
        
        double usdRate = 448.50; // 1 Доллар
        double eurRate = 485.20; // 1 Евро
        double rubRate = 4.80;   // 1 Рубль

        System.out.println("💵 1 USD = " + usdRate + " KZT");
        System.out.println("💶 1 EUR = " + eurRate + " KZT");
        System.out.println("🪙 1 RUB = " + rubRate + " KZT");
        System.out.println("------------------------\n");
    }
}