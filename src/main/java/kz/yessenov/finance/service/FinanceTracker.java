package kz.yessenov.finance.service;

import kz.yessenov.finance.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FinanceTracker {
    private List<Transaction> transactions;

    public FinanceTracker() {
        this.transactions = new ArrayList<>();
    }

    // Транзакция қосу (Кіріс немесе Шығыс)
    public void addTransaction(Scanner scanner) {
        System.out.print("Соманы енгізіңіз (тг): ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Буферді тазалау

        System.out.print("Категорияны енгізіңіз (мысалы: Тамақ, Жалақы): ");
        String category = scanner.nextLine();

        System.out.print("Күнін енгізіңіз (КК.АА.ЖЖЖЖ): ");
        String date = scanner.nextLine();

        System.out.print("Бұл кіріс пе? (1 - Иә / 0 - Жоқ, бұл шығыс): ");
        boolean isIncome = scanner.nextInt() == 1;

        Transaction transaction = new Transaction(amount, category, date, isIncome);
        transactions.add(transaction);
        
        System.out.println("✅ Транзакция сәтті сақталды!\n");
    }

    // Жалпы балансты және тарихты көрсету
    public void showBalance() {
        double balance = 0;
        System.out.println("\n--- ТРАНЗАКЦИЯЛАР ТАРИХЫ ---");
        
        if (transactions.isEmpty()) {
            System.out.println("Әзірге ешқандай жазба жоқ.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t.toString());
                if (t.isIncome()) {
                    balance += t.getAmount();
                } else {
                    balance -= t.getAmount();
                }
            }
        }
        
        System.out.println("----------------------------");
        System.out.println("💰 ҚАЛДЫҚ БАЛАНС: " + balance + " ₸\n");
    }
}