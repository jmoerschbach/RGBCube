package de.jonas.rgbcubecontrol.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jonas.rgbcubecontrol.R
import de.jonas.rgbcubecontrol.ui.AnimationItem
import de.jonas.rgbcubecontrol.ui.AnimationList
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_animation.*

class AvailableAnimationsListAdapter(private val allAnimations: AnimationList,
                                     private val itemClick: (AnimationItem) -> Unit) : RecyclerView.Adapter<AvailableAnimationsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_animation, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindAnimation(allAnimations[position])
    }

    override fun getItemCount(): Int = allAnimations.size

    class ViewHolder(override val containerView: View,
                     private val itemClick: (AnimationItem) -> Unit) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindAnimation(animationItem: AnimationItem) {
            with(animationItem) {

                descriptionText.text = animation.animationName

                itemView.setOnClickListener { itemClick(this) }

            }
        }
    }
}