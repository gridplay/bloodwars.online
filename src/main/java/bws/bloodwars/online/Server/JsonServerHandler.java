package bws.bloodwars.online.Server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

//import org.msgpack.MessagePack;

class JsonServerHandler extends SimpleChannelInboundHandler<String> {
	 private static final Logger logger = LogManager.getLogger(JsonServerHandler.class);
    private final ObjectMapper objectMapper;

    public JsonServerHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Deserialize JSON message to a Map
        @SuppressWarnings("unchecked")
		Map<String, Object> message = objectMapper.readValue(msg, Map.class);
        logger.info("Received message: " + message.toString());
        
        // Handle message and send response
        message.put("response", "Hello from Netty!");
        ctx.writeAndFlush(objectMapper.writeValueAsString(message));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
	    // Channel becomes active (connected)
	    logger.info("Client connected: " + ctx.channel().remoteAddress());
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
	    // Channel becomes inactive (disconnected)
		logger.info("Client disconnected: " + ctx.channel().remoteAddress());
	    // Clean up resources, remove client from active connections list, etc.
	}
}
