package bws.bloodwars.online.Server;

public enum MessageType {
    DATA((byte) 1),
    ACK((byte) 2),
    CONTROL((byte) 3),
    DATA_VALUE((byte) 4),
    ACK_VALUE((byte)5),
    CONTROL_VALUE((byte)6);

    private final byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static MessageType fromByte(byte value) {
        for (MessageType type : MessageType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid byte value for MessageType: " + value);
    }
}