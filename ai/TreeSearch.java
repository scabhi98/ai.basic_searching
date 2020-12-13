package ai;

import models.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class TreeSearch {
    private Graph graph;
    private Tree<ConnectableNode, Object> searchTree;
    private ConnectableNode source;

    public static class Path{
        List<GraphElement> traces;
        public Path(){
            traces = new ArrayList<>();
        }
        public boolean addTrace(GraphElement element){
            return traces.add(element);
        }

        public List<GraphElement> getTraces() {
            return traces;
        }

        public void clear(){
            traces.clear();
        }
    }

    public static class Result{
        boolean success;
        Path path;
        Result(boolean isSuccess, Path path){
            success = isSuccess;
            this.path = path;
        }
        public boolean isSuccess(){
            return success;
        }

        public Path getPath() {
            return path;
        }
    }

    @Nullable
    abstract ConnectableNode strategy(ConnectableNode node);
    protected ConnectableNode[] expand(ConnectableNode<ConnectableNode, Object> node){
        ConnectableNode[] expandedNodes;
        if(node == null){
            node = searchTree.getRoot();
        }
        expandedNodes = Graph.getChildren(node.getValue());

        for(int i=0; i<expandedNodes.length; i++){
            expandedNodes[i] = searchTree.newVertex(expandedNodes[i]);
            searchTree.addChildTo(node,(TreeNode<ConnectableNode,Object>)expandedNodes[i], null);
        }
        return expandedNodes;

    }
    public void initialize(Graph graph){
        this.graph = graph;
    }

    public Result search(ConnectableNode source, @NotNull ConnectableNode destination){
        this.source = source;
        this.searchTree = new Tree<>(new ConnectableNode<>(source)){
            @Override
            public int maxAllowedOutDegree() {
                return Integer.MAX_VALUE;
            }
        };
        ArrayDeque<ConnectableNode> nodeList = new ArrayDeque<>(graph.getVertices());
        ConnectableNode<ConnectableNode,Object> currentNode = null, workingNode;
        while(!nodeList.isEmpty()){
            if(currentNode == null ){
                currentNode = searchTree.getRoot();
                expand(currentNode);
            }
            ConnectableNode<ConnectableNode, Object> nextNode = strategy(currentNode);
            if(destination.getValue().equals(nextNode.getValue().getValue())){
                Path path = new Path();
                Node<ConnectableNode> traceNode = nextNode;
                do{
                    path.addTrace(traceNode.getValue());
                    if(traceNode.getValue().equals(source))
                        break;
                    traceNode = ((TreeNode<ConnectableNode, Object>) traceNode).getParent();
                }while(true);
                return new Result(true, path);
            }
            else{

                if(currentNode != null && nodeList.contains(currentNode.getValue())){
                    nodeList.remove(currentNode.getValue());
                }
                currentNode = nextNode == null ? searchTree.newVertex(nodeList.removeFirst()) : nextNode;
                expand(currentNode);
            }
        }
        return new Result(false,null);
    }
}
