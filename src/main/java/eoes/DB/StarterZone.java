package eoes.DB;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import eoes.Faction;

@Entity
public class StarterZone {
	public Faction faction;
	public String map;
	public String pos;
	public String rot;
	public int instance;
	
	public static StarterZone getZone(Faction faction) {
		StarterZone zone = new StarterZone();
		EntityManager em = DB.emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("SELECT * FROM starter_zones WHERE faction = :faction");
			query.setParameter("faction", faction);
			zone = (StarterZone) query.getResultList();
		}finally {
			em.close();
		}
		return zone;
	}
}
