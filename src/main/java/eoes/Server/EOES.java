package eoes.Server;

import com.fasterxml.jackson.databind.ObjectMapper;

import eoes.Realms;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

public class EOES {
	 private final ObjectMapper objectMapper = new ObjectMapper();
	 @SuppressWarnings("exports")
	public static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	 public EOES(int port) throws Exception {
		 EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	     EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
        	ServerBootstrap b = new ServerBootstrap();
        	b.group(bossGroup, workerGroup)
        	.channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new JsonObjectDecoder());
                    p.addLast(new StringDecoder());
                    p.addLast(new StringEncoder());
                    p.addLast(new JsonServerHandler(objectMapper));
                }
            });

	        ChannelFuture f = b.bind(port).sync();
	        f.channel().closeFuture().sync();
        } finally {
            Realms.ShutdownRealm();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
