package pl.lukok.asudoku;

import android.util.Log;

public class UIHelper {

    public static void killApp() {
        Log.d("kill app: ", "mypid: " + android.os.Process.myPid());
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
