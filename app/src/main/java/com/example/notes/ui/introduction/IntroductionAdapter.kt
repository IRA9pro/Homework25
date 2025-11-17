package com.example.notes.ui.introduction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.data.models.IntroductionModel
import com.example.notes.R
import com.example.notes.databinding.ItemIntroductionBinding

class IntroductionAdapter(
    val introList: List<IntroductionModel>,
    val onStart: () -> Unit,
    val onSkip: (Int) -> Unit
) :
    RecyclerView.Adapter<IntroductionAdapter.IntroductionViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntroductionViewHolder {
        return IntroductionViewHolder(
            ItemIntroductionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: IntroductionViewHolder,
        position: Int
    ) {
        holder.onBind(introList[position], position)
    }

    override fun getItemCount(): Int {
        return introList.size
    }

    inner class IntroductionViewHolder(private val binding: ItemIntroductionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(introModel: IntroductionModel, position: Int) {
            binding.apply {
                tvTitle.text = introModel.title
                tvDescription.text = root.context.getString(introModel.description)

                tvImage.setAnimation(introModel.image)
                tvImage.playAnimation()

                btnStart.setOnClickListener { onStart() }
                btnSkip.setOnClickListener { onSkip(position) }

                if (position == introList.size - 1)
                    btnSkip.visibility = View.INVISIBLE
                else btnStart.visibility = View.INVISIBLE
            }
        }
    }
}