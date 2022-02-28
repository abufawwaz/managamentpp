package com.myfawwaz.mbs.kertek.networking


public class ApiJson {}


/**
 extends AppCompatActivity {


    var tvIsConnected: TextView? = null
    var etName: EditText? = null
    var etCountry: EditText? = null
    var etTwitter: EditText? = null
    var tvResult: TextView? = null
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)
        tvIsConnected = findViewById(R.id.tvIsConnected) as TextView?
        etName = findViewById(R.id.etName)
        etCountry = findViewById(R.id.etCountry)
        etTwitter = findViewById(R.id.etTwitter)
        tvResult = findViewById(R.id.tvResult) as TextView?
        checkNetworkConnection()
    }

    // check network connection
    fun checkNetworkConnection(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        var isConnected = false
        if (networkInfo != null && networkInfo.isConnected.also { isConnected = it }) {
            // show "Connected" & type of network "WIFI or MOBILE"
            tvIsConnected!!.text = "Connected " + networkInfo.typeName
            // change background color to red
            tvIsConnected!!.setBackgroundColor(-0x8333da)
        } else {
            // show "Not Connected"
            tvIsConnected!!.text = "Not Connected"
            // change background color to green
            tvIsConnected!!.setBackgroundColor(-0x10000)
        }
        return isConnected
    }


    @Throws(IOException::class, JSONException::class)
    private fun httpPost(myUrl: String): String? {
        val result = ""
        val url = URL(myUrl)

        // 1. create HttpURLConnection
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8")

        // 2. build JSON object
        val jsonObject = buidJsonObject()

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject)

        // 4. make POST request to the given URL
        conn.connect()

        // 5. return response message
        return conn.responseMessage + ""
    }


    private class HTTPAsyncTask :
        AsyncTask<String?, Void?, String>() {
        protected override fun doInBackground(vararg urls: String): String {
            // params comes from the execute() call: params[0] is the url.
            return try {
                try {
                    httpPost(urls[0])
                } catch (e: JSONException) {
                    e.printStackTrace()
                    "Error!"
                }
            } catch (e: IOException) {
                "Unable to retrieve web page. URL may be invalid."
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        override fun onPostExecute(result: String) {
            tvResult.setText(result)
        }
    }


    fun send(view: View?) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        // perform HTTP POST request
        if (checkNetworkConnection()) HTTPAsyncTask().execute("http://hmkcode.appspot.com/jsonservlet") else Toast.makeText(
            this,
            "Not Connected!",
            Toast.LENGTH_SHORT
        ).show()
    }

    @Throws(JSONException::class)
    private fun buidJsonObject(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.accumulate("name", etName!!.text.toString())
        jsonObject.accumulate("country", etCountry!!.text.toString())
        jsonObject.accumulate("twitter", etTwitter!!.text.toString())
        return jsonObject
    }

    @Throws(IOException::class)
    private fun setPostRequestContent(conn: HttpURLConnection, jsonObject: JSONObject) {
        val os = conn.outputStream
        val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
        writer.write(jsonObject.toString())
        Log.i(com.hmkcode.MainActivity::class.java.toString(), jsonObject.toString())
        writer.flush()
        writer.close()
        os.close()
    }

}*/
