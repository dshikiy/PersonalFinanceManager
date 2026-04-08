package kz.yessenov.finance.service;

import java.util.Scanner;

public class Calculator {
    public void start(Scanner scanner) {
        System.out.println("\n--- 🧮 КАЛЬКУЛЯТОР ---");
        System.out.print("Бірінші санды енгізіңіз: ");
        double num1 = scanner.nextDouble();

        System.out.print("Амалды таңдаңыз (+, -, *, /): ");
        String operator = scanner.next();

        System.out.print("Екінші санды енгізіңіз: ");
        double num2 = scanner.nextDouble();

        double result = 0;
        boolean valid = true;

        switch (operator) {
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "*": result = num1 * num2; break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Қате: Нөлге бөлуге болмайды!");
                    valid = false;
                }
                break;
            default:
                System.out.println("Қате амал енгіздіңіз.");
                valid = false;
        }

        if (valid) {
            System.out.println("✅ Нәтиже: " + result + "\n");
        }
    }
}