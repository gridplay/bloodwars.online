package bws.bloodwars.online;

public class ServerMessage {
	public String uri;
	public String content;

    public void setContent(String uri, String content) {
    	this.uri = uri;
        this.content = content;
    }
}
