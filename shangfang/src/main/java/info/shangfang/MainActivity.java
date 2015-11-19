package info.shangfang;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<String> mList = Arrays.asList("打的", "叫外卖", "查快递", "挂号", "装修", "看电影", "家政服务", "衣物干洗", "缴费记录");
    private Adapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = find(R.id.id_recyclerview);
        mLayoutManager = new GridLayoutManager(this, 3);
        mAdapter = new Adapter(this) {
            @Override
            protected void onItemClick(View v, int position) {
                Toast(position);
            }
        };
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mRadioGroup = find(R.id.rg);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        Toast("首页");
                        break;
                    case R.id.rb_neighbor:
                        Toast("邻里");
                        break;
                    case R.id.rb_mine:
                        Toast("我的");
                        break;
                }
            }
        });
    }


    abstract class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
        Context mContext;

        protected abstract void onItemClick(View v, int position);

        public Adapter(Context context) {
            this.mContext = context;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.mTextView.setText(mList.get(position));
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            switch (position) {
                case 0:
                case 1:
                case 3:
                case 4:
                    params.setMargins(0, 0, 2, 2);
                    break;
                case 2:
                case 5:
                    params.setMargins(0, 0, 0, 2);
                    break;
                case 6:
                case 7:
                    params.setMargins(0, 0, 2, 0);
                    break;

            }

            holder.itemView.setLayoutParams(params);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            ImageView mImageView;
            TextView mTextView;

            public Holder(View itemView) {
                super(itemView);
                mImageView = find(itemView, R.id.iv);
                mTextView = find(itemView, R.id.text);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(v, getAdapterPosition());
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
