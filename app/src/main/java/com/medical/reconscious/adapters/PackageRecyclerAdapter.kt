package com.medical.reconscious.adapters

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.medical.reconscious.R
import com.medical.reconscious.network.respones.PackageResponse
import com.medical.reconscious.utils.heightAnimation
import com.medical.reconscious.utils.inflate
import kotlinx.android.synthetic.main.item_recyclerview_packages.view.*


class PackageRecyclerAdapter(private var packages: List<PackageResponse>, val itemClickListener: OnPackageItemClickListener) : RecyclerView.Adapter<PackageRecyclerAdapter.PackageHolder>() {

    var selected = -1

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): PackageHolder {
        val inflatedView = parent.inflate(R.layout.item_recyclerview_packages, false)
        return PackageHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: PackageHolder, position: Int) {
        val packageItem = packages.get(position)
        holder.bindPackage(packageItem, position, selected, itemClickListener)
    }

    fun setData(pack: List<PackageResponse>) {
        packages = pack
        notifyDataSetChanged()
    }

    fun setSelection(index: Int) {
        if(selected == index) {
            selected = -1
            notifyItemChanged(index)
        }
        else if(selected != -1) {
            val old = selected
            selected = index
            notifyItemChanged(index)
            notifyItemChanged(old)
        }
        else {
            selected = index
            notifyItemChanged(index)
        }

    }

    override fun getItemCount(): Int {
        return packages.size
    }

    class PackageHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v
        private var height = 0
        private var showed = false
        private var isSelected = false

        init {
            view.layout_description.viewTreeObserver.also {
                it.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        view.layout_description.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        height = view.layout_description.measuredHeight

                        val layoutParams: ViewGroup.LayoutParams = view.layout_description.layoutParams
                        layoutParams.height = 0
                        view.layout_description.layoutParams = layoutParams
                    }
                })
            }
        }

        fun bindPackage(packageItem: PackageResponse, position: Int, selected: Int, clickListener: OnPackageItemClickListener) {

            view.txt_name.text = packageItem.name
            view.txt_description.text = packageItem.description
            view.txt_price.text = "$" + packageItem.price.toString()

            if(position == selected) {
                isSelected = false
                selectCell()
            }
            else {
                isSelected = true
                unSelectCell()
            }

            if (packageItem.description != "") {
                view.img_more.setOnClickListener{
                    if (showed)
                        hideDetails()
                    else
                        showDetails()
                }
            }

            view.layout_main.setOnClickListener {
                clickListener.onItemClicked(position, isSelected)
            }
        }

        private fun selectCell() {

            view.layout_main.setBackgroundResource(R.drawable.layout_fragment_item_selected)
            view.txt_name.setTextColor(view.context.getColor(R.color.white))
            view.txt_price.setTextColor(view.context.getColor(R.color.white70))
            view.txt_description.setTextColor(view.context.getColor(R.color.white70))
            view.txt_description1.setTextColor(view.context.getColor(R.color.white70))
            view.img_more.setColorFilter(view.context.getColor(R.color.white))
            view.img_mark.setColorFilter(view.context.getColor(R.color.white))
        }

        private fun unSelectCell() {
            view.layout_main.setBackgroundResource(R.drawable.layout_fragment_item)
            view.txt_name.setTextColor(view.context.getColor(R.color.colorMain))
            view.txt_price.setTextColor(view.context.getColor(R.color.lightGreen))
            view.txt_description.setTextColor(view.context.getColor(R.color.black))
            view.txt_description1.setTextColor(view.context.getColor(R.color.black))
            view.img_more.setColorFilter(view.context.getColor(R.color.textColor))
            view.img_mark.setColorFilter(view.context.getColor(R.color.textColor))
        }

        private fun showDetails(){
            view.img_more.rotation = 180F

            heightAnimation(view.layout_description, 0, height, 600)

            showed = true
        }

        private fun hideDetails(){

            view.img_more.rotation = 0F

            heightAnimation(view.layout_description, view.layout_description.getMeasuredHeight(), 0, 600)

            showed = false
        }
    }
}

interface OnPackageItemClickListener{
    fun onItemClicked(position: Int, isSelected: Boolean)
}

