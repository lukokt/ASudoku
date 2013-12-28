package pl.lukok.asudoku;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: lukasz
 * Date: 12/4/13
 * Time: 7:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class AudioPlayer {

    private static MediaPlayer mp;

    public static void play(Context context, int resource) {

        mp = MediaPlayer.create(context, resource);
        mp.start();

    }


}
