package com.collect.connect.FireBase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collect_n_connect.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AllSetsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: SetsAdapter

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_sets, container, false)
        recyclerView = view.findViewById(R.id.recyclerAllSets)
        progressBar = view.findViewById(R.id.progressAllSets)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SetsAdapter(emptyList())
        recyclerView.adapter = adapter

        cargarTodosLosSets()

        return view
    }

    private fun cargarTodosLosSets() {
        progressBar.visibility = View.VISIBLE
        val user = auth.currentUser ?: return

        db.collection("users")
            .document(user.uid)
            .collection("sets")
            .orderBy("timestamp") // opcional: ordena por fecha de creación
            .get()
            .addOnSuccessListener { snapshots ->
                val listaSets = mutableListOf<SetModel>()
                for (doc in snapshots.documents) {
                    val set = doc.toObject(SetModel::class.java)
                    if (set != null) {
                        listaSets.add(set)
                    }
                }
                adapter.actualizarDatos(listaSets)
                progressBar.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}