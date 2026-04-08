package kz.yessenov.finance;

import kz.yessenov.finance.service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Барлық модульдерді (сервистерді) іске қосу
        FinanceTracker financeTracker = new FinanceTracker(); 
        DebtManager debtManager = new DebtManager(); 
        Calculator calculator = new Calculator();
        QuizManager quizManager = new QuizManager();
        AIChat aiChat = new AIChat();
        
        boolean isRunning = true;

        System.out.println("=========================================");
        System.out.println("  Жеке қаржы менеджеріне қош келдіңіз!  ");
        System.out.println("=========================================");

        while (isRunning) {
            System.out.println("\nНегізгі мәзір:");
            System.out.println("1. 💵 Доллар курсын көру");
            System.out.println("2. 📊 Кіріс/Шығыс қосу");
            System.out.println("3. 💰 Қалдық балансты көру");
            System.out.println("4. 📝 Қарызды тіркеу");
            System.out.println("5. 📒 Қарыздар дәптерін көру");
            System.out.println("6. 🧮 Калькулятор");
            System.out.println("7. 🧠 Қаржылық сауаттылық квизі");
            System.out.println("8. 🤖 ИИ чат-көмекші");
            System.out.println("0. ❌ Шығу");
            System.out.print("Таңдауыңызды енгізіңіз: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    CurrencyService.showExchangeRates();
                    break;
                case 2:
                    financeTracker.addTransaction(scanner);
                    break;
                case 3:
                    financeTracker.showBalance();
                    break;
                case 4:
                    debtManager.addDebt(scanner);
                    break;
                case 5:
                    debtManager.showDebts();
                    break;
                case 6:
                    calculator.start(scanner);
                    break;
                case 7:
                    quizManager.startQuiz(scanner);
                    break;
                case 8:
                    aiChat.startChat(scanner);
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("Қосымшаны пайдаланғаныңызға рахмет. Сау болыңыз!");
                    break;
                default:
                    System.out.println("Қате енгіздіңіз. 0 мен 8 аралығындағы санды таңдаңыз.");
            }
        }
        scanner.close();
    }
}