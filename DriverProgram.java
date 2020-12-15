import ai.BreadthFirstSearch;
import ai.DepthFirstSearch;
import ai.EightPuzzleBoard;
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
                e = graph.newVertex("E"),
                f = graph.newVertex("F"),
                z = graph.newVertex("Z");

        graph.addVertex(a)
                .addVertex(b)
                .addVertex(c)
                .addVertex(d)
                .addVertex(e)
                .addVertex(f)
                .addVertex(z);

        graph   .connect(a,b,4)
                .connect(a,c,3)
                .connect(b,f,5)
                .connect(b,e,12)
                .connect(b,a,4)
                .connect(c,a,3)
                .connect(c,e,10)
                .connect(c,d,7)
                .connect(d,c,7)
                .connect(d,e,2)
                .connect(e,d,2)
                .connect(e,c,10)
                .connect(e,b,12)
                .connect(e,z,5)
                .connect(f,b,5)
                .connect(f,z,16)
                .connect(z,e,5)
                .connect(z,f,16);

        BreadthFirstSearch bfs = new BreadthFirstSearch();
        DepthFirstSearch dfs = new DepthFirstSearch();
        bfs.initialize(graph);
        dfs.initialize(graph);

//        TreeSearch.Result result = dfs.search(a,new ConnectableNode<>("Z"));
        TreeSearch.Result result = bfs.search(a,new ConnectableNode<>("Z"));

        if(result.isSuccess()){
            System.out.print("\nMatch Found!\nPath Tracing: Start");
            List<GraphElement> nodes = result.getPath().getTraces();
            for(int i=result.getPath().getTraces().size() -1 ; i>=0; i--){
                System.out.print(" --> "+((Node<String>)nodes.get(i)).getValue());
            }
        }else{
            System.out.print("\nNo Match Found!");
        }

        EightPuzzleBoard board = new EightPuzzleBoard(EightPuzzleBoard.BLANK_AT_END);

        board.setBoard(1,2,4,3,5,6,7,8,0);

        System.out.println("\nNumber of Misplaced Tiles: "+ EightPuzzleBoard.countMisplacedTiles(board));
        System.out.println("\nTotal Manhattan Distance: "+ EightPuzzleBoard.getTotalManhattanDistance(board));
    }
}
