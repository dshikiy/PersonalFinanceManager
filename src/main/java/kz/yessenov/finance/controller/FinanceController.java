package kz.yessenov.finance.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/finance")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FinanceController {

    // ЖАҢАРТУ: Операциялар тарихын сақтау үшін тізім қостық
    private List<Map<String, Object>> transactions = new ArrayList<>();
    private List<Map<String, Object>> debts = new ArrayList<>();

    @Value("${GROQ_API_KEY:}") // Қате шықпас үшін әдепкі бос мән қостық
    private String groqApiKey;

    // --- 1. БАЛАНС ЖӘНЕ ТРАНЗАКЦИЯЛАР ---
    @GetMapping("/balance")
    public Map<String, Double> getBalance() {
        double currentBalance = 0.0;
        // Балансты барлық операциялардан есептейміз
        for (Map<String, Object> t : transactions) {
            double amt = Double.parseDouble(t.get("amount").toString());
            boolean isIncome = (boolean) t.get("income");
            currentBalance += isIncome ? amt : -amt;
        }
        return Collections.singletonMap("balance", currentBalance);
    }

    @PostMapping("/add")
    public Map<String, String> addTransaction(@RequestBody Map<String, Object> data) {
        try {
            // Журнал үшін ID және уақыт қосамыз
            data.put("id", UUID.randomUUID().toString());
            data.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            
            transactions.add(data); // Журналға сақтаймыз
            return Collections.singletonMap("status", "success");
        } catch (Exception e) {
            return Collections.singletonMap("status", "error");
        }
    }

    // НОВЫЙ МЕТОД: Журналды алу
    @GetMapping("/transactions")
    public List<Map<String, Object>> getTransactions() {
        return transactions;
    }

    // --- 2. ВАЛЮТА КУРСЫ ---
    @GetMapping("/currency")
    public Map<String, Object> getRealCurrency() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> apiResponse =
                    restTemplate.getForObject("https://api.exchangerate-api.com/v4/latest/USD", Map.class);
            Map<String, Double> rates = (Map<String, Double>) apiResponse.get("rates");
            return Collections.singletonMap("USD_KZT", rates.get("KZT"));
        } catch (Exception e) {
            return Collections.singletonMap("USD_KZT", 475.73);
        }
    }

    // --- 3. ҚАРЫЗДАР ---
    @GetMapping("/debts")
    public List<Map<String, Object>> getDebts() {
        return debts;
    }

    @PostMapping("/debts")
    public Map<String, String> addDebt(@RequestBody Map<String, Object> debtData) {
        debtData.put("id", UUID.randomUUID().toString());
        debts.add(debtData);
        return Collections.singletonMap("status", "success");
    }

    // --- 4. AI CHAT ---
    @PostMapping("/chat")
    public Map<String, String> chatWithAI(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String url = "https://api.groq.com/openai/v1/chat/completions";

        if (groqApiKey == null || groqApiKey.isEmpty()) {
            return Collections.singletonMap("reply", "Қате: GROQ_API_KEY табылмады.");
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(groqApiKey);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama-3.3-70b-versatile");
            body.put("messages", List.of(
                    Map.of("role", "system", "content", "Сен кәсіби қаржылық кеңесшісің. Қазақша қысқа жауап бер."),
                    Map.of("role", "user", "content", userMessage)
            ));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            String reply = root.path("choices").get(0).path("message").path("content").asText();
            return Collections.singletonMap("reply", reply);
        } catch (Exception e) {
            return Collections.singletonMap("reply", "ИИ қосылмады. API key немесе модель қате болуы мүмкін.");
        }
    }

    // --- 5. QUIZ ---
    @GetMapping("/quiz")
    public List<Map<String, Object>> getQuiz() {
        return List.of(
                Map.of("question", "Инфляция деген не?", "options", List.of("Бағаның өсуі", "Ақшаның артуы", "Банк пайызы"), "answer", 0),
                Map.of("question", "50/30/20 ережесі 20% не үшін?", "options", List.of("Көңіл көтеру", "Тамақ", "Жинақ"), "answer", 2),
                Map.of("question", "Депозит деген не?", "options", List.of("Қарыз алу", "Банкке сақтау", "Салық"), "answer", 1)
        );
    }
}