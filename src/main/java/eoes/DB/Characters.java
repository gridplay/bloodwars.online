package eoes.DB;

import javax.persistence.Entity;
import javax.persistence.Id;

import eoes.Server.*;

@Entity
public class Characters {
	@Id
	private int id;
	private String account_id;
	private String name;
	private Faction faction;
	private Gender gender;
	private int health;
	private int max_health;
	private int mapid;
	private int instance_id;
	private String pos;
	private String rot;
	private float money;
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Faction getFaction() {
		return faction;
	}
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMax_health() {
		return max_health;
	}
	public void setMax_health(int max_health) {
		this.max_health = max_health;
	}
	public int getMapid() {
		return mapid;
	}
	public void setMapid(int mapid) {
		this.mapid = mapid;
	}
	public int getInstance_id() {
		return instance_id;
	}
	public void setInstance_id(int instance_id) {
		this.instance_id = instance_id;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getRot() {
		return rot;
	}
	public void setRot(String rot) {
		this.rot = rot;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
}
