package com.lzyyd.hsq.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lzyyd.hsq.bean.DaoMaster;
import com.lzyyd.hsq.bean.DaoSession;
import com.lzyyd.hsq.bean.SearchBean;
import com.lzyyd.hsq.bean.SearchBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Create by liguo on 2020/9/2
 * Describe:
 */
public class DBManager {
    private final static String dbName = "lzyyd_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条记录
     *
     * @param searchBean
     */
    public void insertSearchBean(SearchBean searchBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearchBeanDao userDao = daoSession.getSearchBeanDao();
        userDao.insert(searchBean);
    }

    /**
     * 查询用户列表
     */
    public List<SearchBean> querySearch(String search) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearchBeanDao userDao = daoSession.getSearchBeanDao();
        QueryBuilder<SearchBean> qb = userDao.queryBuilder();
        qb.where(SearchBeanDao.Properties.Searchname.eq(search)).orderAsc(SearchBeanDao.Properties.Searchname);
        List<SearchBean> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<SearchBean> querySearchBean(String username) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearchBeanDao userDao = daoSession.getSearchBeanDao();
        QueryBuilder<SearchBean> qb = userDao.queryBuilder();
        qb.where(SearchBeanDao.Properties.Username.eq(username)).orderAsc(SearchBeanDao.Properties.Username);
        List<SearchBean> list = qb.list();
        return list;
    }

    /**
     * 删除所有记录
     */
    public void deleteAllSearchBean() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearchBeanDao userDao = daoSession.getSearchBeanDao();
        userDao.deleteAll();
    }
}
