import ai.*;
import models.*;

import java.io.InputStreamReader;
import java.util.Scanner;

public class DriverProgram {
    public static void main(String args[]){
        EightPuzzleBoard board = new EightPuzzleBoard(EightPuzzleBoard.BLANK_AT_END);

        Scanner sc = new Scanner(new InputStreamReader(System.in));
        for(int i = 1; i <= 3; i++){
            for(int j = 1; j <= 3; j++) {
                System.out.printf("Enter Block (%d,%d): ", i, j);
                board.setBlock(i, j, sc.nextInt());
            }
        }

        System.out.println("\nNumber of Misplaced Tiles: "
                + EightPuzzleBoard.countMisplacedTiles(board));
        System.out.println("\nTotal Manhattan Distance: "
                + EightPuzzleBoard.getTotalManhattanDistance(board));
    }
}
