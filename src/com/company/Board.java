package com.company;

public class Board {
   char [][] board = new char[3][3];

    public void initialBoard(){
        for (int i = 0; i < 3 ;++i){
            for (int j = 0; j < 3; ++j){
                board[i][j] = '_';
            }
        }
    }

    public void displayBoard(){
        for (int i = 0; i < 3 ;++i){
            for (int j = 0; j < 3; ++j){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    public boolean xWin() {
        //vertical win
        if (this.board[0][0] == 'X' && this.board[1][0] == 'X' && this.board[2][0] == 'X')
            return true;
        if (this.board[0][1] == 'X' && this.board[1][1] == 'X' && this.board[2][1] == 'X')
            return true;
        if (this.board[0][2] == 'X' && this.board[1][2] == 'X' && this.board[2][2] == 'X')
            return true;
        //horizontal win
        if ((this.board[0][0] == 'X' && this.board[0][1] == 'X' && this.board[0][2] == 'X') || (this.board[1][0] == 'X'
                && this.board[1][1] == 'X' && this.board[1][2] == 'X') || (this.board[2][0] == 'X' && this.board[2][1] == 'X'
                && this.board[2][2] == 'X'))
            return true;
        // diagnose win
        if ((this.board[0][0] == 'X' && this.board[1][1] == 'X' && this.board[2][2] == 'X') ||
                (this.board[0][2] == 'X' && this.board[1][1] == 'X' && this.board[2][0] == 'X'))
            return true;

        return false;
    }
}
