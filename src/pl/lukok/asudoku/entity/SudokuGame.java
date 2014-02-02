package pl.lukok.asudoku.entity;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import pl.lukok.asudoku.algorithm.BoardFactory;
import pl.lukok.asudoku.algorithm.NaiveAlgorithm;

/**
 * Game logic, validate moves etc
 */
public class SudokuGame {

    public static String GAME_LEVEL = "game_level";
    private SudokuBoard board;

    public static int LEVEL_EASY = 3;
    public static int LEVEL_MEDIUM = 4;
    public static int LEVEL_HARD = 5;

    protected int level = SudokuGame.LEVEL_MEDIUM;

    public SudokuGame(int level) {

        Log.d("LEVEL: ", Integer.toString(level));
        setLevel(level);
        init();
    }

    private void init() {

        SudokuBoard board = BoardFactory.getBoard(new NaiveAlgorithm(), getLevel());
        setBoard(board);
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
    }

    public int getLevel() {
        return level;
    }


    public boolean hasActiveField() {
         return (getBoard().getActiveField() != null);
    }

    public void setActiveFieldValue(int value) {

        if(hasActiveField()) {
            getBoard().getActiveField().setValue(value);
        }
    }

    public boolean selectField(Point point) {
        return selectField(point.x, point.y);
    }

    public void checkActiveFieldErrors() {

        SudokuField active = getBoard().getActiveField();
        board.checkFieldErrors(active);
    }

//    private void removeErrors(SudokuField active) {
//
//        if (!active.hasCollision()) {
//            return;
//        }
//
//        for(int i = 0; i < SudokuBoard.FIELD_NUM; i++) {
//
//            removeCollisionWithActiveField(active.x, i);
//            removeCollisionWithActiveField(i, active.y);
//        }
//
//        Log.d("removeErrors: ", active + " convert to " + (active.x-active.x%3) + ", " + (active.y-active.y%3));
//
//        Point start = getPartialBoardStartPoint(active);
//        for(int x = start.x; x < start.x + 3; x++) {
//            for(int y = start.y; y < start.y + 3; y++) {
//
//                removeCollisionWithActiveField(x, y);
//            }
//        }
//    }

//    private void removeCollisionWithActiveField(int x, int y) {
//
//        SudokuField field = getBoard().getField(x, y);
//        SudokuField active = getBoard().getActiveField();
//
//        if (field.getValue() == active.getValue()) {
//            field.removeCollision();
//            active.removeCollision();
//        }
//    }

    private boolean selectField(int fieldX, int fieldY) {

        if (!getBoard().isValidField(fieldX, fieldY)) {
            Log.d("selectField not valid field: ", (Integer.valueOf(fieldX)).toString() + ", " + (Integer.valueOf(fieldY)).toString());
            return false;
        }

        SudokuField field = board.getField(fieldX, fieldY);

        if (!field.isEditable()) {
            return false;
        }

        board.setActiveField(field);
        return true;
    }

    public void setLevel(int level) {
        this.level = level;
    }

//    public boolean isValid(SudokuBoard board) {
//
//
//        for(int x = 0; x < SudokuBoard.FIELD_NUM; x++) {
//            for(int y = 0; y < SudokuBoard.FIELD_NUM; y++) {
//                return (hasFieldError()) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }

    public boolean isGameOver() {
        return board.isSolved();
    }
}
