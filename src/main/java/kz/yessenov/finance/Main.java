package kz.yessenov.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("✅ Бэкенд сервер 8080-портта іске қосылды! Енді сайтты (index.html) аша беріңіз.");
    }
}