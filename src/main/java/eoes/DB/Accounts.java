package eoes.DB;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Entity
public class Accounts {
	private static ObjectMapper objectMapper = new ObjectMapper();
	public String id;
	public String gpid;
	public String username;
	public int created;
	public int rank;
	public int session;
	
	public static boolean isUserIDExists(String userID) {
		EntityManager em = DB.emf.createEntityManager();
		Query query = em.createQuery("SELECT id FROM accounts WHERE gpid = :gpid");
		query.setParameter("gpid", userID);
		Accounts account = (Accounts) query.getSingleResult();
		if (account != null) {
			return true;
		}
		return false;
	}
	public static void insertData(String userID, String username) {
		EntityManager em = DB.emf.createEntityManager();
		em.getTransaction().begin();
		Accounts acc = new Accounts();
		UUID uuid = UUID.randomUUID();
        long unixTimeMillis = System.currentTimeMillis();
        long unixTimeSeconds = unixTimeMillis / 1000L;
        acc.id = uuid.toString();
        acc.gpid = userID;
        acc.username = username;
        acc.created = (int)unixTimeSeconds;
        em.persist(acc);
        em.getTransaction().commit();
        em.close();
	}
	
	@SuppressWarnings("exports")
	public static JsonNode handleLogin(JsonNode params) {
        String username = params.get("username").asText();
        String userID = params.get("userID").asText();
        ObjectNode result = objectMapper.createObjectNode();
        result.put("message", "Login Success!");
        result.put("success", true);
		if (!Accounts.isUserIDExists(userID)) {
			Accounts.insertData(userID, username);
		}
        
        /*ObjectNode sendShit = objectMapper.createObjectNode();
        sendShit.put("username", username);
        sendShit.put("userid", userID);
        JsonServerHandler.broadcastMessage("newplayer", sendShit);*/
        
        return result;
    }
}
