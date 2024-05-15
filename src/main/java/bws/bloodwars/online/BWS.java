package bws.bloodwars.online;

import io.netty.bootstrap.Bootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
public class BWS {
	 private static final Logger logger = LogManager.getLogger(BWS.class);
	 public BWS(int port) throws InterruptedException {
		 EventLoopGroup group = new NioEventLoopGroup();

	        try {
	        	Bootstrap serverBootstrap = new Bootstrap();
	            serverBootstrap.group(group)
	                          .channel(NioDatagramChannel.class)
	                          .handler(new ChannelInitializer<NioDatagramChannel>() {
	                              @Override
	                              protected void initChannel(NioDatagramChannel ch) {
	                                  ChannelPipeline p = ch.pipeline();
	                                  //p.addLast(new LoggingHandler(LogLevel.INFO));
	                                  p.addLast(new StringDecoder());
	                                  p.addLast(new StringEncoder());
	                                  p.addLast(new Incoming());
	                              }
	                          });

	            logger.info("Netty server started on port " + port);
	            serverBootstrap.bind(port).sync().channel().closeFuture().sync();
	        } finally {
	        	group.shutdownGracefully();
	        }
	    }
}
