package eoes.DB;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import eoes.Faction;
import eoes.Gender;

@Entity
public class Characters {
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Id
	public int id;
	public String account_id;
	public String name;
	public Faction faction;
	public Gender gender;
	public int health;
	public int max_health;
	public String map;
	public int instance_id;
	public String pos;
	public String rot;
	public float money;
	public int level;
	public int xp;
	
	@SuppressWarnings("unchecked")
	public static List<Characters> GetChars(String accID) {
		EntityManager em = DB.emf.createEntityManager();
		List<Characters> chars = null;
		try {
            em.getTransaction().begin();
			Query query = em.createQuery("SELECT * FROM characters WHERE account_id = :account_id");
			query.setParameter("account_id", accID);
			chars = query.getResultList();
			
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
        } finally {
            em.close();
		}
		return chars;
	}

    @SuppressWarnings("exports")
	public static JsonNode handleChars(JsonNode params) {
    	String userID = params.get("userID").asText();
    	List<Characters> chars = Characters.GetChars(userID);
        ObjectNode result = objectMapper.createObjectNode();
        result.put("chars", chars.toString());
		return result;
	}
    @SuppressWarnings("exports")
	public static JsonNode CreateChar(JsonNode params) {
    	EntityManager em = DB.emf.createEntityManager();
    	int fac = params.get("faction").asInt();
    	Faction faction = Faction.Mortal;
    	switch(fac) {
    		case 0:
    			faction = Faction.Vampire;
    			break;
    		case 1:
    			faction = Faction.Lycan;
    			break;
    		case 2:
    			faction = Faction.Mortal;
    			break;
    		default:
    			faction = Faction.Vampire;
    			break;
    	}
    	int gen = params.get("gender").asInt();
    	Gender gender = Gender.NonBinary;
    	switch(gen) {
    		case 0:
    			gender = Gender.Male;
    			break;
    		case 1:
    			gender = Gender.Female;
    			break;
    		case 2:
    			gender = Gender.NonBinary;
    			break;
    		default:
    			gender = Gender.Male;
    			break;
    	}
    	StarterZone zone = StarterZone.getZone(faction);
    	try {
    		em.getTransaction().begin();
    		Characters chars = new Characters();
    		chars.account_id = params.get("accid").asText();
    		chars.name = params.get("name").asText();
    		chars.faction = faction;
    		chars.gender = gender;
    		chars.health = 100;
    		chars.max_health = 100;
    		chars.money = 0.00f;
    		chars.level = 1;
    		chars.xp = 0;
    		chars.instance_id = zone.instance;
    		chars.map = zone.map;
    		chars.pos = zone.pos;
    		chars.rot = zone.rot;
            em.persist(chars);
            em.getTransaction().commit();
            em.close();
    	}finally {
    		em.close();
    	}
        ObjectNode result = objectMapper.createObjectNode();
        result.put("status", "success");
        return result;
    }

	@SuppressWarnings("exports")
	public static JsonNode GetChars(JsonNode params) {
		EntityManager em = DB.emf.createEntityManager();
		Characters chars = new Characters();
		try {
            em.getTransaction().begin();
			Query query = em.createQuery("SELECT * FROM characters WHERE id = :id");
			query.setParameter("id", params.get("charID").asText());
			chars = (Characters)query.getResultList();
			
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
        } finally {
            em.close();
		}
		ObjectNode result = objectMapper.createObjectNode();
		result.put("char", chars.toString());
		return result;
	}
}
