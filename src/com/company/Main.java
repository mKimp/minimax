package com.company;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Scanner startGame = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Size of the board? Press 1: 3 x 3; Press 2: 4 x 4");
        int b = startGame.nextInt();
        // the board is 3x3
        if (b == 1){
            Board board= new Board(b);
            board.initialBoard();
            board.displayBoard();
            System.out.println("Who want to go first? Press 0 : User go first; Press 1: Computer go first");
            int ans = startGame.nextInt();  // Read user input
            if (ans == 1) { // if the computer goes first, we pick its first move as random
                int x = random();
                int y = random();
                System.out.println("X" + x + " " + y);
                board.placeAMove(new Move(x, y), 'X');
                board.displayBoard();
            }
            // continue running the game until the game is over
            while (!board.isGameOver()) {
                System.out.println("Your move: enter x y coordinates");
                Move m = new Move(startGame.nextInt(), startGame.nextInt());
                board.placeAMove(m, 'O'); // place the user move "O" on the board
                board.displayBoard();
                if (board.isGameOver()) { // check if the game is over after the user move
                    break;
                }
                System.out.println("----------------------WAITING AI MOVE .......................");
                /* Line below is minimax, uncomment it if want to use minimax. If use minimax, comment out the line using
                alpha beta pruning below it */

               // board.minimax_wrapper(0, 'X');
                board.minimaxPruningWrapper(0,'X'); // call alpha beta pruning on X
                long start = System.nanoTime(); //start time to find move
                Move bestMove = board.findBestMove(); // find the best move availabe
                long end = System.nanoTime(); // end time to find the move
                long diff = end - start; // time taken to find the best move
                board.placeAMove(bestMove, 'X');
                double seconds = (double) diff / 1000000000;
                DecimalFormat numberFormat = new DecimalFormat("#.000000");
                System.out.println("Time taken to find the best move " + numberFormat.format(seconds) + " seconds");
                board.displayBoard();
            }
        }
        //the board is 4x4
        else{
            Boardsize4 board= new Boardsize4(b);
            board.initialBoard();
            board.displayBoard();

            System.out.println("Who want to go first? Press 0 : User go first; Press 1: Computer go first");
            int ans = startGame.nextInt();  // Read user input
            if (ans == 1) {  // if the computer goes first, we pick its first move as random
                int x = randomSize4();
                int y = randomSize4();
                System.out.println("X" + x + " " + y);
                board.placeAMove(new Move(x, y), 'X');
                board.displayBoard();
            }
            while (!board.isGameOver()) {
                System.out.println("Your move: enter x y coordinates");
                Move m = new Move(startGame.nextInt(), startGame.nextInt());
                board.placeAMove(m, 'O'); // place the user move "O" on the board
                board.displayBoard();
                if (board.isGameOver()) { // check if the game is over after the user move
                    break;
                }
                System.out.println("----------------------WAITING AI MOVE .......................");
                /* Line below is minimax, uncomment it if want to use minimax. If use minimax, comment out the line using
                alpha beta pruning below it */

                //board.minimax_wrapper(0, 'X');
                board.minimaxPruningWrapper(0,'X'); // call alpha beta pruning on X
                long start = System.nanoTime();
                Move bestMove = board.findBestMove();
                long end = System.nanoTime();
                long diff = end - start; //start time to find move
                board.placeAMove(bestMove, 'X'); // find the best move availabe
                double seconds = (double) diff / 1000000000; // end time to find the move
                DecimalFormat numberFormat = new DecimalFormat("#.000000");  // time taken to find the best move
                System.out.println("Time taken to find the best move  " + numberFormat.format(seconds) + " seconds");
                board.displayBoard();
            }
        }
    }
    public static int random(){
        int max = 2; int min = 0;
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
    public static int randomSize4(){
        int max = 3; int min = 0;
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
