package ai;

import models.ConnectableNode;

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
    protected ConnectableNode[] expand(ConnectableNode node) {
        ConnectableNode[] expandedNodes = super.expand(node);

        for (ConnectableNode n: expandedNodes){
            nodes.addLast(n);
        }
        return expandedNodes;
    }
}
