package pl.lukok.asudoku;

import android.graphics.Point;
import android.util.Log;
import pl.lukok.asudoku.algorithm.BoardFactory;
import pl.lukok.asudoku.algorithm.NaiveAlgorithm;
import pl.lukok.asudoku.entity.SudokuField;
import pl.lukok.asudoku.entity.SudokuBoard;

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
        this.init();
        this.setLevel(level);
    }

    private void init() {

        SudokuBoard board = BoardFactory.getBoard(new NaiveAlgorithm(), this.getLevel());
        this.setBoard(board);
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
         return (this.getBoard().getActiveField() != null);
    }

    public void setActiveFieldValue(int value) {

        if(this.hasActiveField()) {
            this.getBoard().getActiveField().setValue(value);
        }
    }

    public boolean selectField(Point point) {
        return this.selectField(point.x, point.y);
    }

    public void checkActiveFieldErrors() {

        SudokuField active = this.getBoard().getActiveField();

        for(int i = 0; i < SudokuBoard.FIELD_NUM; i++) {

            this.checkFieldErrors(active.x, i);
            this.checkFieldErrors(i, active.y);
        }

        Point start = this.getPartialBoardStartPoint(active);
        for(int x = start.x; x < start.x + 3; x++) {
            for(int y = start.y; y < start.y + 3; y++) {
                this.checkFieldErrors(x, y);
            }
        }
    }

    private void checkFieldErrors(int fieldX, int fieldY) {

        SudokuField field = this.getBoard().getField(fieldX, fieldY);
        field.removeCollision();

        for(int i = 0; i < SudokuBoard.FIELD_NUM; i++) {

            this.checkCollision(field, field.x, i);
            this.checkCollision(field, i, field.y);
        }

        Point start = this.getPartialBoardStartPoint(field);
        for(int x = start.x; x < start.x + 3; x++) {
            for(int y = start.y; y < start.y + 3; y++) {
                this.checkCollision(field, x, y);
            }
        }
    }

    private void checkCollision(SudokuField field, int x, int y) {

        if (field.isEmpty()) {
            return;
        }

        if (field.x == x && field.y == y) {
            return;
        }

        SudokuField next = this.getBoard().getField(x, y);

        if (field.getValue() == next.getValue()) {
            field.setCollision();
        }
    }

    private void removeActiveFieldErrors() {
        this.removeErrors(this.getBoard().getActiveField());
    }

    private void removeErrors(SudokuField active) {

        if (!active.hasCollision()) {
            return;
        }

        for(int i = 0; i < SudokuBoard.FIELD_NUM; i++) {

            this.removeCollisionWithActiveField(active.x, i);
            this.removeCollisionWithActiveField(i, active.y);
        }

        Log.d("removeErrors: ", active + " convert to " + (active.x-active.x%3) + ", " + (active.y-active.y%3));

        Point start = this.getPartialBoardStartPoint(active);
        for(int x = start.x; x < start.x + 3; x++) {
            for(int y = start.y; y < start.y + 3; y++) {

                this.removeCollisionWithActiveField(x, y);
            }
        }
    }

    private Point getPartialBoardStartPoint(SudokuField field) {
        return new Point(field.x-field.x%3, field.y-field.y%3);
    }

    private void removeCollisionWithActiveField(int x, int y) {

        SudokuField field = this.getBoard().getField(x, y);
        SudokuField active = this.getBoard().getActiveField();

        if (field.getValue() == active.getValue()) {
            field.removeCollision();
            active.removeCollision();
        }
    }

    private boolean selectField(int fieldX, int fieldY) {

        if (!this.getBoard().isValidField(fieldX, fieldY)) {
            Log.d("selectField not valid field: ", (new Integer(fieldX)).toString() + ", " + (new Integer(fieldY)).toString());
            return false;
        }

        SudokuField field = this.board.getField(fieldX, fieldY);

        if (!field.isEditable()) {
            return false;
        }

        this.board.setActiveField(field);
        return true;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isGameOver() {

        SudokuField field;
        for(int y = 0; y < SudokuBoard.FIELD_NUM; y++) {
            for(int x = 0; x < SudokuBoard.FIELD_NUM; x++) {
                field = this.board.getField(x, y);
                if (field.isEmpty() || field.hasCollision()) {
                    return false;
                }
            }
        }

        return true;
    }
}
