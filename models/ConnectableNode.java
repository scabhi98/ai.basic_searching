package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectableNode<T, V> implements Connectable<V>, Node<T> {

    private Map<Connectable, V> connections;
    static int DEFAULT_CONNECTION_CAPACITY = 2;

    T data;

    public ConnectableNode(){
        this.connections = new HashMap<>(DEFAULT_CONNECTION_CAPACITY);
    }
    public ConnectableNode(T data){
        this.connections = new HashMap<>(DEFAULT_CONNECTION_CAPACITY);
        this.data = data;
    }

    @Override
    public void connect(Connectable connectable, V connectionData) {
        connections.put(connectable, connectionData);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        else if(obj.getClass() == this.getClass())
            return data.equals(((ConnectableNode) obj).data );
        else
            return false;
    }

    @Override
    public ConnectableNode clone() {
        try {
            super.clone();
        }catch (CloneNotSupportedException e){
            //ignored
        }
        ConnectableNode node = new ConnectableNode();
        node.data = data;
        return node;
    }

    public DataEdge<V> getDataEdge(ConnectableNode source, ConnectableNode dest){
        if(source.isConnectedTo(dest)){
            new DataEdge(source.connections.get(dest));
        }
        return null;
    }

    @Override
    public boolean isConnectedTo(Connectable connectable) {
        return connections.containsKey(connectable);
    }

    @Override
    public void disconnect(Connectable connectable) {
        connections.remove(connectable);
    }

    @Override
    public Map<Connectable, V > getConnections() {
        return connections;
    }

    @Override
    public T getValue() {
        return data;
    }

    @Override
    public void setValue(T value) {
        data = value;
    }
}
