package eoes.DB;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Entity
public class Accounts {
	private String id;
	private String gpid;
	private String username;
	private int created;
	private int rank;
	private int session;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGpid() {
		return gpid;
	}
	public void setGpid(String gpid) {
		this.gpid = gpid;
	}
	public int getCreated() {
		return created;
	}
	public void setCreated(int created) {
		this.created = created;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getSession() {
		return session;
	}
	public void setSession(int session) {
		this.session = session;
	}
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
        acc.setId(uuid.toString());
        acc.setGpid(userID);
        acc.setUsername(username);
        acc.setCreated((int)unixTimeSeconds);
        em.persist(acc);
        em.getTransaction().commit();
        em.close();
	}
}
