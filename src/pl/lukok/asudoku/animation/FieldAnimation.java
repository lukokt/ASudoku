package pl.lukok.asudoku.animation;

import pl.lukok.asudoku.entity.SudokuField;

/**
 * Created by lukasz on 12/27/13.
 */
public class FieldAnimation {

    private SudokuField field;
    private int newValue;

    public FieldAnimation(SudokuField field, int newValue) {
        this.field = field;
        this.newValue = newValue;
    }
}
