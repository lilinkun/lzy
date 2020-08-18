package com.lzyyd.hsq.base;

import android.content.Context;

import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.data.source.HttpDataSource;
import com.lzyyd.hsq.data.source.LocalDataSource;
import com.lzyyd.hsq.http.HttpDataSourceImpl;
import com.lzyyd.hsq.http.LocalDataSourceImpl;
import com.lzyyd.hsq.http.RetrofitHelper;
import com.lzyyd.hsq.http.RetrofitService;

public class Injection  {
    public static DataRepository provideDemoRepository(Context context) {
        //网络API服务
        RetrofitService apiService = RetrofitHelper.getInstance(context).getServer();
        //网络数据源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return DataRepository.getInstance(httpDataSource, localDataSource);
    }
}
