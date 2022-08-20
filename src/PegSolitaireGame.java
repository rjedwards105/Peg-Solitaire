import java.util.Scanner;
public class PegSolitaireGame
{
    /**
     * This method is responsible for everything from displaying the opening
     * welcome message to printing out the final thank you.  It will clearly be
     * helpful to call several of the following methods from here, and from the
     * methods called from here.  See the Sample Runs below for a more complete
     * idea of everything this method is responsible for.
     *
     * @param args - any command line arguments may be ignored by this method.
     */
    public static void main(String[] args)
    {
        // TODO: IMPLEMENT THIS METHOD
        System.out.println("\nWELCOME TO CS300 PEG SOLITAIRE!");
        System.out.println("===============================\n");
        System.out.println("Board Style Menu \n1) Cross \n2) Circle \n3) Triangle \n4) Simple T\n");
        Scanner keyboard = new Scanner(System.in);

        int selectBoard = readValidInt(keyboard, "Choose a board style: ", 1, 4);
        char[][] board = createBoard(selectBoard);

        while (true)
        {
            displayBoard(board != null ? board : new char[0][]);
            if (countPegsRemaining(board) == 1) {
                System.out.println("Congrats, you won!");
                break;
            } else if (countMovesAvailable(board) == 0) {
                System.out.println("It looks like there are no more legal moves.  Please try again.");
                break;
            } else {
                int[] move = readValidMove(keyboard, board);
                int col = move[0];
                int row = move[1];
                int dir = move[2];
                board = performMove(board, row, col, dir);
            }
        }

        System.out.println("\n==========================================");
        System.out.println("THANK YOU FOR PLAYING CS300 PEG SOLITAIRE!");
    }


    /**
     * This method is used to read in all inputs from the user.  After printing
     * the specified prompt, it will check whether the user’s input is in fact
     * an integer within the specified range.  If the user’s input does not
     * represent an integer or does not fall within the required range, print
     * an error message asking for a value within that range before giving the
     * user another chance to enter valid input.  The user should be given as
     * many chances as they need to enter a valid integer within the specified
     * range.  See the Sample Runs to see how these error messages should be
     * phrased, and to see how the prompts are repeated when multiple invalid
     * inputs are entered by the user.
     *
     * @param in - user input from standard in is ready through this.
     * @param prompt - message describing what the user is expected to enter.
     * @param min - the smallest valid integer that the user may enter.
     * @param max - the largest valid integer that the user may enter.
     * @return - the valid integer between min and max entered by the user.
     */
    public static int readValidInt(Scanner in, String prompt, int min, int max)
    {
        // TODO: IMPLEMENT THIS METHOD
        System.out.print(prompt);
        String keyboard = in.nextLine();
        try {
            int input = Integer.parseInt(keyboard);
            if (input > max || input < min) {
                return readValidInt(in, String.format("Please enter your choice as an integer between %d and %d: ", min, max), min, max);
            } else {
                return input;
            }
        } catch (NumberFormatException exception) {
            return readValidInt(in, String.format("Please enter your choice as an integer between %d and %d: ", min, max), min, max);
        }
    }


    /**
     * This method creates, initializes, and then returns a rectangular two
     * dimensional array of characters according to the specified boardType.
     * Initial configurations for each of the possible board types are depicted
     * below.  Note that pegs are displayed as @s, empty holes are displayed as
     * -s, and extra blank positions that are neither pegs nor holes within
     * each rectangular array are displayed as #s.
     *
     * @param boardType - 1-4 indicating one of the following initial patterns:
     *   1) Cross
     *     ###@@@###
     *     ###@@@###
     *     @@@@@@@@@
     *     @@@@-@@@@
     *     @@@@@@@@@
     *     ###@@@###
     *     ###@@@###
     *
     *   2) Circle
     *     #-@@-#
     *     -@@@@-
     *     @@@@@@
     *     @@@@@@
     *     -@@@@-
     *     #-@@-#
     *
     *   3) Triangle
     *     ###-@-###
     *     ##-@@@-##
     *     #-@@-@@-#
     *     -@@@@@@@-
     *
     *   4) Simple T
     *     -----
     *     -@@@-
     *     --@--
     *     --@--
     *     -----
     *
     * @return - the fully initialized two dimensional array.
     */
    public static char[][] createBoard(int boardType)
    {
        // TODO: IMPLEMENT THIS METHOD
        switch (boardType) {
            case 1:
                return new char[][]{
                        {'#', '#', '#', '@', '@', '@', '#', '#', '#'},
                        {'#', '#', '#', '@', '@', '@', '#', '#', '#'},
                        {'@', '@', '@', '@', '@', '@', '@', '@', '@'},
                        {'@', '@', '@', '@', '-', '@', '@', '@', '@'},
                        {'@', '@', '@', '@', '@', '@', '@', '@', '@'},
                        {'#', '#', '#', '@', '@', '@', '#', '#', '#'},
                        {'#', '#', '#', '@', '@', '@', '#', '#', '#'}
                };
            case 2:
                return new char[][]{
                        {'#', '-', '@', '@', '-', '#'},
                        {'-', '@', '@', '@', '@', '-'},
                        {'@', '@', '@', '@', '@', '@'},
                        {'@', '@', '@', '@', '@', '@'},
                        {'-', '@', '@', '@', '@', '-'},
                        {'#', '-', '@', '@', '-', '#'}
                };
            case 3:
                return new char[][]{
                        {'#', '#', '#', '-', '@', '-', '#', '#', '#'},
                        {'#', '#', '-', '@', '@', '@', '-', '#', '#'},
                        {'#', '-', '@', '@', '-', '@', '@', '-', '#'},
                        {'-', '@', '@', '@', '@', '@', '@', '@', '-'}
                };
            case 4:
                return new char[][]{
                        {'-', '-', '-', '-', '-'},
                        {'-', '@', '@', '@', '-'},
                        {'-', '-', '@', '-', '-'},
                        {'-', '-', '@', '-', '-'},
                        {'-', '-', '-', '-', '-'}
                };
            default:
                return null;
        }
    }


