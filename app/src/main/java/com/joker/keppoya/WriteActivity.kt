package com.joker.keppoya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.joker.keppoya.adapter.KeppoyaAdapter
import com.joker.keppoya.database.SQLiteHelper
import kotlinx.android.synthetic.main.activity_write.*

class WriteActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_KEPPOYA = "extra_keppoya"
    }
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : KeppoyaAdapter? = null
    private var keppoya : KeppoyaModel? = null
    private lateinit var id_data : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        sqLiteHelper = SQLiteHelper(this)
        try{
            val keppoya_intent = intent.getParcelableExtra<Keppoya>(EXTRA_KEPPOYA) as Keppoya
            if(keppoya_intent.title != ""){
                fill_title.setText(keppoya_intent.title)
                fill_text.setText(keppoya_intent.text)
                cb_star.isChecked = keppoya_intent.star.toBoolean()
                id_data = keppoya_intent.id.toString()
                btn_save.setVisibility(View.GONE)
            }
        }catch (e:Exception){
                btn_update.setVisibility(View.GONE)
        }

//        val text = "Name : ${person.name.toString()},\nEmail : ${person.email},\nAge : ${person.age},\nLocation : ${person.city}"
        btn_save.setOnClickListener{
            addKeppoya()
        }
        btn_update.setOnClickListener{
            updateKeppoya()

        }

    }

    fun addKeppoya(){
        val title = fill_title.text.toString()
        val text = fill_text.text.toString()
        val star : String?
        if(cb_star.isChecked){
            star = "TRUE"
        }else{
            star ="FALSE"
        }

        if(title.isEmpty() || text.isEmpty()){
            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()
        }else{
            val keppoya = KeppoyaModel(title = title , text = text, star = star)
            val status = this.sqLiteHelper.insertKeppoya(keppoya)

            if(status > -1){
                Handler().postDelayed({
                    val intentBackToHome = Intent(this@WriteActivity, HomeActivity::class.java)
                    startActivity(intentBackToHome)
                    finish()
                },500)
                Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateKeppoya(){
//        Toast.makeText(this, "AXAXAXA"+keppoya?.title, Toast.LENGTH_SHORT).show()
        val title = fill_title.text.toString()
        val text = fill_text.text.toString()

        //check record not changes
//        if(title == keppoya?.title && text == keppoya?.text){
//            Toast.makeText(this, "Record not changed!", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(keppoya == null) return

        if(fill_text.text.toString() != "" && fill_title.text.toString() != ""){
            var star_update : String?
            if(cb_star.isChecked){
                star_update = "TRUE"
            }else{
                star_update ="FALSE"
            }
            val keppoya = KeppoyaModel(id = id_data.toInt() , title = title, text = text, star = star_update)
            val status = sqLiteHelper.updateKeppoya(keppoya)

            if(status > -1){
                Handler().postDelayed({
                    val intentBackToHome = Intent(this@WriteActivity, HomeActivity::class.java)
                    startActivity(intentBackToHome)
                    finish()
                },500)
                Toast.makeText(this, "Data Update", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()

            }
        }else{
            Toast.makeText(this, "Please fill the field", Toast.LENGTH_SHORT).show()
        }



    }
}