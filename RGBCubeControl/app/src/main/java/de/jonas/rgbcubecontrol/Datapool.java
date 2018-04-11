package de.jonas.rgbcubecontrol;


import java.util.List;

import de.jonas.cubeModel.scheduling.AnimationScheduler;

/**
 * Created by Jonas on 13.04.2016.
 */
public class Datapool {

    private static Datapool instance;

    private List<DragListItem> items;
    private BluetoothCube cube;
    private AnimationScheduler scheduler;

    public static Datapool getInstance() {
        if(instance == null)
            instance = new Datapool();
        return instance;
    }

    private Datapool() {}

    public List<DragListItem> getItems() {
        return items;
    }

    public void setItems(List<DragListItem> items) {
        this.items = items;
    }

    public BluetoothCube getCube() {
        return cube;
    }

    public void setCube(BluetoothCube cube) {
        this.cube = cube;
    }

    public AnimationScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(AnimationScheduler scheduler) {
        this.scheduler = scheduler;
    }
}
