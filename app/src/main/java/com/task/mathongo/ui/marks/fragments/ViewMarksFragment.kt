package com.task.mathongo.ui.marks.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.task.mathongo.R
import com.task.mathongo.databinding.FragmentViewMarksBinding
import com.task.mathongo.network.Resource
import com.task.mathongo.ui.marks.adapters.MarksAdapter
import com.task.mathongo.ui.marks.adapters.ScorePagingAdapter
import com.task.mathongo.ui.marks.models.TestScore
import com.task.mathongo.ui.marks.viewmodels.GetMarksViewModel
import com.task.mathongo.ui.marks.viewmodels.DeleteScore
import com.task.mathongo.ui.marks.viewmodels.PagingViewModel
import com.task.mathongo.util.AppUtils
import com.task.mathongo.util.AppUtils.Companion.isNetworkAvailable
import com.task.mathongo.util.AppUtils.Companion.showToastMsg
import com.task.mathongo.util.Constants.Companion.TESTSCORE
import com.task.mathongo.util.PreferenceHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ViewMarksFragment : Fragment(),ScorePagingAdapter.EditScore,ScorePagingAdapter.DeleteScore {

    private lateinit var bindings : FragmentViewMarksBinding

    private val pagingViewModel : PagingViewModel by activityViewModels()
    private val deleteScore : DeleteScore by activityViewModels()
    private lateinit var recyclerView: RecyclerView

    private lateinit var scoreAdapter : ScorePagingAdapter
    private lateinit var listOfScores : ArrayList<TestScore>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentViewMarksBinding.inflate(layoutInflater)

        listOfScores = ArrayList()
        recyclerView = bindings.marksrecyclerview

        checkEmailAndShowData()
        clickListener()
        return bindings.root
    }

    private fun clickListener() {

        bindings.addmarkbtn.setOnClickListener {
            findNavController().navigate(R.id.view_to_addmarks)
        }

    }

    override fun onResume() {
        super.onResume()



    }


    fun checkEmailAndShowData(){

        if(PreferenceHelper.getEmailId(requireContext())==null){

            val dialog= AppUtils.getUserEmail(requireContext())
            dialog.show()
            dialog.setOnDismissListener {

                    setUpRecyclerView()

            }


        }else{
            setUpRecyclerView()
        }

    }

    fun setUpRecyclerView(){
        val dialog = AppUtils.showProgress(requireContext())
        dialog.show()

        scoreAdapter = ScorePagingAdapter(requireContext(),this,this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = scoreAdapter

        lifecycleScope.launch {
            pagingViewModel.getData().collectLatest {
                scoreAdapter.submitData(it)
            }
        }


        dialog.dismiss()
    }
    override fun editScore(position:TestScore, id: String) {
        val testScore :TestScore = position
        val action = ViewMarksFragmentDirections.viewToAddmarks()
        action.setTestscore(testScore)
        action.update = true
        findNavController().navigate(action)

    }

    override fun deleteScore(position: TestScore, id: String) {

        deleteScore.deleteScore(id)
        val dialog = AppUtils.showProgress(requireActivity())

        deleteScore.deleteStatus.observe(viewLifecycleOwner, Observer {

            when(it){

                is Resource.Success ->{



                    lifecycleScope.launch {
                        pagingViewModel.getData().collectLatest {
                            scoreAdapter.submitData(it)

                        }
                    }

                    dialog.dismiss()
                    showToastMsg(requireContext(),"Test Score deleted Successfully")
                }
                is Resource.Error ->{

                    showToastMsg(requireContext(),"Error deleting test score")
                    dialog.dismiss()

                }
                is Resource.Loading ->{
                    dialog.show()
                }


            }

        })

    }




}