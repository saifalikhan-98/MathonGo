package com.task.mathongo.ui.marks.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.task.mathongo.R
import com.task.mathongo.databinding.FragmentAddMarksBinding
import com.task.mathongo.network.Resource
import com.task.mathongo.ui.marks.models.AddTestRequest
import com.task.mathongo.ui.marks.models.Scores
import com.task.mathongo.ui.marks.viewmodels.UpdateScore
import com.task.mathongo.ui.marks.viewmodels.UploadMarksViewModel
import com.task.mathongo.util.AppUtils
import com.task.mathongo.util.AppUtils.Companion.showToastMsg
import com.task.mathongo.util.Constants.Companion.TESTSCORE
import com.task.mathongo.util.MarksTextWatcher
import com.task.mathongo.util.PreferenceHelper
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*


@AndroidEntryPoint
class AddMarks : Fragment() {




    private lateinit var bindings : FragmentAddMarksBinding
    private val uploadViewModel : UploadMarksViewModel by activityViewModels()
    private val updateViewModel : UpdateScore by activityViewModels()
    private var isUpdate = false
    private lateinit var id : String
    var eitherTestWrittern = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindings = FragmentAddMarksBinding.inflate(layoutInflater)

        setUpTestSeries()
        handleCheckBox()
        clickHandlers()
        if(arguments!=null){
            val args = AddMarksArgs.fromBundle(requireArguments())
            Log.d("xargs", args.toString())
            if(args.update){
                val testScore = args.testscore
                id = testScore!!._id
                bindings.update = testScore
                bindings.marks = testScore?.scores
                bindings.executePendingBindings()
                isUpdate = true
                setUpCheckBox(testScore.scores)
            }else{
                bindings.phymarks.text.clear()
                bindings.chemmarks.text.clear()
                bindings.mathmarks.text.clear()
                bindings.phymarks.setHint(R.string.zero)
                bindings.chemmarks.setHint(R.string.zero)
                bindings.mathmarks.setHint(R.string.zero)
            }

        }

