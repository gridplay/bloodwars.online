package eoes.Server;

public class GameMessage {
	private int messageType;
    private String payload;

    public GameMessage(int messageType, String payload) {
        this.messageType = messageType;
        this.payload = payload;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getPayload() {
        return payload;
    }
}
