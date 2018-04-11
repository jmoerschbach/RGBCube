package de.jonas.rgbcubecontrol;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import de.jonas.cubeModel.animations.Animation;
import de.jonas.cubeModel.scheduling.SchedulingState;

/**
 * Created by Jonas on 04.04.2016.
 */
public class AnimationFragment extends Fragment implements Observer {
    private static final String TAG = "AnimationFragment";
    private DragListView dragListView;
    private ItemAdapter listAdapter;
    private List<DragListItem> dragListItems = new ArrayList<>();
    private Button startStopButton;
    private LaunchActivity host;
    private View v;
    private SchedulingState state;
    private boolean viewNeedsToBeRedrawn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.animation_fragment, container, false);
        dragListView = (DragListView) v.findViewById(R.id.dlv2);
        startStopButton = (Button) v.findViewById(R.id.button_start2);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start();
            }
        });
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Datapool.getInstance().setItems(dragListItems);

    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.w(TAG, "onActivityCreated");
        host = (LaunchActivity) getActivity();
        dragListView.setLayoutManager(new LinearLayoutManager(host));
        if (bundle != null) {
            dragListItems = Datapool.getInstance().getItems();
        }
        listAdapter = new ItemAdapter(dragListItems, R.layout.row_layout, R.id.lv_textline, true);
        dragListView.setAdapter(listAdapter, true);
        dragListView.setCanDragHorizontally(false);
        dragListView.setDragEnabled(true);
        dragListView.setScrollingEnabled(true);

        startStopButton.setText(state == SchedulingState.PLAYING ? R.string.button_stop : R.string.button_start);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        startStopButton = null;
        host = null;
        dragListView = null;
        listAdapter = null;
    }

    public void fillList(List<Animation> allAnimations) {
        dragListItems.clear();
        for (int i = 0; i < allAnimations.size(); i++) {
            dragListItems.add(new DragListItem(Long.valueOf(i), allAnimations.get(i)));
        }
    }

    public void button_start() {
        if (state != SchedulingState.PLAYING) {
            addSelectedAnimationsAndPlay();
        } else {
            host.stopScheduler();
        }
    }

    private void addSelectedAnimationsAndPlay() {
        List<Animation> toPlay = new ArrayList<>();
        for (DragListItem item : dragListItems) {
            if (item.isEnabled())
                toPlay.add(item.getAnimation());
        }
        Log.i(TAG, "added " + toPlay.size() + " animations");
        if (!toPlay.isEmpty())
            host.startScheduler(toPlay);
    }

    public void setStartStopButtonEnabled(boolean enabled) {
        startStopButton.setEnabled(enabled);
    }

    public void updateStartStopButton() {
        if (isAtLeastOneAnimationSelected() || isCurrentlyPlaying())
            setStartStopButtonEnabled(true);
        else
            setStartStopButtonEnabled(false);
    }

    private boolean isCurrentlyPlaying() {
        return state == SchedulingState.PLAYING;
    }

    private boolean isAtLeastOneAnimationSelected() {
        for (DragListItem i : dragListItems)
            if (i.isEnabled())
                return true;
        return false;
    }

    @Override
    public void update(Observable o, Object update) {
        if (!(update instanceof SchedulingState))
            return;
        state = (SchedulingState) update;
        viewNeedsToBeRedrawn = true;
        Log.w(TAG, "updating state to " + state.toString());
    }

    void redraw() {
        if (!viewNeedsToBeRedrawn)
            return;
        viewNeedsToBeRedrawn = false;
        Log.i(TAG, "redraw view");
        //can be null when activity has already been destroyed but update was triggered later
        if (dragListView != null) {
            ItemAdapter itemAdapter = (ItemAdapter) dragListView.getAdapter();
            itemAdapter.setEnabled(state != SchedulingState.PLAYING);
            dragListView.invalidate();
        }
        if (startStopButton != null) {
            startStopButton.setText((state == SchedulingState.PLAYING) ? R.string.button_stop : R.string.button_start);
            startStopButton.invalidate();
        }
        if (v != null) {
            v.invalidate();
        }
    }
}
