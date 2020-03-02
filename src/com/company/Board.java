package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Board {
   char [][] board = new char[3][3];
    ArrayList<Move> availabeMoves;
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
    public boolean XWin() {
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
    public boolean OWin() {
        //vertical win
        if (this.board[0][0] == 'O' && this.board[1][0] == 'O' && this.board[2][0] == 'O')
            return true;
        if (this.board[0][1] == 'O' && this.board[1][1] == 'O' && this.board[2][1] == 'O')
            return true;
        if (this.board[0][2] == 'O' && this.board[1][2] == 'O' && this.board[2][2] == 'O')
            return true;
        //horizontal win
        if ((this.board[0][0] == 'O' && this.board[0][1] == 'O' && this.board[0][2] == 'O') || (this.board[1][0] == 'O'
                && this.board[1][1] == 'O' && this.board[1][2] == 'O') || (this.board[2][0] == 'O' && this.board[2][1] == 'O'
                && this.board[2][2] == 'O'))
            return true;
        // diagnose win
        if ((this.board[0][0] == 'O' && this.board[1][1] == 'O' && this.board[2][2] == 'O') ||
                (this.board[0][2] == 'O' && this.board[1][1] == 'O' && this.board[2][0] == 'O'))
            return true;

        return false;
    }
    public boolean isDraw(){
        boolean isEmpty = false;
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                if (board[i][j] == '_')
                    isEmpty = true;
            }
        }
        return isEmpty;
    }
    public boolean isGameOver(){
        if (XWin() || OWin() || !isDraw())
            return true;
        return false;
    }

    public void placeAMove(Move m, char player){
        if (board[m.x][m.y] == '_'){
            board[m.x][m.y] = player;
        }
    }
    public void findBestMove(){
        ArrayList<Move> availableCell = returnAvailableMove();
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < availableCell.size(); ++i){
            Move m = availableCell.get(i);
            int score = minimax(board, 'X', 0);
            if (score > bestScore) {
                bestScore = score;
                placeAMove(m, 'X');
            }
        }
    }
    public int minimax(char[][]board, char player, int depth){
        if (XWin())
            return 1;
        else if (OWin())
            return -1;
        else if (isDraw())
            return 0;
        ArrayList<Move> availableCell = returnAvailableMove();
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < availableCell.size(); ++i){
            Move m = availableCell.get(i);
            if (player == 'X'){
                placeAMove(m, 'X');
                int best = minimax(board,'O', depth +1);
                scores.add(best);
                //undo the move
                placeAMove(m, '_');
            }
            if (player == 'O'){
                placeAMove(m, 'O');
                int best = minimax(board,'X',depth+1);
                scores.add(best);
                //undo the move
                placeAMove(m, '_');
            }
        }
        if (player == 'X')
            return MaxBestMove(scores);
        return MinBestMove(scores);
    }

    public int MaxBestMove(List<Integer> scores){
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < scores.size(); ++i){
            if (scores.get(i) > best)
                best = scores.get(i);
        }
        return best;
    }
    public int MinBestMove(List<Integer> scores){
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < scores.size(); ++i){
            if (scores.get(i) < best)
                best = scores.get(i);
        }
        return best;
    }
    public ArrayList<Move> returnAvailableMove(){
        availabeMoves = new ArrayList<Move>();
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                if (board[i][j] == '_'){
                    availabeMoves.add(new Move(i,j));
                }
            }
        }
        return availabeMoves;
    }

}
class Move {
    int x; int y;
    public Move(int x, int y){
        this.x = x;
        this.y = y;
    }
}

