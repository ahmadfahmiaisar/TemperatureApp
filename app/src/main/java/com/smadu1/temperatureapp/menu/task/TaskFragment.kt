package com.smadu1.temperatureapp.menu.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.smadu1.temperatureapp.databinding.FragmentTaskBinding
import com.smadu1.temperatureapp.utils.Config.FIREBASE_REFERENCE
import com.smadu1.temperatureapp.utils.Config.FIREBASE_VALUE_PH
import com.smadu1.temperatureapp.utils.Config.FIREBASE_VALUE_SUHU


class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private val phAdapter by lazy { TaskAdapter() }
    private val suhuAdapter by lazy { TaskAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFirebase()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewPh.adapter = phAdapter
        binding.recyclerViewSuhu.adapter = suhuAdapter
    }

    private fun setupFirebase() {
        val database = Firebase.database
        val reference = database.getReference(FIREBASE_REFERENCE)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val phValue = snapshot.child(FIREBASE_VALUE_PH).value.toString()
                    val suhuValue = snapshot.child(FIREBASE_VALUE_SUHU).value.toString()

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