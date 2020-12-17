package com.example.roombasic;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//singleton 包含的表名称，版本号，
@Database(entities = {Word.class},version = 5,exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {

    private static WordDatabase INSTANCE;
    static synchronized WordDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"word_database")
                    //.fallbackToDestructiveMigration() 迁移方式，清空之前的数据，
                    .addMigrations(MIGRATION_4_5) //迁移方式，保留之前的数据
                    .addMigrations(MIGRATION_3_4)//每个对应的版本都要写
                    .build();
        }
        return INSTANCE;
    }

    // 会默认实现
    public abstract WordDao getWordDao();




    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 删除字段
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL ,english_word TEXT," +
                    "chinese_meaning TEXT)");
            database.execSQL("INSERT INTO word_temp (id,english_word,chinese_meaning) " +
                    "SELECT id,english_word,chinese_meaning FROM word");
            database.execSQL("DROP TABLE word");
            database.execSQL("ALTER TABLE word_temp RENAME to word");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN  chinese_invisible INTEGER NOT NULL DEFAULT 0");
        }
    };
}
