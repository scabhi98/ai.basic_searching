package models;

public abstract class Tree<T, V> extends Graph<T, V> {

    private ConnectableNode<T,V> root;

    public Tree(ConnectableNode<T,V> rootNode) {
        root = rootNode;
        vertices.add(root);
    }

    @Override
    public int maxAllowedInDegree() {
        return 1;
    }

    @Override
    public TreeNode<T, V> newVertex(T data) {
        return new TreeNode<>(data);
    }

    public void addChildTo(ConnectableNode parent, TreeNode child, V data){
        addVertex(child);
        this.connect(parent, child, data);
        child.setParent(parent);
    }

    public ConnectableNode<T, V> getRoot() {
        return root;
    }
}
