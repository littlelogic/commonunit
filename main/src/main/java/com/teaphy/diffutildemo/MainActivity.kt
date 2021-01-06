package com.teaphy.diffutildemo
//com.teaphy.diffutildemo.MainActivity
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.badlogic.ui.R
import kotlinx.android.synthetic.main.differ_activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.differ_activity_main)

        initData()

        initView()

        setListener()

    }

    private fun initData() {

    }

    private fun initView() {


    }

    private fun setListener() {
        btnDiff.setOnClickListener {
            startActivity<DiffActivity>()
        }

        btnAld.setOnClickListener {
            startActivity<AsyncListDifferActivity>()
        }

        btnLa.setOnClickListener {
            startActivity<ListAdapterActivity>()
        }

        btnSl.setOnClickListener {
            startActivity<SortedListActivity>()
        }
    }



}