        return bindings.root
    }

    private fun handleCheckBox() {

        bindings.chemistry.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                eitherTestWrittern =true
                bindings.chemmarks.isEnabled=true
            }else{
                bindings.chemmarks.isEnabled=false
            }

        }
        bindings.physicscheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                eitherTestWrittern =true
                bindings.phymarks.isEnabled = true
            }else{
                bindings.phymarks.isEnabled =false
            }

        }
        bindings.math.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                eitherTestWrittern =true
                bindings.mathmarks.isEnabled = true
            }else{
                bindings.phymarks.isEnabled =false
            }

        }



    }

    override fun onResume() {
        super.onResume()
        bindings.chemmarks.addTextChangedListener(MarksTextWatcher(bindings.chemmarks))
        bindings.phymarks.addTextChangedListener(MarksTextWatcher(bindings.phymarks))
        bindings.mathmarks.addTextChangedListener(MarksTextWatcher(bindings.mathmarks))

    }

    override fun onPause() {
        super.onPause()
        bindings.chemmarks.removeTextChangedListener(MarksTextWatcher(bindings.chemmarks))
        bindings.phymarks.removeTextChangedListener(MarksTextWatcher(bindings.phymarks))
        bindings.mathmarks.removeTextChangedListener(MarksTextWatcher(bindings.mathmarks))
    }
    private fun setUpCheckBox(scores: Scores) {

        if(scores.Chemistry!=null){
            bindings.chemistry.isChecked = true
        }
        if(scores.Physics!=null){
            bindings.physicscheck.isChecked = true
        }
        if(scores.Mathematics!=null){
            bindings.math.isChecked = true
        }

    }

    private fun setUpTestSeries() {
        val dialog = AppUtils.showProgress(requireActivity())
        uploadViewModel.testSeries.observe(viewLifecycleOwner, Observer {

            when (it) {

                is Resource.Success -> {
                    bindings.progressbar.visibility = View.GONE
                    val series: ArrayAdapter<String> = ArrayAdapter(
                        requireContext(),
                        R.layout.list_items,
                        it.data!!
                    )
                    bindings.testseries.setAdapter(series)
                    dialog.dismiss()
                }
                is Resource.Error -> {
                    showToastMsg(requireContext(), it.message.toString())
                    dialog.dismiss()
                }
                is Resource.Loading -> {
                    dialog.show()
                }
            }
        })

    }

    private fun clickHandlers() {

        bindings.close.setOnClickListener {
            findNavController().popBackStack()
        }

        bindings.savemarks.setOnClickListener {
            uploadMarks()
        }
        bindings.datedialog.setOnClickListener {
            selectDate()
        }
        bindings.datewritten.setOnClickListener {
            selectDate()
        }

    }

    private fun selectDate() {
        val c = Calendar.getInstance();
        val mYear = c.get(Calendar.YEAR);
        val mMonth = c.get(Calendar.MONTH);
        val mDay = c.get(Calendar.DAY_OF_MONTH);
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                bindings.datewritten.apply {
                    setTextColor(Color.BLACK)
                    text = "${monthOfYear + 1}/$dayOfMonth/$year"
                }
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()

    }


    private fun uploadMarks() {


        val testSeries = bindings.testseries.text.toString()
        val testName = bindings.testname.text.toString()
        val testDate = bindings.datewritten.text.toString()
        val chem = bindings.chemmarks.text.toString().trim().toInt()
        val phy = bindings.phymarks.text.toString().trim().toInt()
        val math = bindings.mathmarks.text.toString().trim().toInt()



        if((TextUtils.isEmpty(testDate)||TextUtils.isEmpty(testSeries)||TextUtils.isEmpty(testName)) && !eitherTestWrittern ){
           showToastMsg(requireContext(), "Please fill all details and atleast one of the tests")
        }else{
            val scores = getScore()
            val addTestRequest = AddTestRequest(
                PreferenceHelper.getEmailId(requireContext()).toString(),
                testSeries,
                scores,
                testDate,
                testName,
                testSeries
            )
            Log.d("uploadDetails",scores.toString())
            if(isUpdate){





                updateViewModel.updateScore(id,  createJsonRequestBody("Chemistry" to chem,"Physics" to phy,"Mathematics" to math))
                checkIfDataUpdated()
            }else{
                uploadViewModel.uploadMarks(requireContext(), addTestRequest)
                checkIfDataUploaded()
            }

        }

    }

    private fun getScore(): Scores {
        val chem = bindings.chemmarks.text.toString().trim().toInt()
        val phy = bindings.phymarks.text.toString().trim().toInt()
        val math = bindings.mathmarks.text.toString().trim().toInt()

        return Scores(chem,phy,math)
    }

    private fun checkIfDataUpdated() {
        val dialog = AppUtils.showProgress(requireActivity())
       updateViewModel.updateStatus.observe(viewLifecycleOwner, Observer {

           when (it) {
               is Resource.Success -> {
                   dialog.dismiss()
                   showToastMsg(requireContext(), "Upload Successful")

               }
               is Resource.Error -> {
                   dialog.dismiss()
                   showToastMsg(requireContext(), "Error Uploading data")

               }
               is Resource.Loading -> {
                   dialog.show()
               }
           }
       })
    }

    private fun checkIfDataUploaded() {
        val dialog = AppUtils.showProgress(requireActivity())
        dialog.show()
        uploadViewModel.marksList.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Success -> {
                    dialog.dismiss()
                    showToastMsg(requireContext(), "Upload Successful")
                    val navController = findNavController()
                    navController.previousBackStackEntry?.savedStateHandle?.set(TESTSCORE, it.data?.testScore)
                    navController.popBackStack()
                }
                is Resource.Error -> {
                    dialog.dismiss()
                    bindings.progressbar.visibility = View.GONE
                    showToastMsg(requireContext(), "Error Uploading data")

                }
                is Resource.Loading -> {

                }
            }
        })
    }
    private fun createJsonRequestBody(vararg params: Pair<String, Int>) =
            JSONObject(mapOf(*params)).toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


}


