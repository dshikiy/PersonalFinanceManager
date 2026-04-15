package kz.yessenov.finance.model;

public class Transaction {
    private double amount;       // Сомасы
    private String category;     // Категориясы (мысалы: "Тамақ", "Айлық")
    private String date;         // Күні
    private boolean isIncome;    // true болса - кіріс, false болса - шығыс

    public Transaction(double amount, String category, String date, boolean isIncome) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.isIncome = isIncome;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        String type = isIncome ? "[КІРІС]" : "[ШЫҒЫС]";
        return type + " " + date + " | " + category + " | " + amount + " ₸";
    }
}