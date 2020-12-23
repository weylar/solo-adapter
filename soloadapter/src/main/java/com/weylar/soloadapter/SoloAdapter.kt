package com.weylar.soloadapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


class SoloAdapter<V : ViewBinding, D>(
    private val view: (ViewGroup) -> V,
    private val bind: V.(D, Int, MutableList<Any>) -> Unit,
    diffCallback: DiffUtil.ItemCallback<D>
) : ListAdapter<D, SoloAdapter.ViewHolder<V>>(diffCallback) {

    class ViewHolder<V : ViewBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(view.invoke(parent))

    override fun onBindViewHolder(
        holder: ViewHolder<V>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val data = getItem(position)
        bind.invoke(holder.binding, data, position, payloads)
        super.onBindViewHolder(holder, position, payloads)
    }


    private class ViewBindingExt(private val view: View) : ViewBinding {
        override fun getRoot() = view
    }


    companion object {

         fun  <V: ViewBinding, D> bind(
            bindingProvider: (layoutInflater: LayoutInflater) -> V,
            diffCallback: DiffUtil.ItemCallback<D> = SoloDiffCallback(),
             binder: V.(D, Int, MutableList<Any>?) -> Unit,
        ) = run {
            SoloAdapter({bindingProvider.invoke(LayoutInflater.from(it.context))},
             binder, diffCallback)
        }

        fun <D> bind(
            @LayoutRes layoutResource: Int,
            diffCallback: DiffUtil.ItemCallback<D> = SoloDiffCallback(),
            binder: View.(D, Int, MutableList<Any>) -> Unit
        ): SoloAdapter<ViewBinding, D> {
            return SoloAdapter({
                ViewBindingExt(
                    LayoutInflater.from(it.context).inflate(layoutResource, it, false)
                )
            }, { data, position, payload ->
                binder.invoke(this.root, data, position, payload) }, diffCallback)


        }
    }


    class SoloDiffCallback<D> : DiffUtil.ItemCallback<D>() {
        override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {}

}