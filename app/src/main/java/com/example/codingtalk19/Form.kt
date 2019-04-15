package com.example.codingtalk19

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.codingtalk19.Database.UserRepository
import com.example.codingtalk19.Local.UserDataSource
import com.example.codingtalk19.Local.UserDatabase
import com.example.codingtalk19.Model.User
import com.example.codingtalk19.R
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_form.*

class Form : AppCompatActivity() {

    private var userRepository: UserRepository? = null
    private var compositeDisposable: CompositeDisposable? = null
    private lateinit var ambil : String
    private lateinit var ambilnama : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        compositeDisposable = CompositeDisposable()

        val userDatabase = UserDatabase.getInstance(this)
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()))

        val tangkap = intent
        ambil = tangkap.getStringExtra("kirim_id")
        ambilnama = tangkap.getStringExtra("nama")

        showDataExist()

        btnEdit.setOnClickListener {
            updateUser(etNama.text.toString(),ambil.toInt())
            finish()
        }

        btnHapus.setOnClickListener {
            deleteUser(ambil.toInt())
            finish()
        }

    }

    private fun showDataExist() {
        tvId.text = ambil
        etNama.setText(ambilnama)
    }

    private fun deleteUser(id:Int){
        val disposable = io.reactivex.Observable.create(ObservableOnSubscribe<Any> {
                e -> userRepository!!.deleteUser(id)
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                //Success
            },
                {
                        throwable -> Toast.makeText(this,""+throwable.message, Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(this, "Data Behasil didelete", Toast.LENGTH_SHORT).show()
                })
        compositeDisposable!!.addAll(disposable)
    }


    private fun updateUser(nama: String, id: Int){
        val disposable = io.reactivex.Observable.create(ObservableOnSubscribe<Any> {
                e -> userRepository!!.updateUser(nama,id)
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                //Success
            },
                {
                        throwable -> Toast.makeText(this,""+throwable.message, Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(this, "Data Behasil diupdate", Toast.LENGTH_SHORT).show()
                })
        compositeDisposable!!.addAll(disposable)
    }

    override fun onDestroy() {
        compositeDisposable?.clear()
        super.onDestroy()
    }
}
