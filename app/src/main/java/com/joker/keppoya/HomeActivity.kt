package com.joker.keppoya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joker.keppoya.adapter.KeppoyaAdapter
import com.joker.keppoya.adapter.ViewPagerAdapter
import com.joker.keppoya.database.SQLiteHelper
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.card_items_keppoya.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeActivity : AppCompatActivity() {

    var imageArray:ArrayList<Int> = ArrayList()

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : KeppoyaAdapter? = null
    private var keppoya : KeppoyaModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        imageArray.add(R.drawable.picture1)
        imageArray.add(R.drawable.picture2)

//        carouselView!!.pageCount = imageArray.size
//
//        carouselView!!.setImageListener(imageListener)
//        view pager

        val fragment : ArrayList<Fragment> = arrayListOf(
            Page1Fragment(),
            Page2Fragment()
        )
        val adapter_viewPager = ViewPagerAdapter(fragment, this)
        view_pager.adapter = adapter_viewPager


//        inisialisasi recycler view
        recyclerView = findViewById(R.id.rc_keppoya)
        initRecylerView()

//        SQLITE
        sqLiteHelper = SQLiteHelper(this)
        getKeppoya()


        adapter?.setOnClickItem {
            Toast.makeText(this,it.title, Toast.LENGTH_SHORT).show()
//            INTENT
            val keppoyaParcel = Keppoya(it.id.toString(), it.title, it.text, it.star)
            keppoya = it

//          inntent keppoy


            val moveToWriteActivity = Intent(this@HomeActivity, WriteActivity::class.java )
            moveToWriteActivity.putExtra(WriteActivity.EXTRA_KEPPOYA,keppoyaParcel)
            startActivity(moveToWriteActivity)

        }

        adapter?.setOnClickDeleteItem {
            deleteKeppoya(it.id)
        }


        fab_btn.setOnClickListener {
            val moveToWriteActivityWithoutData = Intent(this@HomeActivity, WriteActivity::class.java)
            startActivity(moveToWriteActivityWithoutData)
        }

    }

    fun getKeppoya(){
        val keppoyaList = sqLiteHelper.getAllKeppoya()
//        log.e("pppppp","${keppoyaList.size}")

        adapter?.addItems(keppoyaList)
    }
    fun deleteKeppoya(id : Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure want to delete this item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog,_->
            sqLiteHelper.deleteKeppoyaById(id)
            getKeppoya()
            dialog.dismiss()

        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    fun initRecylerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = KeppoyaAdapter()
        recyclerView.adapter = adapter
    }

    var imageListener = ImageListener { position, imageView -> imageView.setImageResource(imageArray[position])
//        Toast.makeText(this,"nama file"+ position, Toast.LENGTH_LONG).show()
    }
}