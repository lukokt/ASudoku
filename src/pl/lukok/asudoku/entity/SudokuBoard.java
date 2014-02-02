package pl.lukok.asudoku.entity;

import android.graphics.Point;
import android.util.Log;
import pl.lukok.asudoku.SudokuUtil;

public class SudokuBoard {

    public static int FIELD_NUM = 9;

    protected SudokuField activeField = null;

    private SudokuField[][] board;

    public SudokuBoard() {

        this.init();
    }

    private void init() {

        setBoard(new SudokuField[SudokuBoard.FIELD_NUM][SudokuBoard.FIELD_NUM]);

        for(int x = 0; x < SudokuBoard.FIELD_NUM; x++) {
            for(int y = 0; y < SudokuBoard.FIELD_NUM; y++) {
                setField(new SudokuField(x, y));
            }
        }
    }

//    public void reset() {
//
//        this.clean();
//        //this.generateBoard();
//
//    }

    public SudokuField getField(int x, int y) {
//        this.validatePosition(x,y);
        return this.getBoard()[x][y];
    }

    public void setField(SudokuField field) {
//        this.validatePosition(x,y);
        this.getBoard()[field.x][field.y] = field;
    }

    public void setField(int x, int y, SudokuField field) {
        field.x = x; field.y = y;
        this.setField(field);
    }

    /**
     * @param x
     * @param y
     * @throws Exception
     */
    private void validatePosition(int x, int y) throws Exception {

        if ( x < 1 || x > SudokuBoard.FIELD_NUM || y < 1 || 9 > SudokuBoard.FIELD_NUM ) {
            throw new Exception("Invalide board position x,y ");
        }
    }

    public SudokuField[][] getBoard() {
        return board;
    }

    public boolean isValidField(int fieldX, int fieldY) {
        return (fieldX >= 0 && fieldX < FIELD_NUM && fieldY >= 0 && fieldY < FIELD_NUM);
    }

    public void setBoard(SudokuField[][] board) {
        this.board = board;
    }

    public SudokuField getActiveField() {
        return activeField;
    }

    public void setActiveField(SudokuField field) {
        Log.d("active: ", (Integer.valueOf(field.x)).toString() + ", " + (Integer.valueOf(field.y)).toString());
        if (this.getActiveField() != null) {
            this.getActiveField().setActive(false);
        }

        field.setActive(true);

        this.activeField = field;
    }

    public void checkFieldErrors(SudokuField field) {

        for(int i = 0; i < SudokuBoard.FIELD_NUM; i++) {

            checkFieldCollisions(field.x, i);
            checkFieldCollisions(i, field.y);
        }

        Point start = getPartialBoardStartPoint(field);
        for(int x = start.x; x < start.x + 3; x++) {
            for(int y = start.y; y < start.y + 3; y++) {
                checkFieldCollisions(x, y);
            }
        }
    }

    public boolean isValid(SudokuField field) {

        for(int i = 0; i < SudokuBoard.FIELD_NUM; i++) {

            if(hasCollision(field, field.x, i)) {
                return false;
            }
            if (hasCollision(field, i, field.y)) {
                return false;
            }
        }

        Point start = getPartialBoardStartPoint(field);
        for(int x = start.x; x < start.x + 3; x++) {
            for(int y = start.y; y < start.y + 3; y++) {
                if(hasCollision(field, x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasCollision(SudokuField field, int x, int y) {

        if (field.isEmpty()) {
            return false;
        }

        if (field.x == x && field.y == y) {
            return false;
        }

        return field.getValue() == getField(x, y).getValue();
    }

    private void checkFieldCollisions(int fieldX, int fieldY) {

        SudokuField field = getField(fieldX, fieldY);
        field.removeCollision();

        for(int i = 0; i < SudokuBoard.FIELD_NUM; i++) {

            checkCollision(field, field.x, i);
            checkCollision(field, i, field.y);
        }

        Point start = getPartialBoardStartPoint(field);
        for(int x = start.x; x < start.x + 3; x++) {
            for(int y = start.y; y < start.y + 3; y++) {
                checkCollision(field, x, y);
            }
        }
    }

    private void checkCollision(SudokuField field, int x, int y) {

        if (hasCollision(field, x, y)) {
            field.setCollision();
        }
    }

    private Point getPartialBoardStartPoint(SudokuField field) {
        return new Point(field.x-field.x%3, field.y-field.y%3);
    }

    public boolean isValid() {
        SudokuField field;
        for(int y = 0; y < SudokuBoard.FIELD_NUM; y++) {
            for(int x = 0; x < SudokuBoard.FIELD_NUM; x++) {
                field = getField(x, y);
                if (!isValid(field)) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isSolved() {
        return isFulfilled() && isValid();
    }

    private boolean isFulfilled() {

        for(int y = 0; y < SudokuBoard.FIELD_NUM; y++) {
            for(int x = 0; x < SudokuBoard.FIELD_NUM; x++) {
                if (getField(x, y).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void applyLevel(int level) {

        SudokuField field;
        for(int y = 0; y < SudokuBoard.FIELD_NUM; y+=3) {
            for(int x = 0; x < SudokuBoard.FIELD_NUM; x+=3) {
                int removed = 0;
                while (removed < level) {

                    field = getField(SudokuUtil.random(x, x + 3), SudokuUtil.random(y, y + 3));

                    if (!field.isEditable()) {
                        field.initEditableField();
                        removed++;
                    }
                }
            }
        }
    }
}
