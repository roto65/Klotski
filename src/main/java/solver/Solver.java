package solver;

import java.util.*;

import static main.Constants.COLUMNS;
import static main.Constants.ROWS;
import static solver.Shared.*;

public class Solver {

    //-----------
    // define
    //-----------
    boolean SKIP_MIRROR_STATE = true;

    private final Queue<BoardObj> Q; //queue for breadth first search (external)
    private final Map<Long, Long> H; //hash maps for current state to parent state & collision detection (external)

    int [] initBoard;
    int exploreCount, emptyCount;

    //--------------------------------------------------
    // function used to check win condition
    //
    // return: true if condition is met, false otherwise
    //--------------------------------------------------
    private boolean reachGoal(int [] curBoard) {
        for (int[] gGoalPo : gGoalPos) {
            if (curBoard[gGoalPo[0] + gGoalPo[1] * COLUMNS] != G_GOAL_BLOCK)
                return false;
        }
        return true;
    }

    //------------------------------------------------------------------
    // add new state to queue and hashmap if it does not exist before
    //
    // return: 1 if new state is added to hashmap and queue, 0 otherwise
    //------------------------------------------------------------------
    private int statePropose(BoardObj boardObj, long parentKey) {
        long curMirrorKey;

        if(H.put(boardObj.key, parentKey) == null) {
            if(SKIP_MIRROR_STATE) { //don't calculate left-right mirror state
                curMirrorKey = gBoard2Key(boardObj.board, true);
                H.put(curMirrorKey, parentKey);
            }
            //no any state same as current, add it
            Q.add(boardObj);
            return 1; //add new state
        }

        return 0; //state already exist
    }

    //------------------------------------------------------
    // how many spaces or block there are (origin excluded)
    //------------------------------------------------------
    private int countLengthX(int [] board, int posX, int posY, int directionX, int block)	{
        int step = -1;

        do {
            step++;
            posX +=  directionX;
        } while (posX >= 0 && posX < COLUMNS && board[posX+posY*COLUMNS] == block);

        return step;
    }

    private int  countLengthY(int [] board, int posX, int posY, int directionY, int block)	{
        var step = -1;

        do {
            step++;
            posY +=  directionY;
        } while (posY >= 0 && posY < ROWS && board[posX+posY*COLUMNS] == block);

        return step;
    }

    //---------------------------------------------------
    // slide empty-cell up or down by directionY (-1 or 1)
    //
    // directionY: 1: empty down (block up), -1: empty up (block down)
    //
    // return how many new state created
    //
    //---------------------------------------------------
    private int slideVertical(BoardObj boardObj, long parentKey, int emptyX, int emptyY, int directionY) {
        int blockX, blockY; //block position (x,y)
        int blockValue; //block value
        int blockSizeX, blockSizeY; //block style
        var curBoard = boardObj.board;

        //Find the block
        blockX = emptyX;
        blockY = emptyY + directionY;
        if(blockY < 0 || blockY >= ROWS) return 0; //out of range

        blockValue = curBoard[blockX+blockY*COLUMNS];
        if(blockValue <= G_EMPTY_BLOCK) return 0; //empty

        blockSizeX = gBlockStyle[gBlockBelongTo[blockValue]][0]; //block size X
        blockSizeY = gBlockStyle[gBlockBelongTo[blockValue]][1]; //block size Y

        //Begin vertical move ------------------

        //----------------------------------------------------------------
        // block slide up|down: must block size X can slide to empty space
        //
        //   min-X <---- empty space ----> max-X
        //
        //           +--------------+
        //           |              |
        //           +--------------+
        //     min-X <---- block ---> max-X
        //
        //   minBlockX must >= minSpaceX && maxBlockX must <= maxSpaceX
        //-----------------------------------------------------------------

        //--------------------------------------------------------------------------
        // find the block min-X and max-X
        // minimum block position X = current block position X - count of left block
        //--------------------------------------------------------------------------

        var minBlockX = blockX - countLengthX(curBoard, blockX, blockY, -1, blockValue);
        var maxBlockX = minBlockX + blockSizeX - 1;

        int stateCount = 0;
        boolean boardCopied = false;
        BoardObj childObj;
        int [] childBoard = new int[0];
        long curKey = 0;

        do {
            //--------------------------------------------------------------------------
            // calculate the space min-X and max-X of next position
            //--------------------------------------------------------------------------

            //minimum space position X = current space position X - count of left space
            var minSpaceX = emptyX - countLengthX(curBoard, emptyX, emptyY, -1, G_EMPTY_BLOCK);

            //maximum space position X = current space position X + count of right space
            var maxSpaceX = emptyX + countLengthX(curBoard, emptyX, emptyY, +1, G_EMPTY_BLOCK);

            //block left-right (X) range must less or equal to left-right (X) space size
            if(minBlockX < minSpaceX || maxBlockX > maxSpaceX) return stateCount;

            if(!boardCopied) {
                //first time, copy board array & set curKey
                childBoard = curBoard.clone();
                curKey = boardObj.key;
                boardCopied = true;
            }

            //slide empty-block up|down
            for(var x = minBlockX ; x <= maxBlockX; x++) {
                childBoard[x+emptyY*COLUMNS] = blockValue;
                childBoard[x+(emptyY + blockSizeY * directionY)*COLUMNS] = G_EMPTY_BLOCK;
            }

            childObj = new BoardObj(childBoard, gBoard2Key(childBoard ,false));
            if(parentKey != 0) {
                //-----------------------------------------------
                // parentKey != 0 means move more than one step
                //-----------------------------------------------
                stateCount += statePropose(childObj, parentKey);
            } else {
                stateCount += statePropose(childObj, curKey);
                parentKey = curKey;
            }

            emptyY -= directionY;

        } while (emptyY >= 0 && emptyY < ROWS && childBoard[emptyX+emptyY*COLUMNS] == G_EMPTY_BLOCK);

        return stateCount;
    }

