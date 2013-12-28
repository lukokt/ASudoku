package pl.lukok.asudoku.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import pl.lukok.asudoku.AudioPlayer;
import pl.lukok.asudoku.R;
import pl.lukok.asudoku.SudokuApplication;
import pl.lukok.asudoku.UIHelper;
import pl.lukok.asudoku.gui.LevelDialog;

public class ShowMainMenu extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.button_new_game).setOnClickListener(this);
        findViewById(R.id.button_continue).setOnClickListener(this);
        findViewById(R.id.button_info).setOnClickListener(this);
        findViewById(R.id.button_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        AudioPlayer.play(this, R.raw.punch );

        switch (v.getId()) {

        case R.id.button_new_game:
            this.startNewGame();
            break;

        case R.id.button_continue:
            this.continueGame();
            break;

        case R.id.button_info:
            startActivity(new Intent(this, ShowGameInfo.class));
            break;

        case R.id.button_exit:
            finish();
            UIHelper.killApp();

        default:
            break;
        }
    }

    protected void continueGame() {
        if (((SudokuApplication)getApplication()).hasGame()) {
            startActivity(new Intent(this, StartGame.class));
        } else {
            this.startNewGame();
        }
    }

    protected void startNewGame() {
        new LevelDialog(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("create options menu: ", "options");
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO te samo menu istnieje w grze
        switch(item.getItemId()) {

        case R.id.menu_settings:
            startActivity(new Intent(this, Settings.class));
            break;

        }

        return true;
    }
}
