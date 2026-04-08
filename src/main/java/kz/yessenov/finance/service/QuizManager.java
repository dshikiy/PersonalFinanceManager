package kz.yessenov.finance.service;

import kz.yessenov.finance.model.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizManager {
    private List<Question> questions;

    public QuizManager() {
        questions = new ArrayList<>();
        // Сұрақтарды алдын ала қосып қоямыз
        questions.add(new Question(
            "Инфляция дегеніміз не?", 
            new String[]{"1. Ақша құнының өсуі", "2. Ақша құнсыздануы (бағаның өсуі)", "3. Жалақының көтерілуі"}, 
            2));
        questions.add(new Question(
            "Төтенше жағдайға арналған қорда (финансовая подушка) қанша қаражат болуы керек?", 
            new String[]{"1. 1 айлық шығын көлемінде", "2. 3-6 айлық шығын көлемінде", "3. 1 жылдық кіріс"}, 
            2));
        questions.add(new Question(
            "Несие алғанда ең алдымен неге мән беру керек?", 
            new String[]{"1. Жылдық тиімді сыйақы мөлшерлемесіне (ЖТСМ/ГЭСВ)", "2. Айлық төлемге ғана", "3. Банктің атауына"}, 
            1));
    }

    public void startQuiz(Scanner scanner) {
        System.out.println("\n--- 🧠 ҚАРЖЫЛЫҚ САУАТТЫЛЫҚ КВИЗІ ---");
        int score = 0;

        for (Question q : questions) {
            System.out.println("\nСұрақ: " + q.getText());
            for (String option : q.getOptions()) {
                System.out.println(option);
            }
            System.out.print("Жауабыңыз (санмен): ");
            int answer = scanner.nextInt();

            if (q.isCorrect(answer)) {
                System.out.println("✅ Дұрыс!");
                score++;
            } else {
                System.out.println("❌ Қате жауап.");
            }
        }
        System.out.println("\nКвиз аяқталды! Сіздің ұпайыңыз: " + score + " / " + questions.size() + "\n");
    }
}