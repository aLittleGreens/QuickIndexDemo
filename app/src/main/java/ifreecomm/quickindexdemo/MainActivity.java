package ifreecomm.quickindexdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifreecomm.quickindexdemo.bean.Friend;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private QuickIndexBar quickIndexBar;
    private TextView indexText;
    private List<Friend> friends = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1: 准备数据
        fillList();
        //2: 排序
        Collections.sort(friends);
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        QuickAdapter quickAdapter = new QuickAdapter(this, friends);
        listView.setAdapter(quickAdapter);
        quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);
        indexText = (TextView) findViewById(R.id.indexText);
        indexText.setScaleX(0);
        indexText.setScaleY(0);
        quickIndexBar.setOnQuickIndexTouchListener(new QuickIndexBar.OnQuickIndexTouchListener() {
            @Override
            public void onTouchIndex(String letter) {

                if (indexText.getScaleX() == 0) {
                    indexText.animate().scaleY(1.0f).scaleX(1.0f).setDuration(200).start();
                }
                indexText.setText(letter);

                for (int i = 0; i < friends.size(); i++) {
                    if (friends.get(i).getPinyin().equals(letter)) {
                        listView.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void quickCancal() {
                if (indexText.getScaleX() != 0) {
                    indexText.animate().scaleY(0).scaleX(0).setDuration(300).setInterpolator(new OvershootInterpolator()).start();
                }
            }
        });
    }

    private void fillList() {
        // 虚拟数据
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("包正"));
        friends.add(new Friend("蔡三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("和其正"));
        friends.add(new Friend("老陈"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
    }
}
