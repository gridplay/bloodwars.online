package bws.bloodwars.online;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
public class ServerMessageEncoder extends MessageToByteEncoder<ServerMessage> {
    private final ServerMessageSerializer serializer;

    public ServerMessageEncoder(ServerMessageSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ServerMessage message, ByteBuf out) throws Exception {
        byte[] data = serializer.serialize(message);
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}