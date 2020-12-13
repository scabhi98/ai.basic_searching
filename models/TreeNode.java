package models;

import java.util.Collection;

public class TreeNode<T,V> extends ConnectableNode<T, V> {
    ConnectableNode<T,V> parent;

    public void setParent(ConnectableNode<T, V> parent) {
        this.parent = parent;
    }

    public TreeNode() {
        super();
        parent = null;
    }

    public TreeNode(T data) {
        super(data);
        parent = null;
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
