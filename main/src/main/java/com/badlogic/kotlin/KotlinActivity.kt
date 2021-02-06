package com.badlogic.kotlin
// com.badlogic.kotlin.KotlinActivity
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.badlogic.ui.R
import com.badlogic.utils.ALog
import com.badlogic.utils.Tools
import kotlinx.android.synthetic.main.activ_kotlin.*

class KotlinActivity : Activity() {

    companion object {
        @JvmStatic
        fun startMe(hContext: Context) {
            val intent = Intent(hContext, KotlinActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            hContext.startActivity(intent)
        }

        @JvmStatic
        val NORMAL_VIEW = 2
    }

    private val mHandler = Handler()
    private var mAdapter : MAdapter? = null
    private var mLinearLayoutManager : LinearLayoutManager? = null
    ///----
    private var markTvDr : Drawable? = null
    private var dp_5 : Int = Tools.dip2px(Tools.getApplication(), 5f)
    private var dp_8 : Int = Tools.dip2px(Tools.getApplication(), 8f)


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activ_kotlin)
        setStatusBarTransparent()
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
        } else {
            window.statusBarColor = 0xFF_FF_CB_00.toInt()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView() {
        markTvDr = Tools.getCornerDrawable(0xff00C853.toInt(), dp_8)

        val colors = intArrayOf(0xffFFCB00.toInt(), 0xffFF5500.toInt())
        val mGradientDrawable = GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)
        mGradientDrawable.shape = GradientDrawable.RECTANGLE //
        topStatusbg.background = mGradientDrawable
        
        val statusBarHeight = Tools.getStatusBarHeight1(this)
        topStatusbg.layoutParams.height = statusBarHeight + Tools.dip2px(this, 56F);

        mAdapter = MAdapter(this)
        mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager?.orientation = LinearLayoutManager.VERTICAL
        rvBar.adapter = mAdapter
        rvBar.layoutManager = mLinearLayoutManager
        rvBarRefresh.setOnRefreshListener {
            rvBarRefresh.isRefreshing = true;
            update()
        }
        topBack.setOnClickListener { finish() }
    }

    private fun update() {
        mHandler.postDelayed(object : Runnable {
            override fun run() {
                this@KotlinActivity.mAdapter?.notifyDataSetChanged()

            }
        },2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)

    }

    val clickLoadListener = View.OnClickListener {
        ALog.i("201215r-VpnSettingActivity-clickLoadListener-01->")
        if (it.getTag() is MViewHolder) {
            var hMViewHolder : MViewHolder = it.getTag() as MViewHolder

            mAdapter?.notifyDataSetChanged()
        }
    }

    inner class MViewHolder : RecyclerView.ViewHolder {

        var position : Int? = -1
        //---
        var rootView : View? = null
        var frontPoint : View? = null
        var loadStateImage : ImageView? = null
        var countryImage : ImageView? = null
        var countryTv : TextView? = null


        constructor(itemView: View, viewType_: Int) : super(itemView) {
            rootView = itemView;
            frontPoint = itemView.findViewById(R.id.frontPoint)
            loadStateImage = itemView.findViewById(R.id.loadStateImage)
            countryImage = itemView.findViewById(R.id.countryImage)
            countryTv = itemView.findViewById(R.id.countryTv)
            rootView?.setOnClickListener(clickLoadListener)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    inner class MAdapter(private val context: Context) : RecyclerView.Adapter<MViewHolder>() {

        private val mInflater: LayoutInflater
        var loadStateA_f : Drawable? = null
        var loadStateB_f : Drawable? = null
        var loadStateC_f : Drawable? = null

        init {
            mInflater = LayoutInflater.from(context)
            loadStateA_f = Tools.getCornerDrawable(0xff00E676.toInt(), dp_5)
            loadStateB_f = Tools.getCornerDrawable(0xffF57C00.toInt(), dp_5)
            loadStateC_f = Tools.getCornerDrawable(0xffD32F2F.toInt(), dp_5)
        }


        override fun getItemCount(): Int {
            return 30
        }

        override fun getItemViewType(position: Int): Int {
            return NORMAL_VIEW
        }

        override fun onBindViewHolder(viewHolder: MViewHolder, position: Int) {
            viewHolder.position = position


        }

        override fun onBindViewHolder(holder: MViewHolder, position: Int, payloads: List<Any>) {
            super.onBindViewHolder(holder, position, payloads)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MViewHolder {
            var view: View = mInflater.inflate(R.layout.activ_kotlin_item, viewGroup, false)
            return MViewHolder(view, viewType)
        }
    }



}