package com.jhobahego.questionsgpt.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ChatgtpService {

    @Autowired
    private Environment environment;

    private final String OPEN_API_URL = environment.getProperty("OPEN_API_URL");

    private final String API_KEY = environment.getProperty("API_KEY");

    public String generarPregunta(String prompt) throws IOException {

        URL apiUrl = new URL(OPEN_API_URL);

        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+API_KEY);
        connection.setDoOutput(true);

        try(OutputStream stream = connection.getOutputStream()) {
            stream.write(prompt.getBytes());
            stream.flush();
        }

        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();

                String line = "";
                while((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } else {
            throw new IOException("Solicitud fallida codigo de respuesta: "+responseCode);
        }
    }
}
