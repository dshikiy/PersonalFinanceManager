package kz.yessenov.finance.service;

import kz.yessenov.finance.model.Debt;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DebtManager {
    private List<Debt> debts;

    public DebtManager() {
        this.debts = new ArrayList<>();
    }

    // Қарызды қосу
    public void addDebt(Scanner scanner) {
        System.out.print("Адамның атын енгізіңіз: ");
        String name = scanner.next();

        System.out.print("Соманы енгізіңіз (тг): ");
        double amount = scanner.nextDouble();

        System.out.print("Бұл қандай қарыз? (1 - Мен қарызбын / 2 - Маған қарыз): ");
        boolean isIOwe = scanner.nextInt() == 1;

        debts.add(new Debt(name, amount, isIOwe));
        System.out.println("✅ Қарыз сәтті тіркелді!\n");
    }

    // Барлық қарыздарды көрсету
    public void showDebts() {
        System.out.println("\n--- ҚАРЫЗДАР ДӘПТЕРІ ---");
        if (debts.isEmpty()) {
            System.out.println("Қазіргі уақытта ешқандай қарыз жоқ. Керемет!");
        } else {
            double iOweTotal = 0;
            double owedToMeTotal = 0;

            for (Debt debt : debts) {
                System.out.println(debt.toString());
                if (debt.isIOwe()) {
                    iOweTotal += debt.getAmount();
                } else {
                    owedToMeTotal += debt.getAmount();
                }
            }
            System.out.println("------------------------");
            System.out.println("🔴 Жалпы менің қарыздарым: " + iOweTotal + " ₸");
            System.out.println("🟢 Маған қайтарылатын қарыздар: " + owedToMeTotal + " ₸\n");
        }
    }
}