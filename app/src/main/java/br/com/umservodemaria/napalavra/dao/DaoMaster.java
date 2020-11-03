package br.com.umservodemaria.napalavra.dao;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by cleiton.dantas on 05/06/2017.
 */

public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;



    public DaoMaster(Database db, int schemaVersion) {
        super(db, schemaVersion);
        registerDaoClass(AnotacoesEntityDao.class);
    }

    @Override
    public DaoSession newSession() {

        return null;
    }

    @Override
    public DaoSession newSession(IdentityScopeType type) {

        return null;
    }
}
