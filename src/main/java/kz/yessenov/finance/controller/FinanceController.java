package kz.yessenov.finance.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@RestController
@RequestMapping("/api/finance")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FinanceController {

    private double currentBalance = 0.0;
    private List<Map<String, Object>> debts = new ArrayList<>();

    // ✅ Берём ключ из environment variable
    @Value("${GROQ_API_KEY}")
    private String groqApiKey;

    // --- 1. BALANCE ---
    @GetMapping("/balance")
    public Map<String, Double> getBalance() {
        return Collections.singletonMap("balance", currentBalance);
    }

    @PostMapping("/add")
    public Map<String, String> addTransaction(@RequestBody Map<String, Object> data) {
        try {
            double amount = Double.parseDouble(data.get("amount").toString());
            boolean isIncome = (boolean) data.get("income");
            currentBalance += isIncome ? amount : -amount;
            return Collections.singletonMap("status", "success");
        } catch (Exception e) {
            return Collections.singletonMap("status", "error");
        }
    }

    // --- 2. CURRENCY ---
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

    // --- 3. DEBTS ---
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

    // --- 4. AI CHAT (GROQ) ---
    @PostMapping("/chat")
    public Map<String, String> chatWithAI(@RequestBody Map<String, String> request) {

        String userMessage = request.get("message");
        String url = "https://api.groq.com/openai/v1/chat/completions";

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(groqApiKey);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama-3.3-70b-versatile");
            body.put("messages", List.of(
                    Map.of("role", "system",
                           "content", "Сен кәсіби қаржылық кеңесшісің. Қазақша қысқа жауап бер."),
                    Map.of("role", "user", "content", userMessage)
            ));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, entity, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            String reply = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            return Collections.singletonMap("reply", reply);

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap(
                    "reply",
                    "ИИ қосылмады. API key немесе модель қате болуы мүмкін."
            );
        }
    }

    // --- 5. QUIZ ---
    @GetMapping("/quiz")
    public List<Map<String, Object>> getQuiz() {
        return List.of(
                Map.of(
                        "question", "Инфляция деген не?",
                        "options", List.of("Бағаның өсуі", "Ақшаның артуы", "Банк пайызы"),
                        "answer", 0
                ),
                Map.of(
                        "question", "50/30/20 ережесі 20% не үшін?",
                        "options", List.of("Көңіл көтеру", "Тамақ", "Жинақ"),
                        "answer", 2
                ),
                Map.of(
                        "question", "Депозит деген не?",
                        "options", List.of("Қарыз алу", "Банкке сақтау", "Салық"),
                        "answer", 1
                )
        );
    }
}