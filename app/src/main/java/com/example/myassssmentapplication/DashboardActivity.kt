package com.example.myassssmentapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassssmentapplication.databinding.ActivityDashboardBinding
import com.example.myassssmentapplication.EntityAdapter
import com.example.myassssmentapplication.DashboardResponse
import com.example.myassssmentapplication.Entity
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var adapter: EntityAdapter
    private val entityList = mutableListOf<Entity>()

    @Inject
    lateinit var apiService: ApiService  // ✅ Injected with Hilt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        adapter = EntityAdapter(entityList) { entity ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("entity", entity)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Fetch data using injected ApiService
        val keypass = intent.getStringExtra("keypass") ?: ""
        fetchDashboardData(keypass)
    }

    private fun fetchDashboardData(keypass: String) {
        apiService.getDashboard(keypass).enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(
                call: Call<DashboardResponse>,
                response: Response<DashboardResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    entityList.clear()
                    entityList.addAll(response.body()!!.entities)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@DashboardActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
