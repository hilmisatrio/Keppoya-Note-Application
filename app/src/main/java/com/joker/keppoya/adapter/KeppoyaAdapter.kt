package com.joker.keppoya.adapter

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joker.keppoya.KeppoyaModel
import com.joker.keppoya.R

class KeppoyaAdapter : RecyclerView.Adapter<KeppoyaAdapter.KeppoyaViewHolder>() {

    private var keppoyaList : ArrayList<KeppoyaModel> = ArrayList()
    private var onClickItem : ((KeppoyaModel)->Unit)? = null
    private var onClickDeleteItem: ((KeppoyaModel)->Unit)?=null

    fun addItems(items : ArrayList<KeppoyaModel>){
        this.keppoyaList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (KeppoyaModel)->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (KeppoyaModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = KeppoyaViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_keppoya ,parent,false)
            )

    override fun onBindViewHolder(holder: KeppoyaViewHolder, position: Int) {
        val keppoya = keppoyaList[position]
        holder.bindView(keppoya)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(keppoya)
        }
        holder.btn_remove.setOnClickListener {
            onClickDeleteItem?.invoke(keppoya)
        }
    }

    override fun getItemCount(): Int {
        return keppoyaList.size
    }

//    class keppoya view holder
    class KeppoyaViewHolder(var view : View):RecyclerView.ViewHolder(view){
//        private var id = view.findViewById<TextView>(R.id.id_keppoya)
        private var title = view.findViewById<TextView>(R.id.title_keppoya)
        private var text = view.findViewById<TextView>(R.id.text_keppoya)
        private var star = view.findViewById<RatingBar>(R.id.star_keppoya)
        var btn_remove = view.findViewById<TextView>(R.id.btn_remove)
        fun bindView(keppoya:KeppoyaModel){
//            id.text  = keppoya.id.toString()
            title.text = keppoya.title
            text.text = keppoya.text
            if(keppoya.star == "TRUE"){
                star.numStars = 1
            }else {
                star.setVisibility(View.GONE)
            }
        }
    }
}