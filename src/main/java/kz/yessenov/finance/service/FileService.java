package kz.yessenov.finance.service;

import kz.yessenov.finance.model.Transaction;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String FILE_NAME = "transactions.txt";

    // Метод для сохранения транзакций в файл
    public static void saveTransactions(List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Transaction t : transactions) {
                // Записываем данные через точку с запятой (;)
                writer.println(t.getAmount() + ";" + t.getCategory() + ";" + t.getDate() + ";" + t.isIncome());
            }
        } catch (IOException e) {
            System.out.println("Файлға сақтау кезінде қате шықты: " + e.getMessage());
        }
    }

    // Метод для загрузки транзакций из файла при запуске
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return transactions; // Если файла еще нет, возвращаем пустой список
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    double amount = Double.parseDouble(parts[0]);
                    String category = parts[1];
                    String date = parts[2];
                    boolean isIncome = Boolean.parseBoolean(parts[3]);
                    
                    transactions.add(new Transaction(amount, category, date, isIncome));
                }
            }
        } catch (IOException e) {
            System.out.println("Файлды оқу кезінде қате шықты: " + e.getMessage());
        }
        return transactions;
    }
}