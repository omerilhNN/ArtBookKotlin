package com.omrilhn.artbookkotlin

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omrilhn.artbookkotlin.databinding.RecyclerRowBinding

class EventAdapter(val eventList : ArrayList<Event>): RecyclerView.Adapter<EventAdapter.EventHolder>() {

    class EventHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EventHolder(binding)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.binding.recyclerViewTextView.text = eventList.get(position).name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,ArtActivity::class.java)
            intent.putExtra("info","old")
            intent.putExtra("id",eventList.get(position).id)//send data named "id" to the artActivity
            holder.itemView.context.startActivity(intent)
        }
    }
}