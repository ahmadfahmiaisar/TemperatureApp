package com.smadu1.temperatureapp.menu.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.smadu1.temperatureapp.R
import com.smadu1.temperatureapp.databinding.FragmentHistoryBinding
import com.smadu1.temperatureapp.utils.Config
import com.smadu1.temperatureapp.utils.Config.FIREBASE_HISTORY

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val reference = Firebase.database.getReference(FIREBASE_HISTORY)

    private val historyAdapter by lazy { HistoryAdapter() }
    private var histories: MutableList<HistoryModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupFirebase()
    }

    private fun setupFirebase() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val genericType = object : GenericTypeIndicator<MutableList<HistoryModel>>() {}
                histories = snapshot.getValue(genericType) ?: return
                historyAdapter.updateData(histories?: return)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireActivity(),
                    "terdapat kesalahan dalam pengambilan history data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setupView() {
        binding.rvHistory.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvHistory.adapter = historyAdapter
        historyAdapter.setOnItemClickListener { item ->
            val indexItemDeleted = histories?.indexOf(item) ?: 0
            histories?.removeAt(indexItemDeleted)
            reference.setValue(histories)
                .addOnSuccessListener {
                    Toast.makeText(requireActivity(), "berhasil di hapus", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireActivity(), "tidak berhasil dihapus", Toast.LENGTH_SHORT).show()
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}