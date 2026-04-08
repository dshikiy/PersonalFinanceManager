package kz.yessenov.finance.service;

import java.util.Scanner;

public class AIChat {
    public void startChat(Scanner scanner) {
        System.out.println("\n--- 🤖 ҚАРЖЫЛЫҚ ИИ-КӨМЕКШІ ---");
        System.out.println("Сәлем! Мен сіздің виртуалды қаржыгеріңізбін.");
        System.out.println("Сұрағыңызды жазыңыз (шығу үшін 'шығу' деп жазыңыз):");
        
        scanner.nextLine(); // Буферді тазалау

        while (true) {
            System.out.print("Сіз: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("шығу")) {
                System.out.println("ИИ: Сау болыңыз! Қаржыңыз әрқашан берекелі болсын.");
                break;
            }

            // Қарапайым кілт сөздер арқылы жауап беру (Заглушка)
            if (input.contains("қарыз") || input.contains("кредит")) {
                System.out.println("ИИ: Қарыздан құтылу үшін алдымен ең жоғары пайызы бар несиені жабуға тырысыңыз. Бұл 'Қар көшкіні' әдісі деп аталады.");
            } else if (input.contains("ақша жинау") || input.contains("депозит")) {
                System.out.println("ИИ: Табысыңыздың кем дегенде 10%-ын бірден депозитке салып отыруды әдетке айналдырыңыз.");
            } else if (input.contains("инвестиция")) {
                System.out.println("ИИ: Инвестицияны бастамас бұрын, 'төтенше жағдай қорын' (3-6 айлық шығыныңызды) жинап алғаныңыз абзал.");
            } else {
                System.out.println("ИИ: Кешіріңіз, мен бұл сұрақты түсінбедім. 'Қарыз', 'ақша жинау' немесе 'инвестиция' туралы сұрап көріңіз.");
            }
        }
    }
}