package com.myfawwaz.mbs.management.activities;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import com.myfawwaz.mbs.management.R;

        import java.io.IOException;
        import java.io.OutputStream;
        import java.io.UnsupportedEncodingException;
        import java.util.Calendar;

        import android.bluetooth.BluetoothSocket;
        import android.content.Intent;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.text.Html;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.content.*;
        import android.os.*;
        import android.webkit.*;
        import com.myfawwaz.mbs.management.networking.Api;
        import java.io.*;
public class PrintActivity extends Activity {
    private String TAG = "Main Activity";
    private Activity mActivity;
    //   private CoordinatorLayout mCLayout;
    private WebView webView;
    EditText message, numberprint;
    ImageView btnPrint, btnLoad, btnConn, btnBefore, btnNext;
    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;
    private String content;
    private Context mContext;
    private TextView messagetv;
    private String id;
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printlayout);
        Intent intent = getIntent();

        id = intent.getStringExtra("detailOrder");
        // Get the application context
        if (id == null) id = "";
        mContext = getApplicationContext();
        mActivity = PrintActivity.this;
        messagetv = (TextView) findViewById(R.id.tvmsg);
        message = (EditText) findViewById(R.id.txtMessage);
        numberprint = (EditText) findViewById(R.id.editNumberOrder);
        btnPrint = (ImageView) findViewById(R.id.btnPrint);
        btnLoad = (ImageView) findViewById(R.id.btnLoad);
        btnConn = (ImageView) findViewById(R.id.btnConnPrint);
        btnBefore = (ImageView) findViewById(R.id.btnPrintbefore);
        btnNext = (ImageView) findViewById(R.id.btnPrintnext);
        //web
        webView = (WebView) findViewById(R.id.web_view);

        printLoad(id);
        messagetv.setText("please wait...load order no. " + id);
        //ebd web
        btnPrint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                printDemo();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(url);
            }
        });
        btnConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printConn();
            }
        });

        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idnext = Integer.parseInt(id) - 1;
                //webView.loadUrl(Api.URL_PRINT + idnext);
               // numberprint.setText(idnext);
                printLoad(""+idnext);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idnext = Integer.parseInt(id) + 1;
                //webView.loadUrl(Api.URL_PRINT + idnext);
                //numberprint.setText(idnext);
                printLoad(""+idnext);
            }
        });

        if (btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), DeviceBlue.class);
            // this.startActivityForResult(BTIntent, DeviceBlue.REQUEST_CONNECT_BT);
        }

    }

//=========
protected void printLoad(String idx) {
        id = idx;
        webView.setWebViewClient(new

    WebViewClient());
        webView.getSettings().

    setJavaScriptEnabled(true);
        webView.getSettings().

    setDomStorageEnabled(true);
        webView.getSettings().

    setAppCacheEnabled(true);
        webView.getSettings().

    setBuiltInZoomControls(true);
        webView.getSettings().

    setSaveFormData(true);
        webView.getSettings().

    setSupportZoom(false);
        webView.requestFocus();
    //fit view web
        webView.getSettings().

    setAppCacheEnabled(true);
        webView.getSettings().

    setLoadWithOverviewMode(true);
        webView.getSettings().

    setUseWideViewPort(true);

    final Context myctxt = this; //your activity or application context

    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            content = html;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                message.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
                messagetv.setText("Text Ready to edit or print");
                String txr = message.getText().toString();
                txr.replaceAll("\n", "");
                message.setText(txr);
            } else {

                message.setText(Html.fromHtml(content));
                String txr = message.getText().toString();
                txr.replaceAll("\n", "");
                message.setText(txr);
                messagetv.setText("Text Ready to edit or print");
            }
        }
    }
    /* Register a new JavaScript interface called HTMLOUT */
        webView.addJavascriptInterface(new

    MyJavaScriptInterface(), "HTMLOUT");

    /* WebViewClient must be set BEFORE calling loadUrl! */
        webView.setWebViewClient(new

    WebViewClient() {
        @Override
        public void onPageFinished (WebView view, String url)
        {
            /* This call inject JavaScript into the page which just finished loading. */
            webView.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            File f = getFileFromUrl(url);
            messagetv.setText("Text Ready to edit or print order no. " + id);
            numberprint.setText(id);
            message.requestFocus();
        }

        private File getFileFromUrl (String url)
        {
            // TODO: Implement this method
            return null;
        }
    });
    url = Api.URL_PRINT + idx;
    messagetv.setText("Text on load");
    messagetv.setText("please wait...");
    webView.loadUrl(url);
}
    //======

    protected void printBill() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), DeviceBlue.class);
            this.startActivityForResult(BTIntent, DeviceBlue.REQUEST_CONNECT_BT);
        }
        else{
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();
                byte[] printformat = new byte[]{0x1B,0x21,0x03};
                outputStream.write(printformat);
                printCustom("Fair Group BD",2,1);
                printCustom("Pepperoni Foods Ltd.",0,1);
                printPhoto(R.drawable.ic_icon_pos);
                printCustom("H-123, R-123, Dhanmondi, Dhaka-1212",0,1);
                printCustom("Hot Line: +88000 000000",0,1);
                printCustom("Vat Reg : 0000000000,Mushak : 11",0,1);
                String dateTime[] = getDateTime();
                printText(leftRightAlign(dateTime[0], dateTime[1]));
                printText(leftRightAlign("Qty: Name" , "Price "));
                printCustom(new String(new char[32]).replace("\0", "."),0,1);
                printText(leftRightAlign("Total" , "2,0000/="));
                printNewLine();
                printCustom("Thank you for coming & we look",0,1);
                printCustom("forward to serve you again",0,1);
                printNewLine();
                printNewLine();
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    protected void printConn() {

            Intent BTIntent = new Intent(getApplicationContext(), DeviceBlue.class);
            this.startActivityForResult(BTIntent, DeviceBlue.REQUEST_CONNECT_BT);
        }
    protected void printDemo() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), DeviceBlue.class);
            this.startActivityForResult(BTIntent, DeviceBlue.REQUEST_CONNECT_BT);
        }
        else{
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();

                byte[] printformat = { 0x1B, 0*21, FONT_TYPE };
                //outputStream.write(printformat);

                //print title
                printUnicode();
                //print normal text
                printPhoto(R.drawable.ic_icon_pos);
                printCustom(message.getText().toString(),0,0);
                //   printPhoto(R.drawable.ic_icon_pos);
                printNewLine();
                printText("     >>>>   Thank you  <<<<     "); // total 32 char in a single line
                //resetPrint(); //reset printer
                //    printUnicode();
                printNewLine();
                printNewLine();

                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
        try {
            switch (size){
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align){
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print photo
    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            if(bmp!=null){
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode(){
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try{
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 +str2;
        if(ans.length() <31){
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }


    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime [] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) +"/"+ c.get(Calendar.MONTH) +"/"+ c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) +":"+ c.get(Calendar.MINUTE);
        return dateTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(btsocket!= null){
                outputStream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = DeviceBlue.getSocket();
            if(btsocket != null){
                printText(message.getText().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //js
    final class IJavascriptHandler {

        Context mContext;
        IJavascriptHandler(Context c) {
            mContext = c;
        }

        //API 17 and higher required you to add @JavascriptInterface as mandatory before your method.
        @SuppressLint("LongLogTag")
        @JavascriptInterface
        public void processContent(String aContent)
        {
            //this method will be called from within the javascript method that you will write.
            final String content = aContent;
            Log.e("The content of the current page is ",content);
        }
    };
    //end
}

