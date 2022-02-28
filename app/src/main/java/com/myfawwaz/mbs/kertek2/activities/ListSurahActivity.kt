package com.myfawwaz.mbs.kertek.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.android.gms.location.*
import com.myfawwaz.mbs.kertek.R
import com.myfawwaz.mbs.kertek.adapter.OrderAdapter
import com.myfawwaz.mbs.kertek.adapter.OrderAdapter.onSelectData
import com.myfawwaz.mbs.kertek.model.ModelOrder
import com.myfawwaz.mbs.kertek.networking.Api
import com.myfawwaz.mbs.kertek.utils.GetAddressIntentService
import kotlinx.android.synthetic.main.activity_list_order.*
import org.json.JSONArray
import org.json.JSONException
import java.text.NumberFormat
import java.util.*

/**
 * Created by Azhar Rivaldi on 22-12-2019.
 */

class ListSurahActivity : AppCompatActivity(), onSelectData {

    var orderAdapter: OrderAdapter? = null
    var progressDialog: ProgressDialog? = null
    var modelOrder: MutableList<ModelOrder> = ArrayList()
    var hariIni: String? = null
    var tanggal: String? = null
    val jsonSavedkey: String? = null
    private var jsondata: String = ""

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var addressResultReceiver: LocationAddressResultReceiver? = null
    private var currentLocation: Location? = null
    private var locationCallback: LocationCallback? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_order)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Mohon Tunggu")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Sedang menampilkan data...")

        addressResultReceiver = LocationAddressResultReceiver(Handler())

        val dateNow = Calendar.getInstance().time
        hariIni = DateFormat.format("EEEE", dateNow) as String
        tanggal = DateFormat.format("d MMMM yyyy", dateNow) as String
        tvToday.setText("$hariIni,")
        tvDate.setText(tanggal)

        llTime.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@ListSurahActivity, loadwebview::class.java)) })

//listadd pragment kencel
/* val sendDetail = newInstance("detail") //jadwal sholat
llTime.setOnClickListener(View.OnClickListener {*/
  /* sendDetail.hide(currentFragment);
   sendDetail.show(detailScreen);
   sendDetail.addToBackStack(null);
   sendDetail.commit();*/
  // sendDetail.show(supportFragmentManager, sendDetail.tag)
  // savedInstanceState.putString("detail", Bundle?)
   //("detail")
  // startActivity(new Intent(MainActivity.this, FragmentMenu.class));
//})
loadList.setOnClickListener(View.OnClickListener {
var inputDay = "&rgdt="+listDay.text.toString()

//Toast.makeText(this, inputDay, Toast.LENGTH_SHORT).show()
listSurah()
}

)

//map
llMosque.setOnClickListener(View.OnClickListener {
   startActivity(Intent(this@ListSurahActivity, MasjidActivity::class.java)) })
rvOrderan.setLayoutManager(LinearLayoutManager(this))
rvOrderan.setHasFixedSize(true)

fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
locationCallback = object : LocationCallback() {
   override fun onLocationResult(locationResult: LocationResult) {
       currentLocation = locationResult.locations[0]
       address
   }
}
startLocationUpdates()
//Methods get data

listSurah()
}

