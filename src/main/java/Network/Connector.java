package Network;

public interface Connector {
    void connect();
    void sendmsg(String msg);
    String listenToNetwork();
    boolean turn();
    boolean isConnected();
    ConnectorType getConnectorType();
}
