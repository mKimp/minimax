package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Board board = new Board();
        board.initialBoard();
        board.displayBoard();
        System.out.println(board.xWin());
    }
}