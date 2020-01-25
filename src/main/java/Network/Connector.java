package Network;

public interface Connector {
    void connect();
    void sendMessage(String msg);
    String listenToNetwork();
    boolean turn();
    boolean isConnected();
    ConnectorType getConnectorType();
    boolean close();
}
