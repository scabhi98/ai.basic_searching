package ai;

import models.ConnectableNode;
import models.TreeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;

public class DepthFirstSearch extends TreeSearch {
    ArrayDeque<ConnectableNode> nodes;
    public DepthFirstSearch(){
        nodes = new ArrayDeque<>();
    }
    @Override
    @Nullable ConnectableNode strategy(ConnectableNode node) {
        ConnectableNode nodeRes = null;
        if(!nodes.isEmpty()){
            nodeRes = nodes.removeFirst();
        }
        return nodeRes;
    }

    @Override
    boolean isExpandable(ConnectableNode<ConnectableNode, Object> source, ConnectableNode child) {
        boolean res = true;
        ConnectableNode node = source, root = searchTree.getRoot();
        while(res && node != root && node != null){
            if(node.getClass().equals(TreeNode.class)){
                res = ! ((TreeNode<ConnectableNode, Object>) node).getValue().getValue()
                        .equals( (child).getValue() );
            }
            node = ((TreeNode) node).getParent();
        }
        return res;
    }

    @Override
    protected ConnectableNode[] expand(@NotNull ConnectableNode node) {
        ConnectableNode[] expandedNodes = super.expand(node);

        for (ConnectableNode n: expandedNodes){
            nodes.addFirst(n);
        }
        return expandedNodes;
    }
}
