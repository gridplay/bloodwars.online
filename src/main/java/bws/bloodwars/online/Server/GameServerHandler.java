package bws.bloodwars.online.Server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GameServerHandler extends SimpleChannelInboundHandler<GameMessage> {
	@Override
    protected void channelRead0(ChannelHandlerContext ctx, GameMessage msg) {
		System.out.println("Received message: " + msg.getMessageType() + " - " + msg.getPayload());
    }

}
