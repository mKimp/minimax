package com.company;

import java.util.ArrayList;
import java.util.List;

public class Board {
   char [][] board; int size;
    ArrayList<Move> availabeMoves;
    ArrayList<MoveAndScore> MoveAndScore;
    public Board(int size){
        if (size == 1){
            board = new char[3][3];
            this.size = 3;
        }
    }
    public void initialBoard(){
        for (int i = 0; i < size ;++i){
            for (int j = 0; j < size; ++j){
                board[i][j] = '_';
            }
        }
    }

    public void displayBoard(){
        for (int i = 0; i < size ;++i){
            for (int j = 0; j < size; ++j){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    public boolean XWin_board3() {
        //vertical win
        if ((this.board[0][0] == 'X' && this.board[1][0] == 'X' && this.board[2][0] == 'X')
                ||  (this.board[0][1] == 'X' && this.board[1][1] == 'X' && this.board[2][1] == 'X')
                || (this.board[0][2] == 'X' && this.board[1][2] == 'X' && this.board[2][2] == 'X'))
            return true;
        //horizontal win
        if ((this.board[0][0] == 'X' && this.board[0][1] == 'X' && this.board[0][2] == 'X')
                || (this.board[1][0] == 'X' && this.board[1][1] == 'X' && this.board[1][2] == 'X')
                || (this.board[2][0] == 'X' && this.board[2][1] == 'X' && this.board[2][2] == 'X'))
            return true;
        // diagnose win
        if ((this.board[0][0] == 'X' && this.board[1][1] == 'X' && this.board[2][2] == 'X')
                || (this.board[0][2] == 'X' && this.board[1][1] == 'X' && this.board[2][0] == 'X'))
            return true;
        return false;
    }

    public boolean OWin_board3() {

        //vertical win
        if ((this.board[0][0] == 'O' && this.board[1][0] == 'O' && this.board[2][0] == 'O')
            || (this.board[0][1] == 'O' && this.board[1][1] == 'O' && this.board[2][1] == 'O')
            ||(this.board[0][2] == 'O' && this.board[1][2] == 'O' && this.board[2][2] == 'O'))
            return true;
        //horizontal win
        if ((this.board[0][0] == 'O' && this.board[0][1] == 'O' && this.board[0][2] == 'O')
                || (this.board[1][0] == 'O' && this.board[1][1] == 'O' && this.board[1][2] == 'O')
                || (this.board[2][0] == 'O' && this.board[2][1] == 'O' && this.board[2][2] == 'O'))
            return true;
        // diagnose win
        if ((this.board[0][0] == 'O' && this.board[1][1] == 'O' && this.board[2][2] == 'O')
                || (this.board[0][2] == 'O' && this.board[1][1] == 'O' && this.board[2][0] == 'O'))
            return true;
        return false;
    }

    public boolean isGameOver(){
        if (XWin_board3() || OWin_board3() || returnAvailableMove().isEmpty())
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

    // get the best move from the highest return value for X
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
    // alpha beta wrapper
    public void minimaxPruningWrapper(int depth, char player){
        MoveAndScore = new ArrayList<>();
        minimaxPruning(board, player, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    // alpha beta
    public int minimaxPruning(char[][]board, char player, int depth, int alpha, int beta){
        if (XWin_board3()) // check if x win
            return 1;
        if (OWin_board3()) //check if o win
            return -1;
        ArrayList<Move> availableCell = returnAvailableMove(); // return the available move
        if (availableCell.isEmpty())
            return 0;
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < availableCell.size(); ++i){
            Move m = availableCell.get(i);
            if (player == 'X'){
                placeAMove(m, player);
                int best = minimaxPruning(board,'O', depth +1, alpha, beta); // depth first search
                alpha = Math.max(alpha, best); //update alpha if max
                if(depth == 0){
                    MoveAndScore.add(new MoveAndScore(best,m)); // keep track the best score for AI to find the best move later
                }
                scores.add(best);
            }
            else if (player == 'O'){
                placeAMove(m, player);
                int best = minimaxPruning(board,'X', depth +1, alpha, beta);
                beta = Math.min(beta, best); // update beta if min
                scores.add(best);
            }
            // undo the move
            board[m.x][m.y] = '_';
            if (beta <= alpha) // condition to prune met , ignore the rest of branches.
                break;
        }
        // alternate the return value based on max or min
        if (player == 'X')
            return MaxBestMove(scores);
        else
            return MinBestMove(scores);
    }

    // minimax wrapper
    public void minimax_wrapper(int depth, char player){
        MoveAndScore = new ArrayList<>();
        minimax(board,player,depth);
    }

    // minimax
    public int minimax(char[][]board, char player, int depth){
        if (XWin_board3()) //check if X win
            return 1;
        if (OWin_board3()) //check if O win
            return -1;

        ArrayList<Move> availableCell = returnAvailableMove();
        if (availableCell.isEmpty()) //check if it draw
            return 0;
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < availableCell.size(); ++i){
            Move m = availableCell.get(i);
            if (player == 'X'){
                placeAMove(m, player);
                int best = minimax(board,'O', depth +1); //depth first search
                scores.add(best);
                if(depth == 0){  //keep track the best score for AI to find the best move later
                    MoveAndScore.add(new MoveAndScore(best,m));
                }
            }
            else if (player == 'O'){
                placeAMove(m, player);
                int best = minimax(board,'X',depth+1);  //depth first search
                scores.add(best);
            }
            //undo the move
            board[m.x][m.y] = '_';
        }
        // alternate the return value based on max or min
        if (player == 'X')
            return MaxBestMove(scores);
        else
            return MinBestMove(scores);
    }
    //return the maximum value in the list
    public int MaxBestMove(List<Integer> scores){
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < scores.size(); ++i){
            if (scores.get(i) > best) {
                best = scores.get(i);
            }
        }
        return best;
    }

    //return the minimum value in the list
    public int MinBestMove(List<Integer> scores){
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < scores.size(); ++i){
            if (scores.get(i) < best) {
                best = scores.get(i);
            }
        }
        return best;
    }
    //return the available moves
    public ArrayList<Move> returnAvailableMove(){
        availabeMoves = new ArrayList<>();
        for (int i = 0; i < size; ++i){
            for (int j = 0; j < size; ++j){
                if (board[i][j] == '_'){
                    availabeMoves.add(new Move(i,j));
                }
            }
        }
        return availabeMoves;
    }
}
// like tuple to contain a move and its score
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