    //---------------------------------------------------
    // slide empty-cell left or right by directionX (-1 or 1)
    //
    // directionX: 1: empty right (block left), -1: empty right (block left)
    //
    // return how many new state created
    //---------------------------------------------------
    private int slideHorizontal(BoardObj boardObj, long parentKey, int emptyX, int emptyY, int directionX) {
        int blockX, blockY; //block position (x,y)
        int blockValue; //block value
        int blockSizeX, blockSizeY; //block style
        var curBoard = boardObj.board;
        //Find the block
        blockX = emptyX + directionX;
        if(blockX < 0 || blockX >= COLUMNS) return 0; //out of range

        blockY = emptyY;

        if((blockValue = curBoard[blockX+blockY*COLUMNS]) <= G_EMPTY_BLOCK) return 0; //empty
        blockSizeX = gBlockStyle[gBlockBelongTo[blockValue]][0]; //block size X
        blockSizeY = gBlockStyle[gBlockBelongTo[blockValue]][1]; //block size Y

        //Begin horizontal move ------------------

        //--------------------------------------------------------------------
        // block slide left|right: must block size Y can slide to empty space
        //
        //   min-X <---- empty space ----> max-X
        //
        //    --+-- min-Y
        //      |          +---+   --+-- min-Y
        //      |          |   |     |
        //  empty space    |   |   block
        //      |          |   |     |
        //      |          +---+   --+-- max-Y
        //    --+-- max-Y
        //
        //   minBlockY must >= minSpaceY && maxBlockY must <= maxSpaceY
        //---------------------------------------------------------------------

        //--------------------------------------------------------------------------
        // find the block min-Y and max-Y
        // minimum block position Y = current block position Y - count of up block
        //--------------------------------------------------------------------------

        var minBlockY = blockY - countLengthY(curBoard, blockX, blockY, -1, blockValue);
        var maxBlockY = minBlockY + blockSizeY - 1;

        int stateCount = 0;
        boolean boardCopied = false;
        BoardObj childObj;
        int [] childBoard = new int[0];
        long curKey = 0;

        do {
            //--------------------------------------------------------------------------
            // calculate the space min-X and max-X of next position
            //--------------------------------------------------------------------------

            //minimum space position Y = current space position Y - count of up space
            var minSpaceY = emptyY - countLengthY(curBoard, emptyX, emptyY, -1, G_EMPTY_BLOCK);

            //maximum space position X = current space position X + count of right space
            var maxSpaceY = emptyY + countLengthY(curBoard, emptyX, emptyY, +1, G_EMPTY_BLOCK);

            //block up-down (Y) range must less or equal to up-down (Y) space size
            if(minBlockY < minSpaceY || maxBlockY > maxSpaceY) return stateCount;

            if(!boardCopied) {
                //first time, copy board array & set curKey
                childBoard = curBoard.clone();
                curKey = boardObj.key;
                boardCopied = true;
            }

            //slide empty-block left|right
            for(var y = minBlockY ; y <= maxBlockY; y++) {
                childBoard[emptyX+y*COLUMNS] = blockValue;
                childBoard[(emptyX + blockSizeX * directionX)+y*COLUMNS] = G_EMPTY_BLOCK;
            }

            childObj = new BoardObj(childBoard, gBoard2Key(childBoard, false));
            if(parentKey != 0) {
                //-----------------------------------------------
                // parentKey != 0 means move more than one step
                //-----------------------------------------------
                stateCount += statePropose(childObj, parentKey);
            } else {
                stateCount += statePropose(childObj, curKey);
                parentKey = curKey;
            }

            emptyX -= directionX;

        } while (emptyX >= 0 && emptyX < COLUMNS && childBoard[emptyX+emptyY*COLUMNS] == G_EMPTY_BLOCK);

        return stateCount;
    }

