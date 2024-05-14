package bws.bloodwars.online;
import java.io.*;
public class ServerMessageSerializer {
	public byte[] serialize(ServerMessage message) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(message);
            return bos.toByteArray();
        }
    }

    public ServerMessage deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInput in = new ObjectInputStream(bis)) {
            return (ServerMessage) in.readObject();
        }
    }
}
