package de.jonas.rgbcubecontrol;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import de.jonas.cubeModel.Cube;
import de.jonas.cubeModel.animations.Animation;
import de.jonas.cubeModel.scheduling.AnimationScheduler;
import de.jonas.cubeModel.scheduling.SchedulingState;
import de.jonas.rgbcubecontrol.bluetooth.BluetoothSPP;


public class LaunchActivity extends AppCompatActivity {

    public String TAG = "LaunchActivity";
    private static final int REQUEST_ENABLE_BT = 1;

    private AnimationScheduler scheduler;
    private BluetoothCube cube;
    private UIUpdater uiUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView icon = (ImageView)findViewById(R.id.connectionStatus);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"reset of auto connection retries");
                cube.resetAutoconnectionRetries();
            }
        });
        if (savedInstanceState == null) {
            Log.w(TAG, "onCreate");
            initBluetoothCube();
            initScheduling();
            initList();
        } else {
            Log.w(TAG, "restore");
            restore();
        }
        initUiUpdater();
    }

    private void initUiUpdater() {
        uiUpdater = new UIUpdater(new Runnable() {
            AnimationFragment f = (AnimationFragment) getFragmentManager().findFragmentById(R.id.animationFragment);
            ImageView icon = (ImageView) findViewById(R.id.connectionStatus);

            @Override
            public void run() {
                if (cube != null && cube.isConnected()) {
                    icon.setImageResource(R.drawable.connect);
                    f.updateStartStopButton();
                } else {
                    icon.setImageResource(R.drawable.disconnect);
                    if (scheduler.getState() != SchedulingState.STOPPED) {
                        scheduler.stop();
                    }
                    f.setStartStopButtonEnabled(false);
                }
                f.redraw();
            }
        }, 200);
    }

    private void restore() {
        cube = Datapool.getInstance().getCube();
        Log.w(TAG, cube.toString());
        scheduler = Datapool.getInstance().getScheduler();
    }

    private void initBluetoothCube() {
        BluetoothSPP bt = new BluetoothSPP(this);

        if (!bt.isBluetoothAvailable()) {
            Log.e(TAG, "BT not available...");
            Toast.makeText(this, "BT not supported, exiting...", Toast.LENGTH_LONG).show();
            finish();
        }
        if (!bt.isBluetoothEnabled()) {
            Log.e(TAG, "Please enable BT");
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
        } else {
            cube = new BluetoothCube(bt);
            cube.setupBluetooth();
            Datapool.getInstance().setCube(cube);
        }
    }

    void stopScheduler() {
        scheduler.stop();
    }

    void startScheduler(List<Animation> toPlay) {
        for (Animation item : toPlay)
            scheduler.addAnimation(item);
        scheduler.start();
    }

    private void initScheduling() {
        scheduler = new AnimationScheduler(new MusicPlayer(getApplicationContext()),cube);
        Datapool.getInstance().setScheduler(scheduler);
    }

    private void initList() {
        AnimationFragment frag = (AnimationFragment) getFragmentManager().findFragmentById(R.id.animationFragment);
        frag.fillList(findAllAnimations());
        scheduler.addObserver(frag);
    }

    private List<Animation> findAllAnimations() {
        List<Animation> allAnimations = new ArrayList<>();
        try {
            for (int i = 0; i < Animation.ANIMATIONS.length; i++) {
                Class<? extends Animation> foundClass = (Class<? extends Animation>) Class.forName("de.jonas.cubeModel.animations." + Animation.ANIMATIONS[i]);
                Constructor<? extends Animation> cons = foundClass.getDeclaredConstructor(Cube.class);
                Animation currentAnimation = cons.newInstance(new Object[]{cube});
                Log.i(TAG, currentAnimation.getAnimationName() + " found");
                allAnimations.add(currentAnimation);
                Log.i(TAG, "Added " + currentAnimation);
            }
        } catch (Exception e) {
            Log.e(TAG, "bla");
        }
        return allAnimations;
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiUpdater.startUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiUpdater.stopUpdates();
        //scheduler.stop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            scheduler.stop();
            cube.stopBluetooth();
        }
        Log.w(TAG, "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            Log.i(TAG, "Exit was pressed");
            Toast.makeText(this, R.string.exit_app, Toast.LENGTH_SHORT).show();
            cube.stopBluetooth();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            initBluetoothCube();
        }
    }
}