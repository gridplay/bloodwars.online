package bws.bloodwars.online.Server;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RudpMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    	if (in.readableBytes() < RudpMessage.MINIMUM_LENGTH) {
            return; // Not enough data yet, wait for more bytes
        }

        int sequenceNumber = in.readInt();
        byte messageTypeByte = in.readByte();
        MessageType messageType = MessageType.fromByte(messageTypeByte);

        // Decode message based on message type
        switch (messageType) {
            case DATA_VALUE:
                decodeDataMessage(sequenceNumber, in, out);
                break;
            case ACK_VALUE:
                decodeAckMessage(sequenceNumber, in, out);
                break;
            case CONTROL_VALUE:
                decodeControlMessage(sequenceNumber, in, out);
                break;
            default:
                // Unknown message type, handle error or discard message
                in.skipBytes(in.readableBytes()); // Skip remaining bytes
                break;
        }
    }
    private void decodeDataMessage(int sequenceNumber, ByteBuf in, List<Object> out) {
        // Decode data message fields and construct RudpMessage
        // Example:
        int payloadLength = in.readInt();
        byte[] payload = new byte[payloadLength];
        in.readBytes(payload);

        RudpMessage dataMessage = new RudpMessage(sequenceNumber, MessageType.DATA, payload);
        out.add(dataMessage);
    }

    private void decodeAckMessage(int sequenceNumber, ByteBuf in, List<Object> out) {
        // Decode acknowledgment message fields and construct RudpMessage
        // Example:
        RudpMessage ackMessage = new RudpMessage(sequenceNumber, MessageType.ACK, null); // No payload
        out.add(ackMessage);
    }

    private void decodeControlMessage(int sequenceNumber, ByteBuf in, List<Object> out) {
        // Decode control message fields and construct RudpMessage
        // Example:
        boolean controlFlag = in.readBoolean();
        RudpMessage controlMessage = new RudpMessage(sequenceNumber, MessageType.CONTROL, new byte[]{(byte) (controlFlag ? 1 : 0)});
        out.add(controlMessage);
    }
}