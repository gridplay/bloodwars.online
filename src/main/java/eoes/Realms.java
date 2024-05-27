package eoes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Realms {
	public static int maxClients = 0;
	RealmJson rj;
	public int SaveRealm(App app) {
		int rid = 0;
		try {
			String portstr = app.LoadConfig("server.port");
			String servername = app.LoadConfig("server.name");
			String servertype = app.LoadConfig("server.type");
			String serverregion = app.LoadConfig("server.region");
			String servermax = app.LoadConfig("server.maxclients");
			maxClients = Integer.parseInt(servermax);
			
			URL url = new URL("https://eclipseofeternity.world/api/realms");
			
			rj = new RealmJson(Integer.parseInt(portstr), servername, servertype, serverregion);
			String jsonInputString = rj.getJson();

            // Open a connection to the URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                RealmJson jsonResponse = objectMapper.readValue(response.toString(), RealmJson.class);
                System.out.println("JSON Response: " + jsonResponse);

                // Access data in the JSON response
                System.out.println("Realm ID: " + jsonResponse.GetID());
                rid = jsonResponse.GetID();
            } else {
                System.out.println("POST request did not work");
            }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rid;
	}
	public void UpdateRealm() {
		try {
			URL url = new URL("https://eclipseofeternity.world/api/realms");
			rj.UpdatePop();
			String jsonInputString = rj.getJson();

            // Open a connection to the URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("PUT request did not work");
            }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void ShutdownRealm() {
		try {
			URL url = new URL("https://eclipseofeternity.world/api/realms");
			rj.UpdateStatus("Offline");
			String jsonInputString = rj.getJson();

            // Open a connection to the URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("DELETE request did not work");
            }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
