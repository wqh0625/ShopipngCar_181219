package bawie.com.month_01_demo.core;

import bawie.com.month_01_demo.bean.Result;

/**
 * @author dingtao
 * @date 2018/12/6 14:42
 * qq:1940870847
 */
public interface DataCall<T> {

    void success(T data);

    void fail(Result result);
}
