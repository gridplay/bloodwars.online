package bws.bloodwars.online.Server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RudpServerHandler extends SimpleChannelInboundHandler<RudpMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RudpMessage msg) throws Exception {
        processIncomingMessage(ctx, msg);
    }

    private void processIncomingMessage(ChannelHandlerContext ctx, RudpMessage msg) {
        int sequenceNumber = msg.getSequenceNumber();
        byte messageType = msg.getMessageType().getValue();
        byte[] payload = msg.getPayload(); // Assuming a byte array payload

        // Example sequence number checking
        if (!isValidSequenceNumber(sequenceNumber)) {
            // Handle out-of-sequence message (e.g., buffer, discard, or respond with error)
            return;
        }

        // Example acknowledgment handling
        if (requiresAcknowledgment(messageType)) {
            sendAcknowledgment(ctx, sequenceNumber); // Send ACK back to client
        }

        // Process the payload data based on message type
        processPayload(ctx, messageType, payload);
    }

    private boolean isValidSequenceNumber(int sequenceNumber) {
        // Implement your sequence number validation logic here
        return true; // Placeholder, replace with actual logic
    }

    private boolean requiresAcknowledgment(byte messageTypebytes) {
        // Determine if the message type requires an acknowledgment
    	MessageType messageType = MessageType.fromByte(messageTypebytes);
        return messageType == MessageType.DATA; // Example: Data messages require ACK
    }

    private void sendAcknowledgment(ChannelHandlerContext ctx, int sequenceNumber) {
        // Send acknowledgment message back to the client
        RudpMessage ackMessage = new RudpMessage(sequenceNumber, MessageType.ACK, null);
        ctx.writeAndFlush(ackMessage);
    }

    private void processPayload(ChannelHandlerContext ctx, byte messageTypebytes, byte[] payload) {
        // Implement payload processing logic based on message type
    	MessageType messageType = MessageType.fromByte(messageTypebytes);
        switch (messageType) {
            case DATA:
                // Process data message payload
                break;
            case CONTROL:
                // Process control message payload
                break;
            // Add more cases as needed for different message types
			default:
				break;
        }
    }

    @SuppressWarnings("exports")
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Handle exceptions, log errors, close connections, etc.
        cause.printStackTrace();
        ctx.close();
    }
}

