package com.lzhz.lxh.sleepmonitor.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.home.activity.bean.AlarmBean;
import com.lzhz.lxh.sleepmonitor.home.adapter.ShowAlarmAdapter;
import com.lzhz.lxh.sleepmonitor.home.bean.AddAlarmBean;
import com.lzhz.lxh.sleepmonitor.tools.DividerItemDecoration;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import com.lzhz.lxh.sleepmonitor.tools.ToastUtil;
import com.lzhz.lxh.sleepmonitor.tools.view.RecycleViewDivider;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：lxh on 2018-01-05:18:46
 * 邮箱：15911638454@163.com
 * 闹钟页面
 */

public class AlarmActivity extends BaseActivity{

    @BindView(R.id.smrv_alarm_list)
    SwipeMenuRecyclerView smrvAlarmList;
    private ShowAlarmAdapter adapter;
    private List<AlarmBean> list;
    @Override
    public void setRootView() {
        setContent(R.layout.alarm_activity);
    }
    @Override
    public void initData() {

        LogUtils.i(list.toString());
    }
    @Override
    public void initViews() {
        smrvAlarmList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        smrvAlarmList.setHasFixedSize(false);
        // 设置监听器。
        smrvAlarmList.setSwipeMenuCreator(mSwipeMenuCreator);

        smrvAlarmList.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 30, getResources().getColor(R.color.bg_color)));

        smrvAlarmList.setOnItemStateChangedListener(new OnItemStateChangedListener() {
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                LogUtils.i("----"+actionState );
                smrvAlarmList.invalidate();
            }
        });
        //删除数据
        smrvAlarmList.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                    DataSupport.deleteAll(AlarmBean.class,"alarmId = ?",""+list.get(menuBridge.getAdapterPosition()).getAlarmId());
                    //DataSupport.deleteAll(AlarmBean.class,"hour > ?",""+0);
                    list.remove(menuBridge.getAdapterPosition());

                init();
            }
        });
        smrvAlarmList.setItemViewSwipeEnabled(false);

        init();
    }
    private void init(){
        list = new ArrayList<AlarmBean>();
        list = DataSupport.findAll(AlarmBean.class);
        adapter = new ShowAlarmAdapter(AlarmActivity.this, R.layout.mb_alarm_list, list);
        smrvAlarmList.setAdapter(adapter);
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            //  int height = ViewGroup.LayoutParams.MATCH_PARENT;
            {
            SwipeMenuItem deleteItem = new SwipeMenuItem(AlarmActivity.this)
                    .setBackground(R.drawable.selector_red)
                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(RecyclerView.LayoutParams.MATCH_PARENT);
            rightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText("闹钟");
        tvRight.setText("编辑");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AlarmActivity.this, Add1AlarmActivity.class);
                startActivity(intent);
            }
        });
    }

}
