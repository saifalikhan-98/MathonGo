package com.task.mathongo.ui.marks.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.mathongo.R
import com.task.mathongo.databinding.LayoutItemsBinding
import com.task.mathongo.ui.marks.models.TestScore


class ScorePagingAdapter(
                         val context:Context,
                         val onEditClicked: EditScore,
                         val onDeleteClicked: DeleteScore,
                          ) : PagingDataAdapter<TestScore, ScorePagingAdapter.scoreAdapter>(diffCallback = diffUtil) {

    private lateinit var binding: LayoutItemsBinding
    var row_index=-1
    inner class scoreAdapter(private val binding: LayoutItemsBinding) : RecyclerView.ViewHolder(binding.root){

    }


    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<TestScore>(){

            override fun areItemsTheSame(oldItem: TestScore, newItem: TestScore): Boolean = oldItem._id==newItem._id


            override fun areContentsTheSame(oldItem: TestScore, newItem: TestScore): Boolean = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): scoreAdapter {
        binding = LayoutItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return scoreAdapter(binding)
    }

    override fun onBindViewHolder(holder: scoreAdapter, position: Int) {

        val currentItem = getItem(position)

        holder.itemView.apply {

            binding.testmodel = currentItem
            binding.executePendingBindings()





            binding.editbtn.setOnClickListener {
                binding.dropdownoption.visibility = View.GONE
                onEditClicked.editScore(currentItem!!, currentItem._id)

            }
            binding.deletebtn.setOnClickListener {
                binding.dropdownoption.visibility = View.GONE
                onDeleteClicked.deleteScore(currentItem!!, currentItem._id)

            }




        }



    }

    interface EditScore{
        fun editScore(position: TestScore, id: String)
    }


    interface DeleteScore{
        fun deleteScore(position: TestScore, id: String)
    }


}