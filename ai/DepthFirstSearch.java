package ai;

import models.ConnectableNode;
import models.TreeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.List;

public class DepthFirstSearch extends TreeSearch {
    ArrayDeque<ConnectableNode> nodes;
    public DepthFirstSearch(){
        nodes = new ArrayDeque<>();
    }
    @Override
    @Nullable ConnectableNode strategy(ConnectableNode node) {
        ConnectableNode nodeRes = null;
        if(!nodes.isEmpty())
            nodeRes = nodes.removeFirst();
        return nodeRes;
    }

    @Override
    boolean isExpandable(ConnectableNode<ConnectableNode, Object> source, ConnectableNode child) {
        boolean isAncestor = false;
        int i = 0;
        List<ConnectableNode> ancestors = null;
        if(source instanceof TreeNode) {
            ancestors = ((TreeNode) source).getAncestors();
            while (!isAncestor && i < ancestors.size()) {
                isAncestor = ancestors.get(i++).getValue().equals(child.getValue());
            }
        }
        return !isAncestor;
    }

    @Override
    protected ConnectableNode[] expand(@NotNull ConnectableNode node) {
        ConnectableNode[] expandedNodes = super.expand(node);
        for (ConnectableNode n: expandedNodes)
            nodes.addFirst(n);
        return expandedNodes;
    }
}
