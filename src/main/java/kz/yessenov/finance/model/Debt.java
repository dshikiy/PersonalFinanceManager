package kz.yessenov.finance.model;

public class Debt {
    private String personName; // Адамның аты
    private double amount;     // Қарыз сомасы
    private boolean isIOwe;    // true = мен қарызбын, false = маған қарыз

    public Debt(String personName, double amount, boolean isIOwe) {
        this.personName = personName;
        this.amount = amount;
        this.isIOwe = isIOwe;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isIOwe() {
        return isIOwe;
    }

    @Override
    public String toString() {
        String type = isIOwe ? "[-] Мен қарызбын" : "[+] Маған қарыз";
        return type + " | Адам: " + personName + " | Сома: " + amount + " ₸";
    }
}