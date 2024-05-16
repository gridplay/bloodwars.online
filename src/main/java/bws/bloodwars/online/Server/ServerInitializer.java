package bws.bloodwars.online.Server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class ServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
    	ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new RudpMessageEncoder());
        pipeline.addLast(new RudpMessageDecoder());
        pipeline.addLast(new RudpServerHandler());
    }
}
