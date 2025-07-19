package com.example.myassssmentapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    private val entityList = mutableListOf<Entity>()
    private lateinit var entityAdapter: EntityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val entity = intent.getSerializableExtra("entity") as? Entity

        if (entity == null) {
            Toast.makeText(this, "No entity data found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val prop1Text: TextView = findViewById(R.id.detailProperty1)
        val prop2Text: TextView = findViewById(R.id.detailProperty2)
        val descText: TextView = findViewById(R.id.detailDescription)

        prop1Text.text = "Property 1: ${entity.property1}"
        prop2Text.text = "Property 2: ${entity.property2}"
        descText.text = "Description: ${entity.description}"

        // Optionally trigger fetch if needed
        // fetchDashboardData("yourKeypassHere")
    }

    private fun fetchDashboardData(keypass: String) {
        apiService.getDashboard(keypass).enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(
                call: Call<DashboardResponse>,
                response: Response<DashboardResponse>
            ) {
                if (response.isSuccessful) {
                    entityList.clear()
                    response.body()?.entities?.let { entityList.addAll(it) }
                    entityAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@DetailsActivity, "Failed to get data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Toast.makeText(this@DetailsActivity, "Network error", Toast.LENGTH_SHORT).show()
                Log.e("DashboardError", t.message ?: "unknown error")
            }
        })
    }
}
