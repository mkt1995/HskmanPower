package com.example.hkspower.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hkspower.R
import com.example.hkspower.data.api.apis.model.ModelDoa
import com.example.hkspower.databinding.ActivityDashboardBinding
import com.example.hkspower.view.adapter.AdapterDoa
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


class DashboardActivity : AppCompatActivity() {
    var binding: ActivityDashboardBinding?=null
    
    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog
 
    var adapterDoa: AdapterDoa? = null
    var modelDoa: MutableList<ModelDoa> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        val linearLayoutManager = LinearLayoutManager(applicationContext)
        binding!!.rvListDoa.layoutManager = linearLayoutManager
        try {
            val obj = JSONObject(loadJSONFromAsset())
            val userArray = obj.getJSONArray("sort")
            for (i in 0 until userArray.length()) {
                val jsonObjectData = userArray.getJSONObject(i)
                val dataModel = ModelDoa()
                dataModel.mid = jsonObjectData.getString("Mid")
                dataModel.tid = jsonObjectData.getString("Tid")
                dataModel.amt = jsonObjectData.getString("amount")
                dataModel.narrations = jsonObjectData.getString("narration")

                modelDoa.add(dataModel)

            }
            val distinctLocations = modelDoa.distinctBy { it.mid }
            modelDoa.sortBy { it.mid }
            adapterDoa = AdapterDoa(distinctLocations as MutableList<ModelDoa>)
            binding!!.rvListDoa.adapter = adapterDoa
            adapterDoa!!.notifyDataSetChanged()
        }
        catch (e: JSONException) {
            e.printStackTrace()
            Log.d("dhsjdhjs",e.toString())
        }
        binding!!.sortBtn.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.transparentDialog)
            bottomSheetView = LayoutInflater.from(this).inflate(R.layout.sort_box, null)
            bottomSheetDialog.setContentView(bottomSheetView)

            bottomSheetView.findViewById<CheckBox>(R.id.mid_check).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {

                    modelDoa.sortBy { it.mid }
                   // adapterDoa?.notifyDataSetChanged()
                }
            }
            bottomSheetView.findViewById<CheckBox>(R.id.tid_check).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    modelDoa.sortBy { it.tid }
                   // adapterDoa?.notifyDataSetChanged()
                }
            }
            bottomSheetView.findViewById<CheckBox>(R.id.narrations_check).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    modelDoa.sortBy { it.narrations }
                   // adapterDoa?.notifyDataSetChanged()
                }
            }
            bottomSheetDialog.show()
        }
    }

    private fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}