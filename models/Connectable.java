package models;

import java.util.List;
import java.util.Map;

public interface Connectable<V> {
    void connect(Connectable connectable, V connectionData);
    Map<Connectable, V> getConnections();
    void disconnect(Connectable connectable);
    boolean isConnectedTo(Connectable connectable);
}
