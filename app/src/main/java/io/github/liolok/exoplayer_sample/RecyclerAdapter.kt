package io.github.liolok.exoplayer_sample

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class RecyclerAdapter(private val items: List<Item>) : RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])

    // Return the size of item list (invoked by the layout manager)
    override fun getItemCount() = items.size

    // Provide a reference to the views for each item
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) = with(itemView) {
            title.text = item.title
            duration.text = item.duration
            setOnClickListener {
                context.startActivity(Intent(context, DetailActivity::class.java).putExtra(Item.EXTRA_KEY, item))
            }  // on item view clicked, start detail activity and pass the item object as extra
        }  // bind an item object to its view
    }
}
