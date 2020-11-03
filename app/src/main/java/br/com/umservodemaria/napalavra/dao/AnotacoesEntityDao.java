package br.com.umservodemaria.napalavra.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

import br.com.umservodemaria.napalavra.vo.Anotacoes;

/**
 * Created by cleiton.dantas on 05/06/2017.
 */

public class AnotacoesEntityDao extends AbstractDao<Anotacoes, Long> {

    private static final String TABLE_ANOTACOES = "TB_ANOTACOES";

    public AnotacoesEntityDao(DaoConfig config) {
        super(config);
    }

    @Override
    protected Anotacoes readEntity(Cursor cursor, int offset) {
        Anotacoes entity = new Anotacoes(
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0) // id
        );
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, Anotacoes entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, Anotacoes entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, Anotacoes entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
    }

    @Override
    protected Long updateKeyAfterInsert(Anotacoes entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(Anotacoes entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected boolean hasKey(Anotacoes entity) {
        return entity.getId() != null;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}