    /**
     * This method prints out the contents of the specified board using @s to
     * represent pegs, -s to represent empty hole, and #s to represent empty
     * positions that are neither pegs nor holes.  In addition to this, the
     * columns and rows of this board should be labelled with numbers starting
     * at one and increasing from left to right (for column labels) and from
     * top to bottom (for row labels).  See the Sample Runs for examples of how
     * these labels appear next to boards with different dimensions.
     *
     * @param board - the current state of the board being drawn.
     */
    public static void displayBoard(char[][] board)
    {
        // TODO: IMPLEMENT THIS METHOD
        System.out.print("\n  ");
        for (int i = 1; i < board[0].length + 1; i++)
        {
            System.out.print(String.format("%d ", i));
        }
        System.out.println();
        for (int i = 0; i < board.length; i++)
        {
            System.out.print(String.format("%d ", i + 1));
            for (int j = 0; j < board[i].length; j++)
            {
                System.out.print(String.format("%s ", board[i][j]));
            }
            System.out.println();
        }
    }


    /**
     * This method is used to read in and validate each part of a user’s move
     * choice: the row and column that they wish to move a peg from, and the
     * direction that they would like to move/jump that peg in.  When the
     * player’s row, column, and direction selection does not represent a valid
     * move, your program should report that their choice does not constitute a
     * legal move before giving them another chance to enter a different move.
     * They should be given as many chances as necessary to enter a legal move.
     * The array of three integers that this method returns will contain: the
     * user’s choice of column as the first integer, their choice of row as the
     * second integer, and their choice of direction as the third.
     *
     * @param in - user input from standard in is ready through this.
     * @param board - the state of the board that moves must be legal on.
     * @return - the user's choice of column, row, and direction representing
     *   a valid move and store in that order with an array.
     */
    public static int[] readValidMove(Scanner in, char[][] board)
    {
        // TODO: IMPLEMENT THIS METHOD
        int col = readValidInt(in, "Choose the COLUMN of a peg you'd like to move: ", 1, board[0].length);
        int row = readValidInt(in, "Choose the ROW of a peg you'd like to move: ", 1, board.length);
        int dir = readValidInt(in, "Choose a DIRECTION to move that peg 1) UP, 2) DOWN, 3) LEFT, or 4) RIGHT: ", 1, 4);
        if (isValidMove(board, row, col, dir))
        {
            return new int[]{col, row, dir};
        }
        else
        {
            String dirStr;
            switch (dir)
            {
                case 1:
                    dirStr = "UP";
                    break;
                case 2:
                    dirStr = "DOWN";
                    break;
                case 3:
                    dirStr = "LEFT";
                    break;
                case 4:
                    dirStr = "RIGHT";
                    break;
                default:
                    dirStr = "";
            }
            System.out.println(String.format("Moving a peg from row %d and column %d %s is not currently a legal move.\n", row, col, dirStr));
            return readValidMove(in, board);
        }
    }


