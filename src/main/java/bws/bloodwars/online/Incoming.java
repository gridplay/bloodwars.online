package bws.bloodwars.online;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Incoming extends SimpleChannelInboundHandler<DatagramPacket> {
	private static final Logger logger = LogManager.getLogger(Incoming.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
    	ByteBuf content = packet.content();
        String msg = content.toString(CharsetUtil.UTF_8);
        logger.info("Received message: " + msg);
        CharSequence reply = "Yo";
        ByteBuf responseBuffer = Unpooled.copiedBuffer(reply, CharsetUtil.UTF_8);
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, packet.sender());
        
        ctx.writeAndFlush(responsePacket).addListener(future -> {
            if (!future.isSuccess()) {
                logger.info("Failed to send response: " + future.cause());
            }else {
            	logger.info("Message sent");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Handle exceptions (e.g., client disconnects)
        cause.printStackTrace();
        ctx.close();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    	//logger.info("Client connected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
    	//logger.info("Client disconnected: " + ctx.channel().remoteAddress());
    }
}
