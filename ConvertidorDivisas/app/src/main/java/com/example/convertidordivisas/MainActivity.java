package com.example.convertidordivisas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner sourceCurrencySpinner;
    private Spinner targetCurrencySpinner;
    private TextView resultText;

    private String[] currencyCodes = {
            "EUR", "USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN", "RON", "SEK", "CHF",
            "ISK", "NOK", "HRK", "RUB", "TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "ILS",
            "INR", "KRW", "MXN", "MYR", "NZD", "PHP", "SGD", "THB", "ZAR"
    };

    private HashMap<String, Double> exchangeRates = new HashMap<>(); // Almacenar tipos de cambio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.input_value);
        sourceCurrencySpinner = findViewById(R.id.source_currency_spinner);
        targetCurrencySpinner = findViewById(R.id.target_currency_spinner);
        resultText = findViewById(R.id.result_text);
        Button convertButton = findViewById(R.id.convert_button);

        // Llenar los spinners con los códigos de monedas
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyCodes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceCurrencySpinner.setAdapter(adapter);
        targetCurrencySpinner.setAdapter(adapter);

        // Obtener los tipos de cambio al iniciar la actividad
        new FetchExchangeRatesTask().execute();

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });
    }

    //Obtener los tipos de cambio desde la API
    private class FetchExchangeRatesTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String apiUrl = "https://api.currencyapi.com/v3/latest?apikey=cur_live_TO7iOCcSwFKYsBi12dZeFl8uIYXEUiKa669vJzgj&base_currency=MXN";
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONObject data = jsonResponse.getJSONObject("data");

                    // Llenar el HashMap con los tipos de cambio
                    for (String currencyCode : currencyCodes) {
                        if (data.has(currencyCode)) {
                            exchangeRates.put(currencyCode, data.getJSONObject(currencyCode).getDouble("value"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Método para realizar la conversión
    private void convertCurrency() {
        String sourceCurrency = sourceCurrencySpinner.getSelectedItem().toString();
        String targetCurrency = targetCurrencySpinner.getSelectedItem().toString();
        String input = inputValue.getText().toString();

        if (!input.isEmpty() && exchangeRates.containsKey(sourceCurrency) && exchangeRates.containsKey(targetCurrency)) {
            double inputAmount = Double.parseDouble(input);
            double sourceRate = exchangeRates.get(sourceCurrency);
            double targetRate = exchangeRates.get(targetCurrency);
            double result = (inputAmount / sourceRate) * targetRate;

            resultText.setText("Resultado: " + String.format("%.2f", result) + " " + targetCurrency);
        } else {
            resultText.setText("Error: Verifica los valores.");
        }
    }
}
