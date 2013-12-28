package pl.lukok.asudoku.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import pl.lukok.asudoku.AudioPlayer;
import pl.lukok.asudoku.R;

public class GameOverDialog extends Dialog {

    public GameOverDialog(final Context context) {
        super(context);

        AudioPlayer.play(context, R.raw.triumphal_fanfare);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.title_game_over);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((Activity)context).finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
