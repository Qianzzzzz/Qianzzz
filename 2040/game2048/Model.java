package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
<<<<<<< HEAD
 *  @author TODO: Weiqian Peng
=======
 *  @author TODO: YOUR NAME HERE
>>>>>>> 2fa6340ae4dd9b76c09dcbee655054b896bc74ef
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private final Board _board;
    /** Current score. */
    private int _score;
    /** Maximum score so far.  Updated when game ends. */
    private int _maxScore;
    /** True iff game is ended. */
    private boolean _gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        _board = new Board(size);
        _score = _maxScore = 0;
        _gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        _board = new Board(rawValues);
        this._score = score;
        this._maxScore = maxScore;
        this._gameOver = gameOver;
    }

    /** Same as above, but gameOver is false. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore) {
        this(rawValues, score, maxScore, false);
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     * */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return _board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /** Return the current score. */
    public int score() {
        return _score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return _maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /** Allow initial game board to announce a hot start to the GUI. */
    public void hotStartAnnounce() {
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void tilt(Side side) {
        // TODO: Fill in this function.
<<<<<<< HEAD
        _board.setViewingPerspective(side);
        // use the viewing perspective to make less adjustment.
//        Part1 move
//        move tile together according to the movement.
        int size = _board.size();
        for (int col =0;col<size;col+=1){
            int cnt=0;
            for (int row=size -1;row >=0;row-=1) {
//                by using for loop to check every rows
                if (_board.tile(col, row) == null) {
                    cnt += 1;
                    //                    count the number of the null tiles
                }
                else {
                    //                    if there is tile is null, move tile
                _board.move(col, row + cnt, _board.tile(col, row));
                }
            }
        }
//        Part 2 merge
            for (int Col = 0;Col<size;Col+=1){
                for (int Row=size -1;Row>0;Row-=1){
                    if ((_board.tile(Col,Row)!=null)){
                        if ((_board.tile(Col,Row-1)!=null)){
                            if (_board.tile(Col,Row-1).value()==_board.tile(Col,Row).value()){
//                                merge only happens when two tiles are not null and have the same value
                                Tile merge = _board.tile(Col,Row-1);
                                _board.move(Col,Row,merge);
                                _score+=merge.value()*2;
//                                merge two tiles can simply consider as moving the latter tile to former
//                                tile position and value multiple 2.
                                //merge any possible tiles together .
                            }
                        }
                    }
                }
            }
//        Part 3 Move to eliminate space due to merge
//do the move again after merge since there will be space exist after merging, using the same thought as above.
        for (int col =0;col<size;col+=1){
            int cnt=0;
            for (int row=size -1;row >=0;row-=1) {
//                by using for loop to check every rows
                if (_board.tile(col, row) == null) {
                    cnt += 1;
                    //                    count the number of the null tiles
                } else {
                    _board.move(col, row + cnt, _board.tile(col, row));
                }
            }
        }
        _board.setViewingPerspective(Side.NORTH);
=======

>>>>>>> 2fa6340ae4dd9b76c09dcbee655054b896bc74ef
        checkGameOver();
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
<<<<<<< HEAD
        int size = b.size();
//        refer board number of columns an d rows to int size
        for(int i = 0; i < size; i+=1 ) {
            for(int j = 0; j < size; j+=1) {
//                by using for loop to search tile that is null and if is null, function will return true.
                if(b.tile(i, j) == null) {
//                    return true if any of the tiles in the given board are null.
                    return true;
                }
            }
        }
=======
>>>>>>> 2fa6340ae4dd9b76c09dcbee655054b896bc74ef
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
<<<<<<< HEAD
        int size = b.size();
        int MAX_PIECE = 2048;
//        same structure as previous question, but we want to check if the value of the tile is equal to 2048
//       return true if is 2048
        for(int i = 0; i < size; i+=1 ) {
            for(int j = 0; j < size; j+=1) {
                if(b.tile(i, j) != null ) {
                    if ( b.tile(i, j).value() == MAX_PIECE){
                        return true;
                    }
                }
            }
        }
=======
>>>>>>> 2fa6340ae4dd9b76c09dcbee655054b896bc74ef
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
<<<<<<< HEAD
        int size = b.size();
        if(emptySpaceExists(b))
            return true;
        for(int i = 0; i < size; i+=1 ) {
            for(int j = 0; j < size; j+=1) {
                int exit_value = b.tile(i, j).value();
                if (j + 1 < b.size() && exit_value == b.tile(i, j + 1).value()){
                    return true;
                }
                if (i + 1 < b.size() && exit_value == b.tile(i + 1, j).value()) {
                    return true;
                }

                        }
                    }
=======
>>>>>>> 2fa6340ae4dd9b76c09dcbee655054b896bc74ef
        return false;
    }

    /** Returns the model as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    /** Returns whether two models are equal. */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /** Returns hash code of Model’s string. */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
