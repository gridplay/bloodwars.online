package eoes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Realms {
    private static final Logger logger = LogManager.getLogger(Realms.class);
	public static int maxClients = 0;
	static RealmJson rj;
	public static int SaveRealm() {
		int rid = 0;
		try {
			String portstr = App.LoadConfig("server.port");
			String servername = App.LoadConfig("server.name");
			String servertype = App.LoadConfig("server.type");
			String servermax = App.LoadConfig("server.maxclients");
			maxClients = Integer.parseInt(servermax);
			
			URL url = new URL("https://eclipseofeternity.world/api/realms");
			
			rj = new RealmJson(Integer.parseInt(portstr), servername, servertype);
			String jsonInputString = rj.getJson();
			logger.info("Info: ", jsonInputString, jsonInputString, jsonInputString, portstr, servername, servertype, servermax, url, jsonInputString);
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
            logger.info(responseCode);
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
                logger.info("JSON Response: " + jsonResponse);

                // Access data in the JSON response
                logger.info("Realm ID: " + jsonResponse.GetID());
                rid = jsonResponse.GetID();
            } else {
            	logger.info("POST request did not work");
            }
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("error: "+e.toString());
		}
		return rid;
	}
	public static void UpdateRealm() {
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
	public static void ShutdownRealm() {
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
