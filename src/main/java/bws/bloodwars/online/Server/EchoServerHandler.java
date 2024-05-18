package bws.bloodwars.online.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LogManager.getLogger(EchoServerHandler.class);
	@SuppressWarnings("exports")
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		logger.info("Got msg: "+msg);
        ctx.write(msg);
    }
	
	@SuppressWarnings("exports")
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @SuppressWarnings("exports")
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}