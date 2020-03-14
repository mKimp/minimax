package com.company;

import jdk.internal.org.objectweb.asm.tree.FrameNode;

import javax.xml.stream.events.StartDocument;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Scanner startGame = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Size of the board? Press 1: 3 x 3; Press 2: 4 x 4");
        int b = startGame.nextInt();
        if (b == 1){
            Board board= new Board(b);
            board.initialBoard();
            board.displayBoard();
            System.out.println("Who want to go first? Press 0 : User go first; Press 1: Computer go first");
            int ans = startGame.nextInt();  // Read user input
            if (ans == 1) {
                int x = random();
                int y = random();
                System.out.println("X" + x + " " + y);
                board.placeAMove(new Move(x, y), 'X');
                board.displayBoard();
            }
            while (!board.isGameOver()) {
                System.out.println("Your move: enter x y coordinates");
                Move m = new Move(startGame.nextInt(), startGame.nextInt());
                board.placeAMove(m, 'O');
                board.displayBoard();
                if (board.isGameOver()) {
                    break;
                }
                System.out.println("----------------------WAITING AI MOVE .......................");
                //board.minimax_wrapper(0, 'X');
                board.minimaxPruningWrapper(0,'X');
                long start = System.nanoTime();
                Move bestMove = board.findBestMove();
                long end = System.nanoTime();
                long diff = end - start;
                board.placeAMove(bestMove, 'X');
                double seconds = (double) diff / 1000000000;
                DecimalFormat numberFormat = new DecimalFormat("#.000000");
                System.out.println("Time taken to find the best move using Minimax " + numberFormat.format(seconds) + " seconds");
                board.displayBoard();
            }
        }
        else{
            Boardsize4 board= new Boardsize4(b);
            board.initialBoard();
            board.displayBoard();

            System.out.println("Who want to go first? Press 0 : User go first; Press 1: Computer go first");
            int ans = startGame.nextInt();  // Read user input
            if (ans == 1) {
                int x = randomSize4();
                int y = randomSize4();
                System.out.println("X" + x + " " + y);
                board.placeAMove(new Move(x, y), 'X');
                board.displayBoard();
            }
            while (!board.isGameOver()) {
                System.out.println("Your move: enter x y coordinates");
                Move m = new Move(startGame.nextInt(), startGame.nextInt());
                board.placeAMove(m, 'O');
                board.displayBoard();
                if (board.isGameOver()) {
                    break;
                }
                System.out.println("----------------------WAITING AI MOVE .......................");
                //board.minimax_wrapper(0, 'X');
                board.minimaxPruningWrapper(0,'X');
                long start = System.nanoTime();
                Move bestMove = board.findBestMove();
                long end = System.nanoTime();
                long diff = end - start;
                board.placeAMove(bestMove, 'X');
                double seconds = (double) diff / 1000000000;
                DecimalFormat numberFormat = new DecimalFormat("#.000000");
                System.out.println("Time taken to find the best move using Minimax " + numberFormat.format(seconds) + " seconds");
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
        int max = 4; int min = 0;
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
