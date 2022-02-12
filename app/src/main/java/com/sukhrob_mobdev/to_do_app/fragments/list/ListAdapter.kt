package com.sukhrob_mobdev.to_do_app.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sukhrob_mobdev.to_do_app.R
import com.sukhrob_mobdev.to_do_app.data.models.Priority
import com.sukhrob_mobdev.to_do_app.data.models.ToDoData

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    fun setData(data: List<ToDoData>) {
        dataList = data
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var titleText = itemView.findViewById<TextView>(R.id.title_text)
        private val descriptionText = itemView.findViewById<TextView>(R.id.description_text)
        private val cardIndicator = itemView.findViewById<CardView>(R.id.priority_indicator)

        fun bind() {

            // SetOnClick listener
            itemView.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[adapterPosition])
                it.findNavController().navigate(action)
            }

            titleText.text = dataList[adapterPosition].title
            descriptionText.text = dataList[adapterPosition].description
            when (dataList[adapterPosition].priority) {
                Priority.HIGH -> cardIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.red
                    )
                )
                Priority.MEDIUM -> cardIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.yellow
                    )
                )
                Priority.LOW -> cardIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.bind()

    override fun getItemCount(): Int = dataList.size
}