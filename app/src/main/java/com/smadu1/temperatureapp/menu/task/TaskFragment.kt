package com.smadu1.temperatureapp.menu.task

import android.os.Bundle
import android.renderscript.Sampler.Value
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.smadu1.temperatureapp.databinding.FragmentTaskBinding
import com.smadu1.temperatureapp.menu.history.HistoryModel
import com.smadu1.temperatureapp.utils.Config.FIREBASE_HISTORY
import com.smadu1.temperatureapp.utils.Config.FIREBASE_REFERENCE
import com.smadu1.temperatureapp.utils.Config.FIREBASE_VALUE_PH
import com.smadu1.temperatureapp.utils.Config.FIREBASE_VALUE_SUHU
import com.smadu1.temperatureapp.utils.getCurrentDate


class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private val database = Firebase.database
    private val phAdapter by lazy { TaskAdapter() }
    private val suhuAdapter by lazy { TaskAdapter() }
    private var phValue = ""
    private var suhuValue = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFirebase()
        setupView()
    }

    private fun setupView() {
        binding.recyclerViewPh.adapter = phAdapter
        binding.recyclerViewSuhu.adapter = suhuAdapter
        val temperatureHistory = database.getReference(FIREBASE_HISTORY)

        binding.tvSaveData.setOnClickListener {
            temperatureHistory.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericType = object : GenericTypeIndicator<MutableList<HistoryModel>>() {}
                    val currentHistories: MutableList<HistoryModel> =
                        snapshot.getValue(genericType) ?: return

                    currentHistories.add(
                        0, HistoryModel(
                            timeStamp = getCurrentDate(), ph = phValue, suhu = suhuValue
                        )
                    )

                    temperatureHistory.setValue(currentHistories)
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireActivity(),
                                "data sudah tersimpan, silahkan check pada halaman history",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        }
    }

    private fun setupFirebase() {
        val temperatureReference = database.getReference(FIREBASE_REFERENCE)
        temperatureReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    phValue = snapshot.child(FIREBASE_VALUE_PH).value.toString()
                    suhuValue = snapshot.child(FIREBASE_VALUE_SUHU).value.toString()

                    if (suhuAdapter.currentList.isEmpty() && phAdapter.currentList.isEmpty()) {
                        suhuAdapter.submitList(listOf(suhuValue))
                        phAdapter.submitList(listOf(phValue))
                        return
                    }

                    if (suhuAdapter.currentList.first() != suhuValue) {
                        suhuAdapter.addValue(suhuValue)
                    }
                    if (phAdapter.currentList.first() != phValue) {
                        phAdapter.addValue(phValue)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireActivity(),
                    "ada suatu kesalahan, check koneksi internetmu dulu yah",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskFragment()
    }
}