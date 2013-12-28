package pl.lukok.asudoku.entity;

import android.util.Log;

public class SudokuBoard {

    public static int FIELD_NUM = 9;

    protected SudokuField activeField = null;

    private SudokuField[][] board;

    public SudokuBoard() {

        this.init();
    }

    private void init() {

        this.setBoard(new SudokuField[SudokuBoard.FIELD_NUM][SudokuBoard.FIELD_NUM]);

        for(int x = 0; x < SudokuBoard.FIELD_NUM; x++) {
            for(int y = 0; y < SudokuBoard.FIELD_NUM; y++) {
                this.setField(new SudokuField(x, y));
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
        Log.d("active: ", (new Integer(field.x)).toString() + ", " + (new Integer(field.y)).toString());
        if (this.getActiveField() != null) {
            this.getActiveField().setActive(false);
        }

        field.setActive(true);

        this.activeField = field;
    }



}
