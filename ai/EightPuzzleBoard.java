package ai;

public class EightPuzzleBoard {
    private int blocks[];
    private int adjustment;
    public static int BLANK_AT_END = 1, BLANK_AT_START = 0;
    public EightPuzzleBoard(int gapPosition) {
        adjustment = gapPosition > 1 || gapPosition < 0 ? 0 : gapPosition;
        blocks = new int[9];
    }
    public void setBoard(int ...values){
        if(values.length != 8 && values.length != 9)
            throw new IllegalArgumentException("8 values are required");
        System.arraycopy(values, 0, blocks, 0, values.length);
    }

    /**
     * sets a block in the puzzle board.
     * @param row row number of block 1,2 or 3
     * @param col col number block 1,2 or 3
     * @param value value of block in 1 to 8
     */
    public void setBlock(int row, int col, int value){
        blocks[3*(row - 1) + (col - 1)] = value;
    }
    public int getBlock(int row, int col){
        return blocks[3*(row - 1) + (col - 1)];
    }
    public boolean isMisplaced(int row, int col){
        int index = 3*(row - 1) + (col - 1);
        boolean res = blocks[index] != (index + adjustment);
        if(adjustment == BLANK_AT_END && index==8)
            res = blocks[index] != 0;
        return res;
    }

    public static int countMisplacedTiles(EightPuzzleBoard board){
        int sum = 0;
        for(int r=1; r<=3; r++)
            for(int c=1; c<=3; c++)
                if(board.isMisplaced(r,c))
                    sum++;
        return sum;
    }

    public static int getTotalManhattanDistance(EightPuzzleBoard board){
        int sum = 0;
        for(int r=1; r<=3; r++)
            for(int c=1; c<=3; c++)
                sum += board.getManhattanDistance(r,c);
        return sum;
    }

    public int getManhattanDistance(int row, int col){
        if(!isMisplaced(row,col))
            return 0;
        else{
            int val = getBlock(row,col), ra, ca;
            if(val > 0) {
                ra = (val + 2) / 3;
                ca = (val % 3);
                ca = (ca == 0 ? 3 : ca);
            }else
                return 0;
            return  Math.abs(row - ra) + Math.abs(col - ca);
        }
    }
}
