package eoes.Server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import eoes.App;
import eoes.Realms;
import eoes.DB.Accounts;
import eoes.DB.Characters;

class JsonServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LogManager.getLogger(JsonServerHandler.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public JsonServerHandler(ObjectMapper objectMapper) {
        JsonServerHandler.objectMapper = objectMapper;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Deserialize JSON message
        JsonNode message = objectMapper.readTree(msg);
        logger.info("Received message: " + message.toString());

        // Handle JSON-RPC request
        JsonNode response = handleJsonRpcRequest(message);
        
        // Send response
        if (response != null) {
            ctx.writeAndFlush(objectMapper.writeValueAsString(response));
        }
    }

    private JsonNode handleJsonRpcRequest(JsonNode request) {
        if (!request.has("jsonrpc") || !request.get("jsonrpc").asText().equals("2.0")) {
            return createErrorResponse(null, -32600, "Invalid Request");
        }

        String method = request.get("method").asText();
        JsonNode params = request.get("params");
        JsonNode id = request.get("id");

        JsonNode result;
        switch (method) {
            case "login":
                result = Accounts.handleLogin(params);
                break;
            case "getchars":
            	result = Characters.handleChars(params);
            	break;
            case "createchar":
            	result = Characters.CreateChar(params);
            	break;
            case "charselected":
            	result = Characters.GetChars(params);
            	break;
            default:
                return createErrorResponse(id, -32601, "Method not found");
        }

        return createSuccessResponse(id, result);
    }
    
	public static void broadcastMessage(String method, ObjectNode params) {
        ObjectNode jsonRpcMessage = objectMapper.createObjectNode();
        jsonRpcMessage.put("jsonrpc", "2.0");
        jsonRpcMessage.put("method", method);
        jsonRpcMessage.put("params", params.toString());
		try {
			String serializedMessage = objectMapper.writeValueAsString(jsonRpcMessage);
	    	EOES.channels.writeAndFlush(serializedMessage + "\r\n");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private JsonNode createSuccessResponse(JsonNode id, JsonNode result) {
        ObjectNode response = objectMapper.createObjectNode();
        response.put("jsonrpc", "2.0");
        response.set("result", result);
        response.set("id", id);
        return response;
    }

    private JsonNode createErrorResponse(JsonNode id, int code, String message) {
        ObjectNode response = objectMapper.createObjectNode();
        response.put("jsonrpc", "2.0");
        ObjectNode error = objectMapper.createObjectNode();
        error.put("code", code);
        error.put("message", message);
        response.set("error", error);
        response.set("id", id);
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Channel becomes active (connected)
    	Channel channel = ctx.channel();
        EOES.channels.add(channel);
        logger.info("Client connected: " + ctx.channel().remoteAddress());
        App.ClientCount += 1;
        if (App.ClientCount > Realms.maxClients) {
        	ctx.close();
        }
        Realms.UpdateRealm();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // Channel becomes inactive (disconnected)
    	Channel channel = ctx.channel();
    	EOES.channels.remove(channel);
        logger.info("Client disconnected: " + ctx.channel().remoteAddress());
        App.ClientCount -= 1;
        Realms.UpdateRealm();
        // Clean up resources, remove client from active connections list, etc.
    }
}