private fun listSurah(){
var inputDay = "&rgdt="+listDay.text.toString()
   //progressDialog!!.show()

   AndroidNetworking.get(Api.URL_LIST_SURAH+inputDay)
           .setPriority(Priority.MEDIUM)
           .build()
           .getAsJSONArray(object : JSONArrayRequestListener {

               override fun onResponse(response: JSONArray) {
modelOrder.clear()
                   progressBar2.visibility = View.VISIBLE

                   for (i in response.length() downTo 0 ) {
                       try {
                          // progressDialog!!.dismiss()

                           val dataApi = ModelOrder()
                           val jsonObject = response.getJSONObject(i)
                           var inttotal  = jsonObject.getString("total") + "ttl"
                           inttotal = inttotal.replace(".0000ttl", "")
                           val format: NumberFormat = NumberFormat.getCurrencyInstance()
                           format.setMaximumFractionDigits(0)
                           format.setCurrency(Currency.getInstance("IDR"))

                           dataApi.order_id = jsonObject.getString("order_id")
                           dataApi.firstname = jsonObject.getString("firstname")
                           dataApi.lastname = jsonObject.getString("lastname")
                           dataApi.telephone = jsonObject.getString("telephone")
                           dataApi.date_added = jsonObject.getString("date_added")
                           dataApi.totalb = jsonObject.getString("total")
                           dataApi.payment_address_1 = jsonObject.getString("payment_address_1")
                           dataApi.total =  format.format(inttotal.toIntOrNull()).replace("IDR","Rp. ")
                           //dataApi.belanja = jsonObject.getString("belanja")

                           modelOrder.add(dataApi)
                           showListSurah()
                       } catch (e: JSONException) {
                           e.printStackTrace()
                           Toast.makeText(this@ListSurahActivity, "Gagal menampilkan data!",
                                   Toast.LENGTH_SHORT).show()
                           progressBar2.visibility = View.VISIBLE
                       }
                       progressBar2.visibility = View.GONE

                   }
               }
               override fun onError(anError: ANError) {
                   progressDialog!!.dismiss()
                   Toast.makeText(this@ListSurahActivity, "Koneksi keserver terjadi Masalah!",
                           Toast.LENGTH_SHORT).show()
               }
           })
}

private fun showListSurah() {
// surahAdapter = SurahAdapter(this@ListSurahActivity, modelSurah, this)
// rvSurah!!.adapter = surahAdapter
orderAdapter = OrderAdapter(this@ListSurahActivity, modelOrder, this)
rvOrderan!!.adapter = orderAdapter
}

override fun onSelected(modelOrder: ModelOrder) {
val intent = Intent(this@ListSurahActivity, PrintActivity::class.java)
intent.putExtra("detailOrder", modelOrder!!.order_id //"221"
)
startActivity(intent)
/*     val sendDetail2 = newInstance("detail") //jadwal sholat

   sendDetail2.show(supportFragmentManager, sendDetail2.tag)
   //startActivity(new Intent(MainActivity.this, FragmentMenu.class));
*/
Toast.makeText(this, modelOrder!!.order_id, Toast.LENGTH_SHORT).show()
}

private fun startLocationUpdates() {
if (ContextCompat.checkSelfPermission(this,
               Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
   ActivityCompat.requestPermissions(this,
           arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
} else {
   val locationRequest = LocationRequest()
   locationRequest.interval = 1000
   locationRequest.fastestInterval = 1000
   locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
   fusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
}
}

private val address: Unit
get() {
   if (!Geocoder.isPresent()) {
       Toast.makeText(this@ListSurahActivity, "Can't find current address, ", Toast.LENGTH_SHORT).show()
       return
   }
   val intent = Intent(this, GetAddressIntentService::class.java)
   intent.putExtra("add_receiver", addressResultReceiver)
   intent.putExtra("add_location", currentLocation)
   startService(intent)
}

override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
   if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
       startLocationUpdates()
   } else {
       Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
   }
}
}

private inner class LocationAddressResultReceiver internal constructor(handler: Handler?) : ResultReceiver(handler) {
override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
   if (resultCode == 0) {
       address
   }
   if (resultCode == 1) {
       Toast.makeText(this@ListSurahActivity,
               "Address not found, ",
               Toast.LENGTH_SHORT).show()
   }
   val currentAdd = resultData.getString("address_result")
   showResults(currentAdd)
}
}

private fun showResults(currentAdd: String?) {
txtLocation!!.text = currentAdd
}

override fun onResume() {
super.onResume()
startLocationUpdates()
}

override fun onPause() {
super.onPause()
fusedLocationClient!!.removeLocationUpdates(locationCallback)
}
override fun onBackPressed() {

   super.onBackPressed()

}
companion object {
private const val LOCATION_PERMISSION_REQUEST_CODE = 2
}

override fun onSaveInstanceState(outState: Bundle) { // Here You have to save count value
super.onSaveInstanceState(outState)
Log.i("MyTag", "onSaveInstanceState")
outState.putString(jsonSavedkey, jsondata)
}

override fun onRestoreInstanceState(savedInstanceState: Bundle) { // Here You have to restore count value
super.onRestoreInstanceState(savedInstanceState)
Log.i("MyTag", "onRestoreInstanceState")
jsondata = savedInstanceState.getInt(jsonSavedkey).toString()
}
}
