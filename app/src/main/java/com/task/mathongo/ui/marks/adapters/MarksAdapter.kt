package com.task.mathongo.ui.marks.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.mathongo.databinding.LayoutItemsBinding
import com.task.mathongo.ui.marks.models.TestScore
import com.task.mathongo.util.AppUtils.Companion.isNetworkAvailable
import com.task.mathongo.util.AppUtils.Companion.showToastMsg


class MarksAdapter(
    val context: Context,
    val onEditClicked: EditScore,
    val onDeleteClicked: DeleteScore
): RecyclerView.Adapter<MarksAdapter.ViewHolder>() {

    var pos =-1
    private lateinit var bindings : LayoutItemsBinding
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }


    private val diffUtil = object: DiffUtil.ItemCallback<TestScore>(){

        override fun areItemsTheSame(oldItem: TestScore, newItem: TestScore): Boolean {
            Log.d("same", "${oldItem._id==newItem._id}")
            return oldItem._id==newItem._id
        }

        override fun areContentsTheSame(oldItem: TestScore, newItem: TestScore): Boolean {
            Log.d("same", "${oldItem==newItem}")
            return oldItem== newItem
        }


    }

    val markslist = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        bindings= LayoutItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(bindings.root)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {





        val data= markslist.currentList[position]
        var dropdownVisivle = false
        holder.itemView.apply {

            bindings.testmodel = data

            bindings.executePendingBindings()
            Log.d("xpos",pos.toString()+" "+position)




            bindings.editbtn.setOnClickListener {
                onEditClicked.editScore(data,data._id)
                bindings.dropdownoption.visibility = View.GONE
            }
            bindings.deletebtn.setOnClickListener {
                if(isNetworkAvailable(context)){
                    onDeleteClicked.deleteScore(markslist.currentList.get(position), data._id)
                    bindings.dropdownoption.visibility = View.GONE
                }else{
                    showToastMsg(context,"Please check your internet connection")
                }

            }




        }





    }

    override fun getItemCount(): Int = markslist.currentList.size


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface EditScore{
        fun editScore(position: TestScore, id: String)
    }


    interface DeleteScore{
        fun deleteScore(position: TestScore, id: String)
    }


}