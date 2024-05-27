package eoes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class RealmJson {
	public int id;
	public int port;
	public String name;
	public String type;
	public String region;
	public String population;
	public String status;
	public RealmJson(int port, String name, String type, String region) {
		this.port = port;
		this.name = name;
		this.type = type;
		this.region = region;
		int max = Realms.maxClients;
		int pop = App.ClientCount;
		if (pop < (max / 4)) {
			this.population = "Low";
		}else if (pop >= (max / 4) && pop < (max / 2)) {
			this.population = "Medium";
		}else if (pop >= (max / 2) && pop < max) {
			this.population = "High";
		}else if (pop == max) {
			this.population = "Full";
		}
		this.status = "Online";
	}
	public int GetID() {
		return this.id;
	}
	public void UpdateStatus(String status) {
		this.status = status;
	}
	public void UpdatePop() {
		int max = Realms.maxClients;
		int pop = App.ClientCount;
		if (pop < (max / 4)) {
			this.population = "Low";
		}else if (pop >= (max / 4) && pop < (max / 2)) {
			this.population = "Medium";
		}else if (pop >= (max / 2) && pop < max) {
			this.population = "High";
		}else if (pop == max) {
			this.population = "Full";
		}
	}
	public String getJson() {
		String jsonString = "";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
}
