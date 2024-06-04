package eoes.DB;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;

//import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Maps {
	//private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Id
	public int id;
	public String name;
	public int instanced;

	@SuppressWarnings("unchecked")
	public String MapID2Name(int id) {
		EntityManager em = DB.emf.createEntityManager();
		String mapname = null;
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("SELECT * FROM maps WHERE mapid = :mapid");
			query.setParameter("mapid", id);
			List<Maps> maps = query.getResultList();
			for(Maps map : maps) {
				if (map.id == id) {
					mapname = map.name;
					break;
				}
			}
			em.getTransaction().commit();
		}finally {
			em.close();
		}
		return mapname;
	}
}
