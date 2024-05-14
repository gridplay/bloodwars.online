package bws.bloodwars.online;

public class ClientMessage {
    private String content;

    // Default constructor (required for serialization)
    public ClientMessage() {
    }

    // Parameterized constructor
    public ClientMessage(String content) {
        this.content = content;
    }

    // Getter and setter methods
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
