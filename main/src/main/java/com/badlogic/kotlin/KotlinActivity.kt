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

    /*
    伴生对象可以理解为：java中的单例模式，companion 是一个单例。
    一个class中只能有一个伴生对象
     */
    companion object {
        /*
        todo  在 JVM 平台，如果使用 @JvmStatic 注解，你可以将伴生对象的成员生成为真正的静态方法和字段
         */
        @JvmStatic
        fun startMe(hContext: Context) {
            val intent = Intent(hContext, KotlinActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            hContext.startActivity(intent)
        }

        /*
        todo
         @JvmField注解，例如告诉编译器不要生成getter和setter,而是生成Java中成员。
         在伴生对象的作用域内使用该注解标记某个成员，它产生的副作用是标记这个成员不在伴生对象内部作用域
         ，而是作为一个Java最外层类的静态成员存在
         */
        @JvmField
        public val test_aa = 2

        @JvmStatic
        public val NORMAL_VIEW = 2

        @JvmField
        public val test_bb = 2

        val test_a = 2
    }

    fun max(a : Int, b : Int) : Int {
        return  a + b
    }

    /*
     这种简单的可以转为返回表达式,甚至可以省略返回类型
     */
    fun max2(a : Int, b : Int) : Int = a + b

    fun max3(a : Int, b : Int)  = a + b

    /*
    kotlin中没有数组对象
     */
    private fun test_a001(args : Array<String>){
        ALog.i {
            "KotlinJavaTestHelper-test_a001" +
                    "-KotlinActivity.test_aa-> " + test_aa +
                    "-args[0]-> " + args[0] +
                    ""
        }
        ALog.i { "test-KotlinActivity.test_aa-> $test_aa -args[0]-> ${args[0]} " }
        /*
        val 可以简单推断出来的，可以在初始化时，不赋值。
         */
        val message : String
        message = "111"

        val message2 : String
        if (test_aa == 2) {
            message2 = "22"
        } else {
            message2 = "33"
        }
    }

    /*
    生命属性的同时，自带getter和setter方法，即使不重写方法实现的内容，默认也有这两个方法，<属性访问器>,
    todo 所有对此变量的访问都要经此方法。实际上是调用时进行了转换
    当然也可以重写真正意义上的setter和getter方法，此时相应的<属性访问器>就会报错，他们之间只能存在一个
    在加上《private》限制后，才可以重写真正意义上的setter和getter方法，
    并且，selfNameNumber = 0，不会调用setter方法，
     */
    public var selfNameNumber: Int = 0
        get() {
            if ( field < 0 ) {
                field = 0
            }
            return field
        }
        /*private*/ set(value) {
            ALog.i { "KotlinActivity-selfNameNumber-set-01->" }
            if (value < 0) {
                return
            }
            field = value
        }
    /*fun getSelfNameNumber(): Int {
        return selfNameNumber
    }
    fun setSelfNameNumber(value : Int) {
        ALog.i { "KotlinActivity-selfNameNumber-set-02->" }
        selfNameNumber = value
    }*/

    fun test_aa1(selfNameNumber: Int) {
        this.selfNameNumber = selfNameNumber;


        /*
        在函数体内使用it替代object对象去访问其公有的属性和方法
        另一种用途 判断object为null的操作
        //表示object不为null的条件下，才会去执行let函数体
         */
        mAdapter?.loadStateA_f?.let{
            it.alpha = 0
        }

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
        selfNameNumber = 99
        //--------




    }



    /*
    如果你使用 if 作为表达式而不是语句（例如：返回它的值或者把它赋给变量），该表达式需要有 else 分支。
    when的参数可以是任意类型
     */
    private fun test_when( a : Int, b : Int){
        val max = if (a > b) {
            print("Choose a")
            a
        } else {
            print("Choose b")
            b
        }
        when (a) {
            1 -> print("x == 1")
            2 -> print("x == 2")
            else -> { // 注意这个块
                print("x is neither 1 nor 2")
            }
        }
    }

    private fun test_when_1( a : Int, b : Int) : Int{
        return when (a) {
            1 -> 11
            2 -> 222
            else -> { // 注意这个块
                33
            }
        }
    }

    private fun test_when_2( a : Int, b : Int) = when (a) {
        1 -> 11
        2 -> 222
        else -> { // 注意这个块
            33
        }
    }

    private fun test_when_3( a : Int, b : Int) :Int = when (a) {
        1 -> 11
        2 -> 222
        else -> { // 注意这个块
            33
        }
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


    /*
    默认内部类为静态内部类
    inner关键字来修饰内部类的声明，才能让Kotlin中默认的静态内部类变为非静态的成员内部类
     */
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