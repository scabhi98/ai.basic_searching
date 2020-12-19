package ai;

import models.ConnectableNode;
import models.TreeNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;

public class UniformCostSearch extends TreeSearch {
    PriorityQueue<ConnectableNode> nodes;

    public UniformCostSearch(){
        nodes = new PriorityQueue<>(5, new Comparator<ConnectableNode>() {
            @Override
            public int compare(ConnectableNode o1, ConnectableNode o2) {
                return ((TreeNode) o1).getPathCost() - ((TreeNode) o2).getPathCost();
            }
        });
    }

    @Override
    ConnectableNode strategy(ConnectableNode node) {
        ConnectableNode nodeRes = node;
        if(node != null && !nodes.isEmpty())
            nodeRes = nodes.remove();
        return nodeRes;
    }

    @Override
    boolean isExpandable(ConnectableNode<ConnectableNode, Object> source, ConnectableNode child) {
        boolean isAncestor = (source instanceof TreeNode) && ((TreeNode) source).getAncestors().contains(child);
        return !isAncestor;
    }

    @Override
    protected ConnectableNode[] expand(@NotNull ConnectableNode node) {
        ConnectableNode[] expandedNodes = super.expand(node);
        for (ConnectableNode n: expandedNodes)
            if(n != null)
                nodes.add(n);
        return expandedNodes;
    }
}
