package bws.bloodwars.online;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
public class BWS {
	 public BWS(int port) throws InterruptedException {
	        EventLoopGroup bossGroup = new NioEventLoopGroup();
	        EventLoopGroup workerGroup = new NioEventLoopGroup();

	        try {
	            ServerBootstrap serverBootstrap = new ServerBootstrap();
	            serverBootstrap.group(bossGroup, workerGroup)
	                          .channel(NioServerSocketChannel.class)
	                          .childHandler(new ChannelInitializer<SocketChannel>() {
	                              @Override
	                              protected void initChannel(SocketChannel ch) {
	                                  ChannelPipeline pipeline = ch.pipeline();
	                                  // Increase the maximum frame length to a larger value, e.g., 10 MB (10 * 1024 * 1024 bytes)
	                                  //pipeline.addLast(new LengthFieldBasedFrameDecoder(10 * 1024 * 1024, 0, 4, 0, 4));
	                                  pipeline.addLast(new LengthFieldPrepender(8));

	                                  // Add ServerMessageEncoder and ServerMessageDecoder for message encoding/decoding
	                                  ServerMessageSerializer serializer = new ServerMessageSerializer();
	                                  pipeline.addLast(new ServerMessageEncoder(serializer));
	                                  pipeline.addLast(new ServerMessageDecoder(serializer, 10 * 1024 * 1024));
	                                  
	                                  // Add your custom handler for processing incoming messages
	                                  pipeline.addLast(new Incoming());
	                              }
	                          })
	                          .option(ChannelOption.SO_BACKLOG, 128)
	                          .childOption(ChannelOption.SO_KEEPALIVE, true);

	            ChannelFuture future = serverBootstrap.bind(port).sync();
	            System.out.println("Netty server started on port " + port);

	            future.channel().closeFuture().sync();
	        } finally {
	            workerGroup.shutdownGracefully();
	            bossGroup.shutdownGracefully();
	        }
	    }
}
