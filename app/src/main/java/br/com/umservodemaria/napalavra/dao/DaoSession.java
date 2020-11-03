package br.com.umservodemaria.napalavra.dao;

import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

/**
 * Created by cleiton.dantas on 05/06/2017.
 */

public class DaoSession extends AbstractDaoSession {

    public DaoSession(Database db) {
        super(db);
    }
}
