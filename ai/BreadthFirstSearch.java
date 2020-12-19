package ai;

import models.ConnectableNode;
import models.TreeNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;

public class BreadthFirstSearch extends TreeSearch {
    ArrayDeque<ConnectableNode> nodes;

    public BreadthFirstSearch(){
        nodes = new ArrayDeque<>();
    }

    @Override
    ConnectableNode strategy(ConnectableNode node) {
        ConnectableNode nodeRes = node;
        if(node != null && !nodes.isEmpty())
            nodeRes = nodes.removeFirst();
        return nodeRes;
    }

    @Override
    boolean isExpandable(ConnectableNode<ConnectableNode, Object> source, ConnectableNode child) {
        boolean isAncestor = (source instanceof TreeNode)
                && ((TreeNode) source).getAncestors().contains(child);
        return !isAncestor;
    }

    @Override
    protected ConnectableNode[] expand(@NotNull ConnectableNode node) {
        ConnectableNode[] expandedNodes = super.expand(node);
        for (ConnectableNode n: expandedNodes)
            if(n != null)
                nodes.addLast(n);
        return expandedNodes;
    }
}
