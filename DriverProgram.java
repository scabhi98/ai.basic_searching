import ai.BreadthFirstSearch;
import ai.TreeSearch;
import models.*;

import java.util.List;

public class DriverProgram {
    public static void main(String args[]){
        Graph<String, Integer> graph = new Graph<>() {

            @Override
            public int maxAllowedInDegree() {
                return Integer.MAX_VALUE;
            }

            @Override
            public int maxAllowedOutDegree() {
                return Integer.MAX_VALUE;
            }
        };

        ConnectableNode
                a = graph.newVertex("A"),
                b = graph.newVertex("B"),
                c = graph.newVertex("C"),
                d = graph.newVertex("D"),
                e = graph.newVertex("E");

        graph.addVertex(a)
                .addVertex(b)
                .addVertex(c)
                .addVertex(d)
                .addVertex(e);

        graph.connect(a,b).connect(b,c).connect(b,d).connect(c,d).connect(d,e);

        BreadthFirstSearch bfs = new BreadthFirstSearch();
        bfs.initialize(graph);

        TreeSearch.Result result = bfs.search(a,new ConnectableNode<>("E"));

        if(result.isSuccess()){
            System.out.print("\nMatch Found!\nPath Tracing: Start");
            List<GraphElement> nodes = result.getPath().getTraces();
            for(int i=result.getPath().getTraces().size() -1 ; i>=0; i--){
                System.out.print(" --> "+((Node<String>)nodes.get(i)).getValue());
            }
        }

    }
}
