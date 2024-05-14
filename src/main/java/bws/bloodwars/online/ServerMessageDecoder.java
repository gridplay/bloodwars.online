package bws.bloodwars.online;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
//import io.netty.handler.codec.TooLongFrameException;

public class ServerMessageDecoder extends ByteToMessageDecoder {
    public ServerMessageDecoder(ServerMessageSerializer serializer, int i) {
		// TODO Auto-generated constructor stub
	}

	@Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// Read incoming byte data into a byte array 'data'
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);

        // Log incoming byte data
        System.out.println("Incoming byte data: " + Arrays.toString(data));

        // Convert byte data to JSON string
        String jsonString = new String(data, StandardCharsets.UTF_8);

        // Deserialize JSON data into your message class and add it to the output list

        out.add(jsonString);
    }
}
