package ai;

import models.*;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class TreeSearch {
    protected Graph graph;
    protected Tree<ConnectableNode, Object> searchTree;
    protected ConnectableNode source;

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
    public static class Path{
        List<GraphElement> traces;

        private int cost;

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }
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

    @Nullable
    abstract ConnectableNode strategy(ConnectableNode node);
    abstract boolean isExpandable(ConnectableNode<ConnectableNode, Object> source, ConnectableNode child);
    @MustBeInvokedByOverriders
    protected ConnectableNode[] expand(@NotNull ConnectableNode<ConnectableNode, Object> node){
        Set<Connectable> connectedNodes = node.getValue().getConnections().keySet();
        Iterator<Connectable> itr = connectedNodes.iterator();
        List<ConnectableNode> expandedNodes = new ArrayList<>(connectedNodes.size());
        try{
            while(itr.hasNext()){
                ConnectableNode connectedNode =  (ConnectableNode) itr.next();
                if(isExpandable(node,connectedNode)) {
                    TreeNode<ConnectableNode, Object> treeNode = searchTree.newVertex(connectedNode);
                    searchTree.addChildTo(node, treeNode, node.getValue().getDataEdge(connectedNode).getData());
                    expandedNodes.add(treeNode);
                }
            }

        }catch(IllegalArgumentException e){
            //Ignore
        }
        return expandedNodes.toArray(new ConnectableNode[0]);
    }
    public void initialize(Graph graph){
        this.graph = graph;
    }

    public Result search(ConnectableNode source, GoalValidator goalValidator){
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
            if(nextNode !=null && goalValidator.isValidGoal(nextNode.getValue())){
                Path path = new Path();
                path.setCost(((TreeNode ) nextNode).getPathCost());
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
