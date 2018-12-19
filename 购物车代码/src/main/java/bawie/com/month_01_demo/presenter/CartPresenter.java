package bawie.com.month_01_demo.presenter;


import bawie.com.month_01_demo.bean.Result;
import bawie.com.month_01_demo.core.BasePresenter;
import bawie.com.month_01_demo.core.DataCall;
import bawie.com.month_01_demo.model.CartModel;

/**
 * @author dingtao
 * @date 2018/12/6 14:41
 * qq:1940870847
 */
public class CartPresenter extends BasePresenter {

    public CartPresenter(DataCall dataCall) {
        super(dataCall);
    }
    @Override
    protected Result getData(Object... args) {
        Result result = CartModel.goodsList();//调用网络请求获取数据
        return result;
    }
}
