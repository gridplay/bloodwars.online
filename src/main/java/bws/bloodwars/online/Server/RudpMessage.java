package bws.bloodwars.online.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RudpMessage {
	private int sequenceNumber;
    private MessageType messageType;
	public static final int MINIMUM_LENGTH = 4;
	private static final Logger logger = LogManager.getLogger(RudpMessage.class);
	public RudpMessage(int seq, MessageType ack, byte[] payload) {
		sequenceNumber = seq;
		messageType = ack;
	}
	public int getSequenceNumber() {
		// TODO Auto-generated method stub
		return sequenceNumber;
	}
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return messageType;
	}
	public byte[] getPayload() {
		// TODO Auto-generated method stub
		return null;
	}

}
