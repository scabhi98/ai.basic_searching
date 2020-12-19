package models;

import java.util.ArrayList;
import java.util.Collection;

public class TreeNode<T,V> extends ConnectableNode<T, V> {
    ConnectableNode<T,V> parent;
    int depth, pathCost;
    ArrayList<ConnectableNode<T,V> > ancestors;

    public void setParent(ConnectableNode<T, V> parent) {
        this.parent = parent;
    }

    public TreeNode() {
        super();
        init();
    }

    public TreeNode(T data) {
        super(data);
        init();
    }

    private void init(){
        parent = null;
        depth = pathCost = 0;
        ancestors = new ArrayList<>();
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getDepth() {
        return depth;
    }

    public ArrayList<ConnectableNode<T, V>> getAncestors() {
        return ancestors;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public ConnectableNode<T, V> getParent() {
        return parent;
    }

    @Override
    public void connect(Connectable connectable, V connectionData) {
        ((TreeNode) connectable).parent = this;
        super.connect(connectable, connectionData);

    }
}
