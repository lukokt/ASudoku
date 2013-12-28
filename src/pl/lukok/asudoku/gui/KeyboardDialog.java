package pl.lukok.asudoku.gui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import pl.lukok.asudoku.GameView;
import pl.lukok.asudoku.R;

/**
 * Created with IntelliJ IDEA.
 * User: lukasz
 * Date: 11/24/13
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeyboardDialog extends Dialog {

    GameView view;

    public KeyboardDialog(Context context, GameView view) {

        super(context);

        this.setContentView(R.layout.keyboard);
        this.setTitle("Klawiatura");
        this.view = view;

        for(int i = 1; i <= 9; i++) {
            final int value = i;
            getButtonByValue(i).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setFieldValue(value);
                }
            });
        }

        this.show();
    }

    private void setFieldValue(int value) {
        this.view.setFieldValue(value);
        this.dismiss();
    }

    private Button getButtonByValue(int i) {

        switch (i) {
            case 1: return (Button)findViewById(R.id.keyboard_1);
            case 2: return (Button)findViewById(R.id.keyboard_2);
            case 3: return (Button)findViewById(R.id.keyboard_3);
            case 4: return (Button)findViewById(R.id.keyboard_4);
            case 5: return (Button)findViewById(R.id.keyboard_5);
            case 6: return (Button)findViewById(R.id.keyboard_6);
            case 7: return (Button)findViewById(R.id.keyboard_7);
            case 8: return (Button)findViewById(R.id.keyboard_8);
            case 9: return (Button)findViewById(R.id.keyboard_9);
            default: return null;
        }
    }
}
