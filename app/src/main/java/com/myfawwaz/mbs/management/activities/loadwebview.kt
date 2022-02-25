package com.myfawwaz.mbs.management.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
//import com.google.android.gms.common.util.Strings
import com.myfawwaz.mbs.management.R
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.util.*


class loadwebview : Activity() {
    private val TAG = "Main Activity"
    private var mActivity: Activity? = null

    //   private CoordinatorLayout mCLayout;
    private var webView: WebView? = null
    var message: EditText? = null
    var numberprint: EditText? = null
    var btnPrint: ImageView? = null
    var btnLoad: ImageView? = null
    var btnConn: ImageView? = null
    var btnBefore: ImageView? = null
    var btnNext: ImageView? = null
    var FONT_TYPE: Byte = 0
    private var content: String? = null
    private var mContext: Context? = null
    private var messagetv: TextView? = null
    private var id: String? = null
    private var url: String? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loadwebview_layout)
        val intent = intent
        id = intent.getStringExtra("detailOrder")
        // Get the application context
        if (id == null) id = ""
        mContext = applicationContext
        mActivity = this@loadwebview
        messagetv = findViewById<View>(R.id.tvinfo) as TextView
        message = findViewById<View>(R.id.txtAdd) as EditText
        btnLoad = findViewById<View>(R.id.btnLoad) as ImageView
            //web
        webView = findViewById<View>(R.id.web_view_layout) as WebView

        webView!!.webViewClient = WebViewClient()
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.domStorageEnabled = true
        webView!!.settings.setAppCacheEnabled(true)
        webView!!.settings.builtInZoomControls = true
        webView!!.settings.saveFormData = true
        webView!!.settings.setSupportZoom(false)
        webView!!.requestFocus()
        //fit view web
        webView!!.settings.setAppCacheEnabled(true)
        webView!!.settings.loadWithOverviewMode = true
        webView!!.settings.useWideViewPort = true
        url = "https://js.bakulansayur.com/"//Api.URL_PRINT + idx
        // printLoad(id!!) //load yg print

        webLoad(url!!)

        //ebd web
      //  btnPrint!!.setOnClickListener { printDemo() }
        var loadurl: Editable? = message!!.text
  btnLoad!!.setOnClickListener {webLoad(loadurl.toString()) }
 // btnConn!!.setOnClickListener { printConn() }
 /*   btnBefore!!.setOnClickListener {
      val idnext = id!!.toInt() - 1
      //webView.loadUrl(Api.URL_PRINT + idnext);
      // numberprint.setText(idnext);
      printLoad("" + idnext)
  }
 btnNext!!.setOnClickListener {
      val id = id!!.toInt() + 1
      //webView.loadUrl(Api.URL_PRINT + idnext);
      //numberprint.setText(idnext);
      printLoad("" + idnext)
  }
  if (btsocket == null) {
      val BTIntent = Intent(applicationContext, DeviceBlue::class.java)
      // this.startActivityForResult(BTIntent, DeviceBlue.REQUEST_CONNECT_BT);
  }*/
}

//=========

    protected fun webLoad(url: String) {
        messagetv!!.text = "please wait...loading"
        webView!!.loadUrl(url!!)
        message!!.setText(url)
        messagetv!!.text = "please wait...loading"

    }
protected fun printLoad(idx: String) {
  id = idx
  webView!!.webViewClient = WebViewClient()
  webView!!.settings.javaScriptEnabled = true
  webView!!.settings.domStorageEnabled = true
  webView!!.settings.setAppCacheEnabled(true)
  webView!!.settings.builtInZoomControls = true
  webView!!.settings.saveFormData = true
  webView!!.settings.setSupportZoom(false)
  webView!!.requestFocus()
  //fit view web
  webView!!.settings.setAppCacheEnabled(true)
  webView!!.settings.loadWithOverviewMode = true
  webView!!.settings.useWideViewPort = true
  val myctxt: Context = this //your activity or application context

  class MyJavaScriptInterface {
      @JavascriptInterface
      fun processHTML(html: String?) {
          content = html
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
              message!!.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY))
              messagetv!!.text = "Text Ready to edit or print"
              val txr = message!!.text.toString()
              txr.replace("\n".toRegex(), "")
              message!!.setText(txr)
          } else {
              message!!.setText(Html.fromHtml(content))
              val txr = message!!.text.toString()
              txr.replace("\n".toRegex(), "")
              message!!.setText(txr)
              messagetv!!.text = "Text Ready to edit or print"
          }
      }
  }
  /* Register a new JavaScript interface called HTMLOUT */webView!!.addJavascriptInterface(
      MyJavaScriptInterface(),
      "HTMLOUT"
  )

  /* WebViewClient must be set BEFORE calling loadUrl! */webView!!.webViewClient =
      object : WebViewClient() {
          override fun onPageFinished(view: WebView, url: String) {
              /* This call inject JavaScript into the page which just finished loading. */
              webView!!.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
              val f = getFileFromUrl(url)
              messagetv!!.text = "Text Ready to edit or print order no. $id"
              numberprint!!.setText(id)
              message!!.requestFocus()
          }

          private fun getFileFromUrl(url: String): File? {
              // TODO: Implement this method
              return null
          }
      }
  url = "https://js.bakulansayur.com/"//Api.URL_PRINT + idx
  messagetv!!.text = "Text on load"
  messagetv!!.text = "please wait..."
  webView!!.loadUrl(url!!)
}

