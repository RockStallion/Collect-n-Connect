package com.collect.connect.FireBase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collect_n_connect.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileSetsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: SetsAdapter
    private lateinit var tvSinSets: TextView

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_sets, container, false)
        recyclerView = view.findViewById(R.id.recyclerProfileSets)
        progressBar = view.findViewById(R.id.progressProfileSets)
        tvSinSets = view.findViewById(R.id.tvSinSets)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SetsAdapter(emptyList())
        recyclerView.adapter = adapter

        cargarDosSetsPrincipales()

        return view
    }

    private fun cargarDosSetsPrincipales() {
        progressBar.visibility = View.VISIBLE
        val user = auth.currentUser ?: return

        db.collection("users")
            .document(user.uid)
            .collection("sets")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(2)
            .get()
            .addOnSuccessListener { snapshots ->
                val listaSets = mutableListOf<SetModel>()
                for (doc in snapshots.documents) {
                    val set = doc.toObject(SetModel::class.java)
                    if (set != null) {
                        listaSets.add(set)
                    }
                }
                progressBar.visibility = View.GONE

                if (listaSets.isEmpty()) {
                    tvSinSets.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    tvSinSets.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    adapter.actualizarDatos(listaSets)
                }
            }
            .addOnFailureListener { e ->
                progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}