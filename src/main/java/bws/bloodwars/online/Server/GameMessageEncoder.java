package bws.bloodwars.online.Server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

// Encoder
public class GameMessageEncoder extends MessageToByteEncoder<GameMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, GameMessage msg, ByteBuf out) {
        out.writeInt(msg.getMessageType());
        byte[] payloadBytes = msg.getPayload().getBytes(StandardCharsets.UTF_8);
        out.writeInt(payloadBytes.length);
        out.writeBytes(payloadBytes);
    }
}
