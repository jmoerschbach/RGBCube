package de.jonas.rgbcubecontrol;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;
import java.util.List;

import de.jonas.cubeModel.animations.Animation;

/**
 * Created by Jonas on 27.03.2016.
 */
public class ItemAdapter extends DragItemAdapter<DragListItem, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private List<ViewHolder> holders;

    public ItemAdapter(List<DragListItem> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        super(dragOnLongPress);
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        setHasStableIds(true);
        setItemList(list);
        holders = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holders.add(holder);
        return holder;
    }

    public void setEnabled(boolean enabled) {
        for(ViewHolder viewHolder : holders){
            viewHolder.mCheckBox.setEnabled(enabled);
        }
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Log.w("ItemAdapter", "onBindViewHolder");
        Animation animation = mItemList.get(position).getAnimation();
        holder.mText.setText(animation.getAnimationName());
        holder.mCheckBox.setChecked(mItemList.get(position).isEnabled());
        holder.itemView.setTag(mItemList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getId();
    }

    public class ViewHolder extends DragItemAdapter<DragListItem, ItemAdapter.ViewHolder>.ViewHolder {
        public TextView mText;
        public CheckBox mCheckBox;

        public ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId);
            mText = (TextView) itemView.findViewById(R.id.lv_textline);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.lv_checkbox);
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DragListItem item = (DragListItem) itemView.getTag();
                    if (item != null) // why on earth is this check needed??)
                        item.setEnabled(isChecked);
                }
            });
        }

        @Override
        public void onItemClicked(View view) {

            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