//======
protected fun printBill() {
  if (btsocket == null) {
      val BTIntent = Intent(applicationContext, DeviceBlue::class.java)
      this.startActivityForResult(BTIntent, DeviceBlue.REQUEST_CONNECT_BT)
  } else {
      var opstream: OutputStream? = null
      try {
          opstream = btsocket!!.outputStream
      } catch (e: IOException) {
          e.printStackTrace()
      }


      //print command
      try {
          try {
              Thread.sleep(1000)
          } catch (e: InterruptedException) {
              e.printStackTrace()
          }
          opstream = btsocket!!.outputStream
          val printformat = byteArrayOf(0x1B, 0x21, 0x03)
          opstream.write(printformat)
          printCustom("Fair Group BD", 2, 1)
          printCustom("Pepperoni Foods Ltd.", 0, 1)
          printPhoto(R.drawable.ic_icon_pos)
          printCustom("H-123, R-123, Dhanmondi, Dhaka-1212", 0, 1)
          printCustom("Hot Line: +88000 000000", 0, 1)
          printCustom("Vat Reg : 0000000000,Mushak : 11", 0, 1)
          val dateTime = dateTime
          printText(leftRightAlign(dateTime[0], dateTime[1]))
          printText(leftRightAlign("Qty: Name", "Price "))
          printCustom(String(CharArray(32)).replace("\u0000", "."), 0, 1)
          printText(leftRightAlign("Total", "2,0000/="))
          printNewLine()
          printCustom("Thank you for coming & we look", 0, 1)
          printCustom("forward to serve you again", 0, 1)
          printNewLine()
          printNewLine()
          opstream.flush()
      } catch (e: IOException) {
          e.printStackTrace()
      }
  }
}

protected fun printConn() {
  val BTIntent = Intent(applicationContext, DeviceBlue::class.java)
  this.startActivityForResult(BTIntent, DeviceBlue.REQUEST_CONNECT_BT)
}

protected fun printDemo() {
  if (btsocket == null) {
      val BTIntent = Intent(applicationContext, DeviceBlue::class.java)
      this.startActivityForResult(BTIntent, DeviceBlue.REQUEST_CONNECT_BT)
  } else {
      var opstream: OutputStream? = null
      try {
          opstream = btsocket!!.outputStream
      } catch (e: IOException) {
          e.printStackTrace()
      }


      //print command
      try {
          try {
              Thread.sleep(1000)
          } catch (e: InterruptedException) {
              e.printStackTrace()
          }
          opstream = btsocket!!.outputStream
          val printformat = byteArrayOf(0x1B, (0 * 21).toByte(), FONT_TYPE)
          opstream.write(printformat);

          //print title
          printUnicode()
          //print normal text
          printPhoto(R.drawable.ic_icon_pos)
          printCustom(message!!.text.toString(), 0, 0)
          //   printPhoto(R.drawable.ic_icon_pos);
          printNewLine()
          printText("     >>>>   Thank you  <<<<     ") // total 32 char in a single line
          //resetPrint(); //reset printer
          //    printUnicode();
          printNewLine()
          printNewLine()
          opstream.flush()
      } catch (e: IOException) {
          e.printStackTrace()
      }
  }
}

