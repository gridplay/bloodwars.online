package bws.bloodwars.online.Server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
class JsonServerHandler extends SimpleChannelInboundHandler<String> {
    private final ObjectMapper objectMapper;

    public JsonServerHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Deserialize JSON message to a Map
        @SuppressWarnings("unchecked")
		Map<String, Object> message = objectMapper.readValue(msg, Map.class);
        System.out.println("Received message: " + message);
        
        // Handle message and send response
        message.put("response", "Hello from Netty!");
        ctx.writeAndFlush(objectMapper.writeValueAsString(message));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
