package de.jonas.rgbcubecontrol.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jonas.rgbcubecontrol.R
import de.jonas.rgbcubecontrol.ui.AnimationItem
import de.jonas.rgbcubecontrol.ui.AnimationList
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_animation.*


class AvailableAnimationsListAdapter(private val allAnimations: AnimationList,
                                     private val clickAction: (AnimationItem) -> Unit) : RecyclerView.Adapter<AvailableAnimationsListAdapter.ViewHolder>() {

    private val TAG = "AvailableAnimationsListAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_animation, parent, false)
        return ViewHolder(view, clickAction)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindAnimation(allAnimations[position])
    }

    override fun getItemCount(): Int = allAnimations.size

    inner class ViewHolder(override val containerView: View,
                           private val clickAction: (AnimationItem) -> Unit) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {


        private val clickVisualization: (AnimationItem) -> Unit = {
            val wasAlreadySelected = it.isSelected
            Log.w(TAG, "${it.animation.animationName} at adapterPosition=$adapterPosition wasAlreadySelected=$wasAlreadySelected")
            allAnimations.deselectAll()
            it.isSelected = !wasAlreadySelected
            allAnimations.animationItems.forEach({ Log.w(TAG, "${it.animation.animationName}: ${it.isSelected}") })
            notifyDataSetChanged()
        }

        private val completeClickAction: (AnimationItem) -> Unit = { clickAction(it); clickVisualization(it) }

        fun bindAnimation(animationItem: AnimationItem) {
            with(animationItem) {
                descriptionText.text = animation.animationName
                selectedRadioButton.isChecked = isSelected
                itemView.isSelected = isSelected
                itemView.setOnClickListener { completeClickAction(this) }
            }
        }
    }
}