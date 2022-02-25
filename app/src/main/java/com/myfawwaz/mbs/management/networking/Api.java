package com.myfawwaz.mbs.management.networking;

/**
 * \
 *
 * */

public class Api {

    //API error
    public static String URL_LIST_SURAH =  "https://www.bakulansayur.com/apicart/showorder.php?app=mobile";//"https://api.npoint.io/99c279bb173a6e28359c/data";
    public static String URL_LIST_ORDERAN = "https://www.bakulansayur.com/apicart/showorder.php?app=";

    public static String URL_LIST_AYAT = "https://api.npoint.io/99c279bb173a6e28359c/surat/{nomor}";

    //public static String URL_LIST_SURAH = "https://al-quran-8d642.firebaseio.com/data.json?print=pretty";
    //public static String URL_LIST_AYAT = "https://al-quran-8d642.firebaseio.com/surat/{nomor}.json?print=pretty";
    public static final String URL_SERVER = "https://www.mbswonosobo.com/app/crud/";
    public static final String URL_ADD= Api.URL_SERVER+"tambah.php";
    public static final String URL_GET_ALL =   "https://www.bakulansayur.com/apicart/showorder.php?app=all";// konfigurasi.URL_SERVER+ "tampilsemuasiswa.php";
    public static final String URL_PRINT =   "https://www.bakulansayur.com/apicart/print.php?app=print&app=print&id=";

    //public static final String URL_GET_EMP = konfigurasi.URL_SERVER+"tampilsiswa.php?id=";
    public static final String URL_UPDATE_TRX = Api.URL_SERVER+"update.php";
    public static final String URL_DELETE_TRX = Api.URL_SERVER+"hapus.php?id=";
    //key
    public static final String URL_GET_TRX = "https://www.mbswonosobo.com/app/crud/tampilsiswa.php?id=";
    public static final String KEY_ORDER_ID = "order_id";
    public static final String KEY_TRX_NAMA = "firstname";
    public static final String KEY_TRX_ID = "order_id";
    public static final String TRX_ID = "TRX_id";
    //tag
    public static final String TAG_JSON_ARRAY_ORDER= "orders";
    public static final String TAG_JSON_ARRAY_TRX= "orders";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "name";

    public static final String TAG_HP = "hp";
    public static final String TAG_ALMT= "almt";
    public static final String TAG_TOTAL = "ttl";
    public static final String TAG_BELANJAAN = "blnj";
    public static final String TAG_TGL = "tgl";
}