    //--------------------------------------------------------
    // Using recursion to trace the steps from bottom to top
    // then put the key value to array
    //--------------------------------------------------------

    static int counter = 0;

    private void getAnswerList(long curKey, ArrayList<Long> boardList) {
        Long keyMap = H.get(curKey); //{ key: curKey, value: parentKey }

        System.out.println("Been here " + ++counter + " times! Value is: " + keyMap);

        if(keyMap != null) getAnswerList(keyMap, boardList);
        boardList.add(curKey);
    }

    //--------------------------------------------------------------------
    // finds all empty cells and tries to move the block in each direction
    // into the empty cell just found
    //
    // return: how many states were created
    //--------------------------------------------------------------------
    private int explore(BoardObj boardObj) {
        int stateCount=0; //how many new state created
        int eCount = 0;   //empty count

    breakLoop:
    for (int emptyX = 0; emptyX < COLUMNS; emptyX++) {
        for (int emptyY = 0; emptyY < ROWS; emptyY++) {

            if (boardObj.board[emptyX+emptyY*COLUMNS] != G_EMPTY_BLOCK) continue;
            eCount++;

            //slide empty up ==> block down
            stateCount += slideVertical(boardObj, 0, emptyX, emptyY, -1); //block at Y-1

            //slide empty down ==> block up
            stateCount += slideVertical(boardObj, 0, emptyX, emptyY, +1); //block at Y+1

            //slide empty left ==> block right
            stateCount += slideHorizontal(boardObj, 0, emptyX, emptyY, -1); //block at X-1

            //slide empty right ==> block left
            stateCount += slideHorizontal(boardObj, 0, emptyX, emptyY, +1); //block at X+1
            if(eCount >= emptyCount) break breakLoop;
        }
    }
    return stateCount;
}

    //---------------------------------
    // public function : initial value
    //---------------------------------
    public Solver(String boardString) {

        emptyCount = gBlockCheck(boardString);

        Q = new LinkedList<>(); //queue for breadth first search
        H = new HashMap<>(); //hash maps for current state to parent state & collision detection

        initBoard = gEasyBoard(boardString);
    }

    //---------------------------------------------------------------------
    // public function : find the answer
    //
    // return: explore count:  number of moves needed
    //		   board list   :  list of all boards with one block moved each
    //---------------------------------------------------------------------
    static ArrayList<Long> boardList = new ArrayList<>();

    public ArrayList<Long> find () {
        //Put the initial state to BFS queue & hash map

        BoardObj first = new BoardObj(initBoard, gBoard2Key(initBoard, false));

        statePropose(first, 0);
        exploreCount = 1; //initial state

        while (Q.size() > 0) {
            BoardObj boardObj = Q.remove();

            if (reachGoal(boardObj.board)) {
                boardList = new ArrayList<>();

                 getAnswerList(boardObj.key, boardList);
                break; //found a solution
            }
            exploreCount += explore(boardObj); //find next board state
        }

        return boardList;
    }
}