    /**
     * This method checks whether a specific move (column + row + direction) is
     * valid within a specific board configuration.  In order for a move to be
     * a valid: 1)there must be a peg at position row, column within the board,
     * 2)there must be another peg neighboring that first one in the specified
     * direction, and 3)there must be an empty hole on the other side of that
     * neighboring peg (further in the specified direction).  This method
     * only returns true when all three of these conditions are met.  If any of
     * the three positions being checked happen to fall beyond the bounds of
     * your board array, then this method should return false.  Note that the
     * row and column parameters here begin with one, and may need to be
     * adjusted if your programming language utilizes arrays with zero-based
     * indexing.
     *
     * @param board - the state of the board that moves must be legal on.
     * @param row - the vertical position of the peg proposed to be moved.
     * @param column - the horizontal position of the peg proposed to be moved.
     * @param direction - the direction proposed to move/jump that peg in.
     * @return - true when the proposed move is legal, otherwise false.
     */
    public static boolean isValidMove(char[][] board, int row, int column, int direction)
    {
        // TODO: IMPLEMENT THIS METHOD
        boolean cond1 = board[row - 1][column - 1] == '@';
        boolean cond2;
        boolean cond3;
        switch (direction)
        {
            case 1:
                if (row <= 2)
                {
                    cond2 = false;
                    cond3 = false;
                }
                else
                {
                    cond2 = board[row - 2][column - 1] == '@';
                    cond3 = board[row - 3][column - 1] == '-';
                }
                break;
            case 2:
                if (row >= board.length - 1) {
                    cond2 = false;
                    cond3 = false;
                }
                else
                {
                    cond2 = board[row][column - 1] == '@';
                    cond3 = board[row + 1][column - 1] == '-';
                }
                break;
            case 3:
                if (column <= 2)
                {
                    cond2 = false;
                    cond3 = false;
                }
                else
                {
                    cond2 = board[row - 1][column - 2] == '@';
                    cond3 = board[row - 1][column - 3] == '-';
                }
                break;
            case 4:
                if (column >= board[0].length - 1)
                {
                    cond2 = false;
                    cond3 = false;
                }
                else
                {
                    cond2 = board[row - 1][column] == '@';
                    cond3 = board[row - 1][column + 1] == '-';
                }
                break;
            default:
                return false;
        }
        return cond1 && cond2 && cond3;
    }


    /**
     * The parameters of this method are the same as those of the isValidMove()
     * method.  However this method changes the board state according to this
     * move parameter (column + row + direction), instead of validating whether
     * the move is valid.  If the move specification that is passed into this
     * method does not represent a legal move, then do not modify the board.
     *
     * @param board - the state of the board will be changed by this move.
     * @param row - the vertical position that a peg will be moved from.
     * @param column - the horizontal position that a peg will be moved from.
     * @param direction - the direction of the neighbor to jump this peg over.
     * @return - the updated board state after the specified move is taken.
     */
    public static char[][] performMove(char[][] board, int row, int column, int direction)
    {
        // TODO: IMPLEMENT THIS METHOD
        if (isValidMove(board, row, column, direction))
        {
            board[row - 1][column - 1] = '-';
            switch (direction) {
                case 1:
                    board[row - 2][column - 1] = '-';
                    board[row - 3][column - 1] = '@';
                    break;
                case 2:
                    board[row][column - 1] = '-';
                    board[row + 1][column - 1] = '@';
                    break;
                case 3:
                    board[row - 1][column - 2] = '-';
                    board[row - 1][column - 3] = '@';
                    break;
                case 4:
                    board[row - 1][column] = '-';
                    board[row - 1][column + 1] = '@';
                default:
                    return board;
            }
        }
        else
        {
            return board;
        }
        return board;
    }


    /**
     * This method counts up the number of pegs left within a particular board
     * configuration, and returns that number.
     *
     * @param board - the board that pegs are counted from.
     * @return - the number of pegs found in that board.
     */
    public static int countPegsRemaining(char[][] board)
    {
        // TODO: IMPLEMENT THIS METHOD
        int count = 0;
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] == '@')
                {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * This method counts up the number of legal moves that are available to be
     * performed in a given board configuration.
     *
     * HINT: Would it be possible to call the isValidMove() method for every
     * direction and from every position within your board?  Counting up the
     * number of these calls that return true should yield the total number of
     * moves available within a specific board.
     *
     * @param board - the board that possible moves are counted from.
     * @return - the number of legal moves found in that board.
     */
    public static int countMovesAvailable(char[][] board)
    {
        // TODO: IMPLEMENT THIS METHOD
        int count = 0;
        for (int i = 1; i < board.length + 1; i++)
        {
            for (int j = 1; j < board[0].length + 1; j++)
            {
                for (int z = 1; z < 5; z++) {
                    if (isValidMove(board, i, j, z))
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}