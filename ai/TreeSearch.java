package ai;

import models.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class TreeSearch {
    protected Graph graph;
    protected Tree<ConnectableNode, Object> searchTree;
    protected ConnectableNode source;

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
    abstract boolean isExpandable(ConnectableNode<ConnectableNode, Object> source, ConnectableNode child);
    protected ConnectableNode[] expand(@NotNull ConnectableNode<ConnectableNode, Object> node){
        List<ConnectableNode> expandedNodes;
        expandedNodes = Graph.getChildren(node.getValue());
        try{
            for(int i=0; i<expandedNodes.size(); i++){
                if(isExpandable(node, expandedNodes.get(i))) {
                    TreeNode<ConnectableNode, Object> treeNode = searchTree.newVertex(expandedNodes.get(i));
                    searchTree.addChildTo(node, treeNode, null);
                    expandedNodes.set(i, treeNode);
                }
                else
                    expandedNodes.remove(i--);
            }

        }catch(IllegalArgumentException e){
            //Ignore
        }
        return expandedNodes.toArray(new ConnectableNode[0]);
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
        ConnectableNode<ConnectableNode,Object> currentNode = null;
        while(!nodeList.isEmpty()){
            if(currentNode == null ){
                currentNode = searchTree.getRoot();
                expand(currentNode);
            }
            ConnectableNode<ConnectableNode, Object> nextNode = strategy(currentNode);
            if(nextNode == null && nodeList.isEmpty())
                break;
            if(nextNode !=null && destination.getValue().equals(nextNode.getValue().getValue())){
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
                if(currentNode == null)
                    break;
                expand(currentNode);
            }
        }
        return new Result(false,null);
    }
}
