package bws.bloodwars.online;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Incoming extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	if (msg instanceof ClientMessage) {
            // Handle ClientMessage
            ClientMessage clientMessage = (ClientMessage) msg;
            System.out.println("Received from client: " + clientMessage.getContent());

            // Construct and send a response message to the client
            ServerMessage responseMessage = new ServerMessage();
            responseMessage.setContent("Hello, client! I received your message.");
            byte[] serializedMessage = MessageSerializer.serializeMessage(responseMessage);
            ctx.writeAndFlush(Unpooled.copiedBuffer(serializedMessage));
    	} else if (msg instanceof String) {
            // Handle String message
            String stringMessage = (String) msg;
            System.out.println("Received string message: " + stringMessage);
            // Construct and send a response message to the client
            ServerMessage responseMessage = new ServerMessage();
            responseMessage.setContent("Hello, client! I received your message.");
            byte[] serializedMessage = MessageSerializer.serializeMessage(responseMessage);
            ctx.writeAndFlush(Unpooled.copiedBuffer(serializedMessage));
        } else {
            // Unexpected message type
            System.err.println("Unexpected message type received: " + msg.getClass().getName());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Handle exceptions (e.g., client disconnects)
        cause.printStackTrace();
        ctx.close();
    }
}
