package com.com.mr_wrong.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mr_wrong.androidstudioproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Wrong on 15/7/25.
 */
public class MyRecycleViewActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private Context mContext;
    private List<String> mydatas;
    private List<Integer> mHeights;
    private LayoutInflater mLayoutinflater;
    List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recyclerview);

        mRecycleView = (RecyclerView) findViewById(R.id.id_recyclerview);
        initData();

        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycleView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecycleView.setAdapter(new MyRectcleAdapter(this, mDatas));


    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    class MyRectcleAdapter extends RecyclerView.Adapter<MyRectcleAdapter.MyViewHolder> {

        public MyRectcleAdapter(Context context, List<String> datas) {
            mContext = context;
            mydatas = datas;
            mLayoutinflater = LayoutInflater.from(context);

            mHeights = new ArrayList<>();
            for (int i = 0; i < mydatas.size(); i++) {
                mHeights.add((int) (100 + Math.random() * 300));
            }
        }

        @Override
        public int getItemCount() {
            return mydatas.size();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
            lp.height = mHeights.get(position);

            holder.tv.setText(mydatas.get(position));
            holder.tv.setLayoutParams(lp);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(mLayoutinflater.inflate(R.layout.item_staggered_home, parent, false));
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.id_num);
            }
        }
    }
}
