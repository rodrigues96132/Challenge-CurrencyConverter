import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Exibir opções de conversão
        System.out.println("Escolha uma das opções de conversão de moeda:");
        System.out.println("1. BRL para USD");
        System.out.println("2. USD para BRL");
        System.out.println("3. EUR para BRL");
        System.out.println("4. BRL para EUR");
        System.out.println("5. GBP para BRL");
        System.out.println("6. BRL para GBP");

        // Solicitar ao usuário a opção desejada
        int option = scanner.nextInt();
        System.out.println("Informe o valor a ser convertido:");
        double amount = scanner.nextDouble();

        // Obter a chave de autenticação
        String apiKey = "8d384cfeab1737a2d064d3c9"; // Substitua com sua chave de autenticação da API

        String baseUrl = "https://v6.exchangerate-api.com/v6/8d384cfeab1737a2d064d3c9/latest/";

        // Moeda base
        String baseCurrency = "";
        String targetCurrency = "";

        switch (option) {
            case 1:
                baseCurrency = "BRL";
                targetCurrency = "USD";
                break;
            case 2:
                baseCurrency = "USD";
                targetCurrency = "BRL";
                break;
            case 3:
                baseCurrency = "EUR";
                targetCurrency = "BRL";
                break;
            case 4:
                baseCurrency = "BRL";
                targetCurrency = "EUR";
                break;
            case 5:
                baseCurrency = "GBP";
                targetCurrency = "BRL";
                break;
            case 6:
                baseCurrency = "BRL";
                targetCurrency = "GBP";
                break;
            default:
                System.out.println("Opção inválida!");
                return;
        }

        // Construir a URL de requisição para a API
        String url = baseUrl + baseCurrency + "?apikey=" + apiKey;

        // Criar o cliente HTTP e a solicitação
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            // Enviar a solicitação e obter a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Processar a resposta JSON com Gson
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            double exchangeRate = jsonResponse.getAsJsonObject("conversion_rates3").get(targetCurrency).getAsDouble();

            // Realizar a conversão
            double convertedAmount = amount * exchangeRate;

            // Exibir o resultado
            System.out.println(amount + " " + baseCurrency + " é igual a " + convertedAmount + " " + targetCurrency);

        } catch (Exception e) {
            System.out.println("Erro ao fazer a solicitação para a API: " + e.getMessage());
        }
    }
}