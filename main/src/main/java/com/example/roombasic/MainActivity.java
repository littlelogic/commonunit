package com.example.roombasic;
//com.example.roombasic.MainActivity
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.badlogic.ui.R;

import java.util.List;

/*todo
    Room三要素：
    Dao:用来处理数据库操作，如增删改查，编译的时候会生成_impl结尾的实现类，实现在DAO中定义的增删改查方法
    Entity:实体类，一个实体类对应一张表
    Database:作为底层连接数据库的主要接入点，它是一个抽象的类，并继承RoomDatabase，编译的时候会自动生成一个_impl结尾的实现类，实现数据库以及表的创建及打开

 */
public class MainActivity extends AppCompatActivity {

    Button buttonInsert,buttonClear;
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    Switch aSwitch;
    MyAdapter myAdapter1,myAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        //wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        myAdapter1 = new MyAdapter(false,wordViewModel);
        myAdapter2 = new MyAdapter(true,wordViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter1);
        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerView.setAdapter(myAdapter2);
                } else {
                    recyclerView.setAdapter(myAdapter1);
                }
            }
        });

        androidx.recyclerview.widget.RecyclerView kkkl;
        androidx.constraintlayout.widget.ConstraintLayout lll;

        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapter1.getItemCount();
                myAdapter1.setAllWords(words);
                myAdapter2.setAllWords(words);
                if (temp!=words.size()) {
                    myAdapter1.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
                }

            }
        });

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonClear = findViewById(R.id.buttonClear);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] english = {
                        "Hello",
                        "World",
                        "Android",
                        "Google",
                        "Studio",
                        "Project",
                        "Database",
                        "Recycler",
                        "View",
                        "String",
                        "Value",
                        "Integer"
                };
                String[] chinese = {
                        "你好",
                        "世界",
                        "安卓系统",
                        "谷歌公司",
                        "工作室",
                        "项目",
                        "数据库",
                        "回收站",
                        "视图",
                        "字符串",
                        "价值",
                        "整数类型"
                };
                for(int i = 0;i<english.length;i++) {
                    wordViewModel.insertWords(new Word(english[i],chinese[i]));
                }
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWords();
            }
        });


    }


}
