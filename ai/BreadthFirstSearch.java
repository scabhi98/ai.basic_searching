package ai;

import models.ConnectableNode;
import models.TreeNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class BreadthFirstSearch extends TreeSearch {
    ArrayDeque<ConnectableNode> nodes;

    public BreadthFirstSearch(){
        nodes = new ArrayDeque<>();
    }

    @Override
    ConnectableNode strategy(ConnectableNode node) {
        ConnectableNode nodeRes = node;
        if(node != null && !nodes.isEmpty()){
            nodeRes = nodes.removeFirst();
        }
        return nodeRes;
    }

    @Override
    boolean isExpandable(ConnectableNode<ConnectableNode, Object> source, ConnectableNode child) {
        boolean isExpandable = !child.getValue().equals(searchTree.getRoot().getValue().getValue()); // Source node should not be visited again
        if(isExpandable && source.getValue() == searchTree.getRoot().getValue()){
            isExpandable = (searchTree.getVertices().size() < 2); // Dont expand source node again
        }else if(isExpandable){
            isExpandable = !((TreeNode<ConnectableNode, Object>) source).getParent().getValue().getValue().equals(
                    child.getValue()  //Parent Node
            );
        }
        return isExpandable;
    }

    @Override
    protected ConnectableNode[] expand(@NotNull ConnectableNode node) {
        ConnectableNode[] expandedNodes = super.expand(node);

        for (ConnectableNode n: expandedNodes){
            if(n != null)
                nodes.addLast(n);
        }
        return expandedNodes;
    }
}
