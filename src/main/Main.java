package main;

import com.google.gson.Gson;
import models.ConnectionHTTP;
import models.ConnetionURL;
import models.ExchangeRateResponse;
import models.GGson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1️⃣ Configuración de clases
        ConnetionURL connetionURL = new ConnetionURL();
        ConnectionHTTP connectionHTTP = new ConnectionHTTP();
        Gson gson = GGson.create();


        try {
            System.out.println("👋 Bienvenido al Conversor de Moneda 🌍");


            // Construir URL
            String url = connetionURL.buildURL();

            // Obtener datos
            String jsonResponse = connectionHTTP.enviarPeticionGet(url);
            ExchangeRateResponse response = gson.fromJson(jsonResponse, ExchangeRateResponse.class);

//            if (!"success".equalsIgnoreCase(response.getResult())) {
//                System.out.println("Error: La API devolvió un resultado no exitoso.");
//                return;
//            }

            Map<String, Double> rates = response.getConversion_rates();
            List<String> availableCurrencies = List.of("ARS", "BOB", "BRL", "COP");

            // Mostrar tasas de interés filtradas
            System.out.println("Tasas de cambio disponibles:");
            for (String code : availableCurrencies) {
                Double value = rates.get(code);
                if (value != null) {
                    System.out.println(code + ": " + value);
                } else {
                    System.out.println(code + ": No disponible");
                }
            }

            while (true) {
                System.out.println("\n¿Qué deseas hacer?");
                System.out.println("1. Convertir moneda");
                System.out.println("2. Salir");
                System.out.print("Elige una opción: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Limpiar salto de línea

                if (option == 1) {
                    System.out.print("Ingresa la moneda destino: ");
                    String targetCurrency = scanner.nextLine().trim().toUpperCase();

                    if (!rates.containsKey(targetCurrency)) {
                        System.out.println("Moneda no disponible.");
                        continue;
                    }

                    System.out.print("Ingresa la cantidad en " + "USD" + ": ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Limpiar salto de línea

                    double rate = rates.get(targetCurrency);
                    double convertedAmount = amount * rate;

                    System.out.printf("%.2f %s equivalen a %.2f %s\n",
                            amount, "USD", convertedAmount, targetCurrency);

                } else if (option == 2) {
                    System.out.println("¡Gracias por usar el conversor! 👋");
                    break;
                } else {
                    System.out.println("Opción no válida.");
                }
            }

        } catch (UnsupportedEncodingException e) {
            System.err.println("Error al codificar la URL: " + e.getMessage());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error en la petición HTTP: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida. Por favor ingresa números donde corresponda.");
        } finally {
            scanner.close();
        }
    }
}
