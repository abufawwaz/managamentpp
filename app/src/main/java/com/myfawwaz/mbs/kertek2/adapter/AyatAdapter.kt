package com.myfawwaz.mbs.kertek.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myfawwaz.mbs.kertek.R
import com.myfawwaz.mbs.kertek.model.ModelAyat



class AyatAdapter(private val mContext: Context,
                  private val items: List<ModelAyat>) : RecyclerView.Adapter<AyatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_ayat, parent, false)
        return ViewHolder(v)
    }
    var terjemah = "true"
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.tvNomorAyat.text = data.nomor
        val satuar = "١"
        val satu = "1"
        val nomor1 = data.nomor?.replace(satu, satuar)
        val duaar = "٢"
        val dua = "2"
        val nomor2 = nomor1?.replace(dua, duaar)
        val tigaar = "٣"
        val tiga = "3"
        val nomor3 = nomor2?.replace(tiga, tigaar)
        val empatar = "٤"
        val empat = "4"
        val nomor4 = nomor3?.replace(empat, empatar)
        val limaar = "٥"
        val lima = "5"
        val nomor5 = nomor4?.replace(lima, limaar)
        val enamar = "٦"
        val enam = "6"
        val nomor6 = nomor5?.replace(enam, enamar)
        val tujuhar = "٧"
        val tujuh = "7"
        val nomor7 = nomor6?.replace(tujuh, tujuhar)
        val delapanar = "٨"
        val delapan = "8"
        val nomor8 = nomor7?.replace(delapan, delapanar)
        val sembilanr = "٩"
        val sembilan = "9"
        val nomor9 = nomor8?.replace(sembilan, sembilanr)
        val nolar = "٠"
        val nol = "0"
        val nomor0 = nomor9?.replace(nol, nolar)


        holder.tvArabic.text = data.arab + "﴿"+nomor0+"﴾"
      holder.tvTerjemahan.text = data.indo
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //Class Holder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNomorAyat: TextView
        var tvArabic: TextView
        var tvTerjemahan: TextView

        init {
            tvNomorAyat = itemView.findViewById(R.id.tvNomorAyat)
            tvArabic = itemView.findViewById(R.id.tvArabic)
            tvTerjemahan = itemView.findViewById(R.id.tvTerjemahan)
        }
    }
}