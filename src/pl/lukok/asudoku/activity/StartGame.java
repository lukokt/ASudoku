/**
 *
 */
package pl.lukok.asudoku.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import pl.lukok.asudoku.GameView;
import pl.lukok.asudoku.R;

/**
 * @author lukasz
 *
 */
public class StartGame extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("create options menu: ", "options");
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO te samo menu istnieje w main menu
        switch(item.getItemId()) {

            case R.id.menu_settings:
                startActivity(new Intent(this, Settings.class));
                return true;

        }

        return false;
    }
}
