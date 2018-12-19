package bawie.com.month_01_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bawie.com.month_01_demo.adapter.CarAdapter;
import bawie.com.month_01_demo.bean.Result;
import bawie.com.month_01_demo.bean.Shop;
import bawie.com.month_01_demo.core.DataCall;
import bawie.com.month_01_demo.presenter.CartPresenter;

public class MainActivity extends AppCompatActivity implements CarAdapter.TotalPriceLister, DataCall<List<Shop>> {

    private TextView mSunPirce;
    private CheckBox mCheck_All;
    private CarAdapter carAdapter;
    private ExpandableListView mGoodsList;
    private CartPresenter cartPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSunPirce = findViewById(R.id.goods_sum_price);
        mCheck_All = findViewById(R.id.check_all);
        mGoodsList = findViewById(R.id.list_car);

        mGoodsList.setGroupIndicator(null); // 没有箭头
        // 让group不能被点击
        mGoodsList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        //点击全选按钮
        mCheck_All.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                carAdapter.checkAll(isChecked);//全部选中
            }
        });
        // 得到适配器对象
        carAdapter = new CarAdapter();
        carAdapter.setTotalPriceLister(this);// 回调总金额
        // 设置适配器
        mGoodsList.setAdapter(carAdapter);
        cartPresenter = new CartPresenter(this);// 调用P层
        cartPresenter.requestData();// 使用MVP模式
    }

    @Override
    public void totalprice(double totalprice) {
        // 设置总金额
        mSunPirce.setText(String.valueOf(totalprice));
    }

    @Override
    public void success(List<Shop> data) {
        carAdapter.addAll(data);

        // 默认展开 所有数据
        int size = data.size();
        for (int i = 0; i < size; i++) {
            mGoodsList.expandGroup(i);// 展开
        }
        // 更新数据
        carAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(Result result) {
        cartPresenter.unBindCall();// 防治内存泄露
        Toast.makeText(this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
