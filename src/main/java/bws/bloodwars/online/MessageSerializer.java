package bws.bloodwars.online;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
public class MessageSerializer {
	private static final Gson gson = new Gson();

    public static byte[] serializeMessage(ServerMessage message) {
        String json = gson.toJson(message);
        return json.getBytes(StandardCharsets.UTF_8);
    }
}
