package pl.lukok.asudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
import pl.lukok.asudoku.activity.StartGame;
import pl.lukok.asudoku.entity.SudokuField;
import pl.lukok.asudoku.gui.GameOverDialog;
import pl.lukok.asudoku.gui.KeyboardDialog;

public class GameView extends View implements Animation.AnimationListener{

    private SudokuRenderer renderer;

    private SudokuGame game;

    private Animation animation1, animation2;

    private int fieldValue;

    public GameView(Context context) {
        super(context);

        StartGame gameActivity = (StartGame)(this.getContext());
        game = ((SudokuApplication)gameActivity.getApplication()).getGame();

        this.setRenderer(new SudokuRenderer(this.getContext(), this.getResources()));

        setFocusable(true);
        setFocusableInTouchMode(true);
        setDrawingCacheEnabled(true);

        animation1 = AnimationUtils.loadAnimation(this.getContext(), R.anim.to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(this.getContext(), R.anim.from_middle);
    }

    protected void onSizeChanged(int width, int height, int oldW, int oldH) {

        this.getRenderer().setWorldSize(width, height);
    }

    private int getValueByKeyCode(int keyCode) {
        if (keyCode >= KeyEvent.KEYCODE_1 && keyCode <= KeyEvent.KEYCODE_9) {
            return  keyCode-7;
        }

        if (keyCode == KeyEvent.KEYCODE_SPACE || keyCode == KeyEvent.KEYCODE_DEL) {
            return SudokuField.INIT_VALUE;
        }

        return -1;
    }

    public boolean onKeyDown (int keyCode, KeyEvent event) {

        int value = getValueByKeyCode(keyCode);
        setFieldValue(value);

        Log.d("GameView onKeyDown", (new Integer(keyCode)).toString() +  " ---> " + event.getDisplayLabel());
        invalidate();

        return super.onKeyDown(keyCode, event);
    }

    public void setFieldValue(int value) {
        if (value >= SudokuField.INIT_VALUE) {

            if(game.hasActiveField()) {
                this.fieldValue = value;
                this.clearAnimation();
                this.setAnimation(animation1);
                this.startAnimation(animation1);
            }


            if (game.isGameOver()) {
                this.showDialogGameOver();
            }
        }
    }

    public void showDialogKeyboard() {
        new KeyboardDialog(this.getContext(), this);
    }

    public void showDialogGameOver() {
        new GameOverDialog(this.getContext());
    }

    public boolean onTouchEvent(MotionEvent event) {

        Log.d("event: ", (new Float(event.getX())).toString()    +  ", " + (new Float(event.getY())).toString());
        Point position = this.getRenderer().convertToBoardPosition(event.getX(), event.getY());

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
 //           case MotionEvent.ACTION_POINTER_DOWN:
                if (game.selectField(position)) {
                    this.showDialogKeyboard();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:

                break;

        }
        invalidate();

        return super.onTouchEvent(event);
    }

    protected void onDraw(Canvas canvas) {

        this.getRenderer().render(canvas, game);
    }

    public SudokuRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(SudokuRenderer renderer) {
        this.renderer = renderer;
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (animation == animation1) {
            this.game.setActiveFieldValue(this.fieldValue);
            this.clearAnimation();
            this.setAnimation(animation2);
            this.startAnimation(animation2);
            game.checkActiveFieldErrors();
        }

        if (animation == animation2) {
          //  game.checkActiveFieldErrors();
         //   invalidate();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
