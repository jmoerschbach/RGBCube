package de.jonas.rgbcubecontrol;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import de.jonas.cubeModel.mediaPlayback.AudioPlayer;

/**
 * Created by Jonas on 10.03.2016.
 */
public class MusicPlayer implements AudioPlayer {

    private MediaPlayer player;
    private Context context;
    private final String TAG = "MusicPlayer";

    public MusicPlayer(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void play(String s) {

        if (s == null || s.isEmpty()) {
            Log.i(TAG, "empty audio filename. No sound will be played");
            return;
        }
        int resID = context.getResources().getIdentifier(s, "raw", context.getPackageName());
        if (resID == 0) {
            Log.w(TAG, s + " could not be found! No sound will be played");
            return;
        }
        player = MediaPlayer.create(context, resID);
        player.setLooping(true);
        player.start();


    }

    @Override
    public void stop() {
        if (player == null)
            return;
        player.stop();
        player.reset();
        player.release();
        player = null;
    }
}
