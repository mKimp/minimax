package com.company;

import java.util.ArrayList;
import java.util.List;

public class Board {
   char [][] board = new char[3][3];
    ArrayList<Move> availabeMoves;
    ArrayList<MoveAndScore> MoveAndScore;
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

    public boolean isGameOver(){
        if (XWin() || OWin() || returnAvailableMove().isEmpty())
            return true;
        return false;
    }

    public void placeAMove(Move m, char player){
        if (board[m.x][m.y] == '_'){
            board[m.x][m.y] = player;
        }
        else{
            System.out.println("Illegal Move");
        }
    }

    public Move findBestMove(){
        int MAX = Integer.MIN_VALUE;
        int best = -1;

        for (int i = 0; i < MoveAndScore.size(); ++i) {
            if (MAX < MoveAndScore.get(i).score) {
                MAX = MoveAndScore.get(i).score;
                best = i;
            }
        }
        return MoveAndScore.get(best).m;
    }
    public void minimaxPruningWrapper(int depth, char player){
        MoveAndScore = new ArrayList<>();
        minimaxPruning(board, player, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int minimaxPruning(char[][]board, char player, int depth, int alpha, int beta){
        if (XWin())
            return 1;
        if (OWin())
            return -1;
        ArrayList<Move> availableCell = returnAvailableMove();
        if (availableCell.isEmpty())
            return 0;
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < availableCell.size(); ++i){
            Move m = availableCell.get(i);
            if (player == 'X'){
                placeAMove(m, player);
                int best = minimaxPruning(board,'O', depth +1, alpha, beta);
                alpha = Math.max(alpha, best);
                if (beta <= alpha)
                    break;
                scores.add(best);
                if(depth == 0){
                    MoveAndScore.add(new MoveAndScore(best,m));
                }
            }
            else if (player == 'O'){
                placeAMove(m, player);
                int best = minimaxPruning(board,'X', depth +1, alpha, beta);
                alpha = Math.min(alpha, best);
                if (beta <= alpha)
                    break;
                scores.add(best);
            }
            board[m.x][m.y] = '_';
        }
        if (player == 'X')
            return MaxBestMove(scores);
        else
            return MinBestMove(scores);
    }


    public void minimax_wrapper(int depth, char player){
        MoveAndScore = new ArrayList<>();
        minimax(board,player,depth);
    }

    public int minimax(char[][]board, char player, int depth){
        if (XWin())
            return 1;
        if (OWin())
            return -1;
        ArrayList<Move> availableCell = returnAvailableMove();
        if (availableCell.isEmpty())
            return 0;
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < availableCell.size(); ++i){
            Move m = availableCell.get(i);
            if (player == 'X'){
                placeAMove(m, player);
                int best = minimax(board,'O', depth +1);
                scores.add(best);
                if(depth == 0){
                    MoveAndScore.add(new MoveAndScore(best,m));
                }
            }
            else if (player == 'O'){
                placeAMove(m, player);
                int best = minimax(board,'X',depth+1);
                scores.add(best);
            }
            board[m.x][m.y] = '_';
        }
        if (player == 'X')
            return MaxBestMove(scores);
        else
            return MinBestMove(scores);
    }

    public int MaxBestMove(List<Integer> scores){
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < scores.size(); ++i){
            if (scores.get(i) > best) {
                best = scores.get(i);
            }
        }
        return best;
    }
    public int MinBestMove(List<Integer> scores){
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < scores.size(); ++i){
            if (scores.get(i) < best) {
                best = scores.get(i);
            }
        }
        return best;
    }
    public ArrayList<Move> returnAvailableMove(){
        availabeMoves = new ArrayList<>();
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
class MoveAndScore{
    int score;
    Move m;
    public MoveAndScore(int s, Move move){
        this.score = s;
        this.m = move;
    }
}
class Move {
    int x; int y;
    public Move(int x, int y){
        this.x = x;
        this.y = y;
    }
}

