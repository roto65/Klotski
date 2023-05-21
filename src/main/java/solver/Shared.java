package solver;

//=======================================
// Klotski share variable & function
//=======================================

//---------------------------------------------------------------------------------------
//  0----------> X
//  |"HAAI"
//  |"HAAI"
//  |"JBBK"
//  |"JNOK"
//  |"P@@Q"
//  V
//  Y
//
// normal block = A..[
// empty  block = @
//---------------------------------------------------------------------------------------
// Block Shapes:
//    @, AA  BB CC DD EE FF GG  H  I  J  K  L  M   N  O  P  Q  R  S  T  U  V  W  X  Y  Z  [
//       AA                     H  I  J  K  L  M
//    1  2   3  4  5  6  7   8  9  10 11 12 13 14  15 16 17 18 19 20
//---------------------------------------------------------------------------------------

import static main.Constants.COLUMNS;

class Shared {

    static final char G_VOID_CHAR = '?';
    static final char G_EMPTY_CHAR = '@';

    static final int G_EMPTY_BLOCK = 1; //gBlockBelongTo index 1 ('@')
    static final int G_GOAL_BLOCK = 2; //gBlockBelongTo index 2 ('A')

    //convert char to index of block style
    //ASCII char   :       ?, @,  A,  B,C,D,E,F,G,  H, HI, J, K, L, M,  N, O, P, Q, R, S, T, U, V, W, X, Y, Z, [
    static final int [] gBlockBelongTo = {-1,0,4,2,2,2,2,2,2,3,3,3,3,3,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    //Index        :       0  1   2   3 4 5 6 7 8   9  10 11 12 13 14  15 16 17 18 19 20 21 22 23 24 25 26 27 28

    static final int [][] gBlockStyle = {{1,1},{1,1},{2,1},{1,2},{2,2}}; //block style:[x size, y size]

    static final int [][] gGoalPos = {{1,4},{2,4}}; //goal position: [x,y]

    //----------------------------------------------------------------------------
    // board string convert to board array with gBlockBelongTo index value
    //
    // param: boardString: a String containing the board layout coded as described
    //					   in the comment section above
    //
    // return boardArray : an array containing the offset of each letter from
    //					   boardString from the '@' char
    //----------------------------------------------------------------------------
    public static int[] gEasyBoard(String boardString) {
        char[] boardArray = boardString.toCharArray();

        int [] array = new int[boardArray.length];

        for (int i = 0; i < boardArray.length; i++) {
            array[i] = ((int) boardArray[i]) - ((int) G_VOID_CHAR);
        }
        return array;
    }

    //---------------------------------------------------------
    // transfer the board to 64 bits int
    // one char convert to 2 bits
    //
    // javascript: They are 64-bit floating point values,
    //             the largest exact integral value is 2 ^ 53
    //             but bitwise/shifts only operate on int32
    //
    // add support key for left-right mirror, 09/02/2017
    //---------------------------------------------------------
    public static long gBoard2Key(int[] board, boolean mirror) {
        int boardKey = 0;
        int primeBlockPos = -1;
        int invBase = 0;

        if (mirror) invBase = -(COLUMNS + 1); //key for mirror board

        for (var i = 0; i < board.length; i++) {

            //---------------------------------------------------------------------
            // Javascript only support 53 bits integer	(64 bits floating)
            // for save space, one cell use 2 bits
            // and only keep the position of prime minister block
            //---------------------------------------------------------------------
            // maximum length = (4 * 5 - 4) * 2 + 4
            //               = 32 + 4 = 36 bits
            //
            // 4 * 5 : max cell
            // - 4   : prime minister block size
            // * 2   : one cell use 2 bits
            // + 4   : prime minister block position use 4 bits
            //---------------------------------------------------------------------

            if (i % COLUMNS == 0) invBase += COLUMNS * 2; //key for mirror board
            int blockValue = board[mirror ? invBase - i : i];
            if (blockValue == G_GOAL_BLOCK) {
                //skip prime minister block, only keep position
                if (primeBlockPos < 0) primeBlockPos = i;
                continue;
            }
            boardKey = (boardKey << 2) + gBlockBelongTo[blockValue];  //bitwise/shifts must <= 32 bits)
        }
        boardKey = (boardKey * 16) + primeBlockPos; //shift 4 bits (0x00-0x0E)

        return boardKey;
    }

    //---------------------------------------
    // board verify
    //
    // return emptyCount for maximum deep check
    //---------------------------------------
    public static int gBlockCheck(String boardString) {

        char [] array = boardString.toCharArray();

        int emptyCount = 0;

        for (char c : array) {
            if (c == G_EMPTY_CHAR) {
                emptyCount++;
            }
        }
        return emptyCount;
    }
}