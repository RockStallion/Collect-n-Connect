package com.example.collect_n_connect

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.collect.connect.Api.RetrofitClient
import com.collect.connect.Api.SetsAdapter
import com.collect.connect.Api.SetsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CatalogoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SetsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalogo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewSets)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SetsAdapter(emptyList())
        recyclerView.adapter = adapter

        fetchLegoSets()
    }

    private fun fetchLegoSets() {
        RetrofitClient.instance.getSets().enqueue(object : Callback<SetsResponse> {
            override fun onResponse(call: Call<SetsResponse>, response: Response<SetsResponse>) {
                if (response.isSuccessful) {
                    val sets = response.body()?.results ?: emptyList()
                    adapter.updateData(sets)
                } else {
                    Log.e("API_ERROR", "Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SetsResponse>, t: Throwable) {
                Log.e("API_FAILURE", "Fallo: ${t.message}")
            }
        })
    }
}