//print custom
private fun printCustom(msg: String, size: Int, align: Int) {
  //Print config "mode"
  val cc = byteArrayOf(0x1B, 0x21, 0x03) // 0- normal size text
  //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
  val bb = byteArrayOf(0x1B, 0x21, 0x08) // 1- only bold text
  val bb2 = byteArrayOf(0x1B, 0x21, 0x20) // 2- bold with medium text
  val bb3 = byteArrayOf(0x1B, 0x21, 0x10) // 3- bold with large text
  try {
      when (size) {
          0 -> outputStream!!.write(cc)
          1 -> outputStream!!.write(bb)
          2 -> outputStream!!.write(bb2)
          3 -> outputStream!!.write(bb3)
      }
      when (align) {
          0 ->                     //left align
              outputStream!!.write(PrinterCommands.ESC_ALIGN_LEFT)
          1 ->                     //center align
              outputStream!!.write(PrinterCommands.ESC_ALIGN_CENTER)
          2 ->                     //right align
              outputStream!!.write(PrinterCommands.ESC_ALIGN_RIGHT)
      }
      outputStream!!.write(msg.toByteArray())
      outputStream!!.write(PrinterCommands.LF.toInt())
      //outputStream.write(cc);
      //printNewLine();
  } catch (e: IOException) {
      e.printStackTrace()
  }
}

//print photo
fun printPhoto(img: Int) {
  try {
      val bmp = BitmapFactory.decodeResource(
          resources,
          img
      )
      if (bmp != null) {
          val command = Utils.decodeBitmap(bmp)
          outputStream!!.write(PrinterCommands.ESC_ALIGN_CENTER)
          printText(command)
      } else {
          Log.e("Print Photo error", "the file isn't exists")
      }
  } catch (e: Exception) {
      e.printStackTrace()
      Log.e("PrintTools", "the file isn't exists")
  }
}

//print unicode
fun printUnicode() {
  try {
      outputStream!!.write(PrinterCommands.ESC_ALIGN_CENTER)
      printText(Utils.UNICODE_TEXT)
  } catch (e: UnsupportedEncodingException) {
      e.printStackTrace()
  } catch (e: IOException) {
      e.printStackTrace()
  }
}

//print new line
private fun printNewLine() {
  try {
      outputStream!!.write(PrinterCommands.FEED_LINE)
  } catch (e: IOException) {
      e.printStackTrace()
  }
}

//print text
private fun printText(msg: String) {
  try {
      // Print normal text
      outputStream!!.write(msg.toByteArray())
  } catch (e: IOException) {
      e.printStackTrace()
  }
}

//print byte[]
private fun printText(msg: ByteArray) {
  try {
      // Print normal text
      outputStream!!.write(msg)
      printNewLine()
  } catch (e: IOException) {
      e.printStackTrace()
  }
}

private fun leftRightAlign(str1: String?, str2: String?): String {
  var ans = str1 + str2
  if (ans.length < 31) {
      val n = 31 - str1!!.length + str2!!.length
      ans = str1 + String(CharArray(n)).replace("\u0000", " ") + str2
  }
  return ans
}

private val dateTime: Array<String?>
  private get() {
      val c = Calendar.getInstance()
      val dateTime = arrayOfNulls<String>(2)
      dateTime[0] =
          c[Calendar.DAY_OF_MONTH].toString() + "/" + c[Calendar.MONTH] + "/" + c[Calendar.YEAR]
      dateTime[1] = c[Calendar.HOUR_OF_DAY].toString() + ":" + c[Calendar.MINUTE]
      return dateTime
  }

override fun onDestroy() {
  super.onDestroy()
  try {
      if (btsocket != null) {
          outputStream!!.close()
          btsocket!!.close()
          btsocket = null
      }
  } catch (e: IOException) {
      e.printStackTrace()
  }
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
  super.onActivityResult(requestCode, resultCode, data)
  try {
      btsocket = DeviceBlue.getSocket()
      if (btsocket != null) {
          printText(message!!.text.toString())
      }
  } catch (e: Exception) {
      e.printStackTrace()
  }
}

//js
internal inner class IJavascriptHandler(var mContext: Context) {
  //API 17 and higher required you to add @JavascriptInterface as mandatory before your method.
  @SuppressLint("LongLogTag")
  @JavascriptInterface
  fun processContent(aContent: String) {
      //this method will be called from within the javascript method that you will write.
      Log.e("The content of the current page is ", aContent)
  }
} //end

companion object {
  private var btsocket: BluetoothSocket? = null
  private var outputStream: OutputStream? = null
  fun resetPrint() {
      try {
          outputStream!!.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT)
          outputStream!!.write(PrinterCommands.FS_FONT_ALIGN)
          outputStream!!.write(PrinterCommands.ESC_ALIGN_LEFT)
          outputStream!!.write(PrinterCommands.ESC_CANCEL_BOLD)
          outputStream!!.write(PrinterCommands.LF.toInt())
      } catch (e: IOException) {
          e.printStackTrace()
      }
  }
}
}