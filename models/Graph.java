package models;

import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.NotYetConnectedException;
import java.util.*;

public abstract class Graph<T, V> {
    Set<ConnectableNode<T, V> > vertices;
    public Graph() {
        vertices = new HashSet<>();
    }

    public ConnectableNode<T, V> newVertex(T data){
        ConnectableNode<T,V> node = new ConnectableNode<T, V>();
        node.setValue(data);
        return node;
    }

    public abstract int maxAllowedInDegree();
    public abstract int maxAllowedOutDegree();

    public static List<ConnectableNode> getChildren(ConnectableNode node){
        Map<ConnectableNode, Object> connections = node.getConnections();
        return new ArrayList<>(connections.keySet());
    }
    public Graph<T,V> addVertex(ConnectableNode<T, V> vertex){
        vertices.add(vertex);
        return this;
    }

    public ConnectableNode lookNodeWithValue(T data){
        Iterator<ConnectableNode<T,V> > iterator = vertices.iterator();
        ConnectableNode node = null;
        while(iterator.hasNext()){
            node = iterator.next();
            if(data != null &&(data.equals(node.getValue())))
                return node;
            else if( data == node.getValue() )
                return node;
        }
        return null;
    }

    public Graph connect(ConnectableNode srcVertex, ConnectableNode destVertex) {
        return connect(srcVertex,destVertex, null);
    }

    public int getInDegree(Connectable vertex){
        int count = 0;
        for (ConnectableNode node: vertices
             ) {
            if(node.isConnectedTo(vertex))
                count++;
        }
        return count;
    }

    public Set<ConnectableNode<T, V> > getVertices() {
        return vertices;
    }

    public int getOutDegree(Connectable vertex){
        return vertex.getConnections().size();
    }

    public Graph connect(ConnectableNode srcVertex, ConnectableNode destVertex, V data){
        if(vertices.contains(srcVertex) && vertices.contains(destVertex)) {
            if(srcVertex.isConnectedTo(destVertex)){
                throw new AlreadyConnectedException();
            }
            else if(getOutDegree(srcVertex) >= maxAllowedOutDegree() || getInDegree(destVertex) >= maxAllowedInDegree()){
                throw new IllegalArgumentException("Maximum Connection Reached");
            }
            else {
                srcVertex.connect(destVertex, data);
            }
        }
        else
            throw new IllegalArgumentException("No Such Vertex is a member of this Graph");
        return this;
    }

    public void disconnect(ConnectableNode srcVertex, ConnectableNode destVertex){
        if(vertices.contains(srcVertex) && vertices.contains(destVertex)) {
            if(!srcVertex.isConnectedTo(destVertex)){
                throw new NotYetConnectedException();
            }
            else {
                srcVertex.disconnect(destVertex);
            }
        }
        else
            throw new IllegalArgumentException("No Such Vertex is a member of this Graph");
    }

    public int getVertexCount(){
        return vertices.size();
    }

}
