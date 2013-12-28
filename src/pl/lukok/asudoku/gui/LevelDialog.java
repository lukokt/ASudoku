package pl.lukok.asudoku.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import pl.lukok.asudoku.R;
import pl.lukok.asudoku.SudokuApplication;
import pl.lukok.asudoku.activity.ShowMainMenu;

public class LevelDialog extends AlertDialog.Builder {


    public LevelDialog(final ShowMainMenu context) {

        super(context);

        this.setItems(R.array.game_level, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int level) {
                ((SudokuApplication)context.getApplication()).startNewGame(level);
            }
        });

        setTitle(R.string.label_choose_level);
        this.show();
    }
}