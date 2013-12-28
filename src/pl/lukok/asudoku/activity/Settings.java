/**
 *
 */
package pl.lukok.asudoku.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import pl.lukok.asudoku.PreferenceUtil;
import pl.lukok.asudoku.R;
import pl.lukok.asudoku.SudokuApplication;

/**
 * @author lukasz
 *
 */
public class Settings extends PreferenceActivity {// implements SharedPreferences.OnSharedPreferenceChangeListener {


    public static boolean getHints(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("prefs_key_hints", true);

    }

    public static boolean getMusic(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("prefs_key_music", false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    addPreferencesFromResource(R.xml.settings);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new GamePreference()).commit();
    }

    public static class GamePreference extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
//            PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences_default, false);
            addPreferencesFromResource(R.xml.settings);
        }
    }
}
