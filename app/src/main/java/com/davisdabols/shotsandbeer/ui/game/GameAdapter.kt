package com.davisdabols.shotsandbeer.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davisdabols.shotsandbeer.databinding.ItemGamePieceBinding
import com.davisdabols.shotsandbeer.repository.models.GamePiece
import kotlin.properties.Delegates

class GameAdapter() : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    var guesses: List<GamePiece> by Delegates.observable(emptyList(), { _, old, new ->
        DiffUtil.calculateDiff(DifferenceUtil(old, new)).dispatchUpdatesTo(this)
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemGamePieceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        val item = guesses[position]
        holder.binding.gamePiece.tag = item
        holder.binding.item = item
    }

    override fun getItemCount() = guesses.size

    inner class ViewHolder(val binding: ItemGamePieceBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DifferenceUtil(private val old: List<GamePiece>, private val new: List<GamePiece>) : DiffUtil.Callback() {
        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            old[oldItemPosition].ID == new[newItemPosition].ID

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            old[oldItemPosition] == new[newItemPosition]
    }
}