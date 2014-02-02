package pl.lukok.asudoku.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.ImageView;
import pl.lukok.asudoku.R;
import pl.lukok.asudoku.activity.Settings;

public class SudokuField {

    public static int INIT_VALUE = 0;

    private boolean editable = false;

    private boolean active = false;

    private boolean collision = false;

    private int value;

    private int originalValue;

 //   public ImageView image;

    public int x, y;

    public SudokuField(int x, int y) {

   //     this.image = new ImageView(context);
        //super(context);
        this.x = x;
        this.y = y;
        this.clear();
    }

    private void clear() {
        this.setValue(SudokuField.INIT_VALUE);
        this.removeCollision();
    }

    public void initField(int value) {
        this.setValue(value);
        this.setOriginalValue(value);
        this.setEditable(false);
    }

    public void initEditableField() {
        this.clear();
        this.setEditable(true);
    }

    public boolean isEmpty() {
        return this.getValue() == SudokuField.INIT_VALUE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    private void setOriginalValue(int originalValue) {
        this.originalValue = originalValue;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean hasCollision() {
        return collision;
    }

    public void setCollision() {
        this.collision = true;
    }

    public void removeCollision() {
        this.collision = false;
    }

    public String toString() {
        return Integer.toString(getValue());
    }

    public int getFontColor(Context context) {
        if (Settings.getHints(context) && hasCollision()) {
            return R.color.board_field_font_error;
        } else if (isEditable()) {
            return R.color.board_field_font_editable;
        }

        return R.color.board_field_font;
    }
}

