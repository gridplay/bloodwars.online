package bws.bloodwars.online.Server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RudpMessageEncoder extends MessageToByteEncoder<RudpMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RudpMessage msg, ByteBuf out) throws Exception {
    	out.writeInt(msg.getSequenceNumber());
        out.writeByte(msg.getMessageType().getValue());
    }
}