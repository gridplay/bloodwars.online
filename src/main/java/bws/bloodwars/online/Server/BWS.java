package bws.bloodwars.online.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class BWS {
	 private static final Logger logger = LogManager.getLogger(BWS.class);
	 public BWS(int port) {
		 try {
	            Selector selector = Selector.open();
	            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
	            serverSocketChannel.socket().bind(new InetSocketAddress(port));
	            serverSocketChannel.configureBlocking(false); // Set non-blocking mode
	            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

	            logger.info("Server started on port " + port);

	            while (true) {
	                if (selector.select() <= 0) {
	                    continue;
	                }

	                Set<SelectionKey> selectedKeys = selector.selectedKeys();
	                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

	                while (keyIterator.hasNext()) {
	                    SelectionKey key = keyIterator.next();
	                    keyIterator.remove();

	                    if (key.isAcceptable()) {
	                        acceptConnection(key, selector);
	                    } else if (key.isReadable()) {
	                        readMessage(key);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }
	 private void acceptConnection(SelectionKey key, Selector selector) throws IOException {
	        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
	        SocketChannel clientSocketChannel = serverSocketChannel.accept();
	        clientSocketChannel.configureBlocking(false);
	        clientSocketChannel.register(selector, SelectionKey.OP_READ);
	        logger.info("Client connected: " + clientSocketChannel.getRemoteAddress());
	    }

	    private void readMessage(SelectionKey key) throws IOException {
	        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
	        ByteBuffer buffer = ByteBuffer.allocate(1024);
	        int bytesRead = clientSocketChannel.read(buffer);
	        if (bytesRead == -1) {
	            clientSocketChannel.close();
	            logger.info("Client disconnected");
	            return;
	        }

	        buffer.flip();
	        byte[] data = new byte[buffer.remaining()];
	        buffer.get(data);
	        String message = new String(data);
	        logger.info("Received from client: " + message);

	        // Process the message and send a response if needed
	        String response = processMessage(message);

	        if (response != null && !response.isEmpty()) {
	            clientSocketChannel.write(ByteBuffer.wrap(response.getBytes()));
	        }
	    }

	    private String processMessage(String message) {
	        // Example: Echo the received message
	        return "Server received: " + message;
	    }
}
