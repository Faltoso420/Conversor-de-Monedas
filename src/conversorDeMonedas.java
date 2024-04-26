import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class conversorDeMonedas {

    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=2742aaf0cac74f6da145de250f48b9dc";

    public static void main(String[] args) {
        JSONObject jsonResponse = realizarSolicitud(API_URL);

        if (jsonResponse != null) {
            JSONObject rates = jsonResponse.getJSONObject("rates");

            System.out.println("Monedas disponibles:");
            for (String moneda : rates.keySet()) {
                System.out.println("- " + moneda);
            }

            Scanner scanner = new Scanner(System.in);

            System.out.println("Ingrese la moneda de origen:");
            String monedaOrigen = scanner.nextLine().toUpperCase();

            System.out.println("Ingrese la moneda de destino:");
            String monedaDestino = scanner.nextLine().toUpperCase();

            System.out.println("Ingrese el monto a convertir:");
            double monto = scanner.nextDouble();

            double tasaOrigen = rates.getDouble(monedaOrigen);
            double tasaDestino = rates.getDouble(monedaDestino);
            double resultado = monto * (tasaDestino / tasaOrigen);

            String resultadoFormateado = String.format("%.2f", resultado);

            System.out.println("El resultado de la conversión de " + monto + "$ " + monedaOrigen + " a " + monedaDestino + " es: " + resultadoFormateado + "$");

            scanner.close();
        } else {
            System.out.println("No se pudo obtener la respuesta de la API.");
        }
    }

    private static JSONObject realizarSolicitud(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return new JSONObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
