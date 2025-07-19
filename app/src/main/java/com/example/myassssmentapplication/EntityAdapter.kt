package com.example.myassssmentapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntityAdapter(
    private val entityList: List<Entity>,
    private val onItemClick: (Entity) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    inner class EntityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val prop1Text: TextView = itemView.findViewById(R.id.property1Text)
        private val prop2Text: TextView = itemView.findViewById(R.id.property2Text)

        fun bind(entity: Entity) {
            prop1Text.text = entity.property1
            prop2Text.text = entity.property2
            itemView.setOnClickListener { onItemClick(entity) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(entityList[position])
    }

    override fun getItemCount(): Int = entityList.size
}
