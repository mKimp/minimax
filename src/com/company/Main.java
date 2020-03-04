package com.company;

import jdk.internal.org.objectweb.asm.tree.FrameNode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Board board = new Board();
        board.initialBoard();
        board.displayBoard();


        Scanner startGame = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Who want to go first? Press 0 : User go first; Press 1: Computer go first");
        int ans = startGame.nextInt();  // Read user input
        if (ans == 1){
            int x = random();
            int y = random();
            board.placeAMove(new Move(x,y),'X');
        }
        while(!board.isGameOver()){
            System.out.println("Enter the location: x y");
            Move m = new Move(startGame.nextInt(), startGame.nextInt());
            board.placeAMove(m, 'O');
            board.displayBoard();
            if(board.isGameOver()){
                break;
            }
            System.out.println("AI move");
            board.minimaxPruningWrapper(0,'X');
            //board.minimax_wrapper(0, 'X');
            long start = System.nanoTime();
            Move bestMove = board.findBestMove();
            long end = System.nanoTime();
            long diff = end - start;
            board.placeAMove(bestMove, 'X');
            double seconds = (double)diff/  1000000000;
            DecimalFormat numberFormat = new DecimalFormat("#.000000");
            System.out.println("Time taken to find the best move in seconds " + numberFormat.format(seconds));
            board.displayBoard();
        }
    }
    public static int random(){
        int max = 3; int min = 0;
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
