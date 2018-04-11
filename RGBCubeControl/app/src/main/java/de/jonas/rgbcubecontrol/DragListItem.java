package de.jonas.rgbcubecontrol;

import android.os.Parcel;
import android.os.Parcelable;

import de.jonas.cubeModel.animations.Animation;

/**
 * Created by Jonas on 31.03.2016.
 */
public class DragListItem  {

    private final Long id;
    private final Animation animation;
    private boolean isEnabled;

    DragListItem(Long id, Animation animation) {
        this(id, animation, false);
    }

    DragListItem(Long id, Animation animation, boolean isEnabled) {
        this.id = id;
        this.animation = animation;
        this.isEnabled = isEnabled;
    }

    Long getId() {
        return id;
    }

    Animation getAnimation() {
        return animation;
    }

    boolean isEnabled() {
        return isEnabled;
    }

    void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String toString() {
        return "id: " + id + ", Animation: " + animation + ", enabled=" + isEnabled;
    }
}
