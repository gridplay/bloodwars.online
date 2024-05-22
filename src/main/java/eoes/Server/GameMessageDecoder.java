package eoes.Server;

import java.nio.charset.StandardCharsets;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class GameMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 8) {
            return; // Wait until the length prefix is available
        }

        in.markReaderIndex();
        int messageType = in.readInt();
        int length = in.readInt();

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return; // Wait until the entire payload is available
        }

        byte[] payloadBytes = new byte[length];
        in.readBytes(payloadBytes);

        String payload = new String(payloadBytes, StandardCharsets.UTF_8);
        GameMessage msg = new GameMessage(messageType, payload);
        out.add(msg);
    }
}