package pl.lukok.asudoku;

import android.app.Application;
import android.content.Intent;
import pl.lukok.asudoku.activity.StartGame;
import pl.lukok.asudoku.entity.SudokuGame;

/**
 * Created with IntelliJ IDEA.
 * User: lukasz
 * Date: 11/17/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuApplication extends Application {

    private SudokuGame game;

    public boolean hasGame() {
        return this.getGame() != null;
    }

    public SudokuGame getGame() {
        return this.game;
    }

    public void startNewGame(int level) {

        game = new SudokuGame(level);
        Intent intent = new Intent(this, StartGame.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
