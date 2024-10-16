package com.example.aplicacionterremotos;

import com.example.aplicacionterremotos.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EarthquakeData {

    private OkHttpClient client = new OkHttpClient();

    private String getUSGSUrl(int days) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(days);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedNow = now.format(formatter);
        String formattedStartDate = startDate.format(formatter);

        return String.format(
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s&minmagnitude=2.5&minlatitude=14.5&maxlatitude=49.5&minlongitude=-125&maxlongitude=-86",
                formattedStartDate, formattedNow
        );
    }

    public void getEarthquakes(int days, final EarthquakeCallback callback) {
        String url = getUSGSUrl(days);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError("Error al obtener datos: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onError("Respuesta inesperada: " + response);
                } else {
                    try {
                        String jsonData = response.body().string();
                        JSONObject json = new JSONObject(jsonData);
                        JSONArray features = json.getJSONArray("features");
                        List<Earthquake> earthquakes = new ArrayList<>();

                        for (int i = 0; i < features.length(); i++) {
                            JSONObject feature = features.getJSONObject(i);
                            JSONObject properties = feature.getJSONObject("properties");
                            JSONObject geometry = feature.getJSONObject("geometry");
                            JSONArray coordinates = geometry.getJSONArray("coordinates");

                            double longitude = coordinates.getDouble(0);
                            double latitude = coordinates.getDouble(1);
                            double magnitude = properties.getDouble("mag");
                            String place = properties.getString("place");

                            earthquakes.add(new Earthquake(latitude, longitude, magnitude, place));
                        }

                        callback.onEarthquakesReceived(earthquakes);
                    } catch (JSONException e) {
                        callback.onError("Error al procesar JSON: " + e.getMessage());
                    }
                }
            }
        });
    }

    public interface EarthquakeCallback {
        void onEarthquakesReceived(List<Earthquake> earthquakes);
        void onError(String error);
    }
}