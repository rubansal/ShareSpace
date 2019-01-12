package com.codingblocks.msitnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String TABLE_NAME="downloads";

    public DBHelper(Context context) {
        super(context, "downloads.db", null, 1);
        this.context=context;
    }

    public static class DBHelperItem implements BaseColumns{
        public static final String FILE_NAME="file_name";
        public static final String FILE_PATH="file_path";
        public static final String FILE_SIZE="file_size";
        public static final String TIME="time";
    }

    String SQL_CREATE_ENTRIES=
            "CREATE TABLE " + TABLE_NAME + " (" +
                    DBHelperItem._ID + " INTEGER PRIMARY KEY" + "," +
                    DBHelperItem.FILE_NAME + " TEXT" + "," +
                    DBHelperItem.FILE_PATH + " TEXT" + "," +
                    DBHelperItem.FILE_SIZE + " TEXT " + "," +
                    DBHelperItem.TIME + " INTEGER " + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Download getItemAt(int position){
        SQLiteDatabase db=getReadableDatabase();
        String[] downloads={
                DBHelperItem._ID,
                DBHelperItem.FILE_NAME,
                DBHelperItem.FILE_PATH,
                DBHelperItem.FILE_SIZE,
                DBHelperItem.TIME
        };
        Cursor c=db.query(TABLE_NAME,
                downloads,
                null,null,
                null,null,null
        );
        if(c.moveToPosition(position)){
            Download download=new Download();
            download.setId(c.getInt(c.getColumnIndex(DBHelperItem._ID)));
            download.setName(c.getString(c.getColumnIndex(DBHelperItem.FILE_NAME)));
            download.setPath(c.getString(c.getColumnIndex(DBHelperItem.FILE_PATH)));
            download.setLength(c.getString(c.getColumnIndex(DBHelperItem.FILE_SIZE)));
            download.setDate(c.getLong(c.getColumnIndex(DBHelperItem.TIME)));
            download.setArrayListItems(c.getString(c.getColumnIndex(DBHelperItem.FILE_NAME)),
                    c.getString(c.getColumnIndex(DBHelperItem.FILE_PATH)),
                    c.getInt(c.getColumnIndex(DBHelperItem._ID)),
                    c.getString(c.getColumnIndex(DBHelperItem.FILE_SIZE)),
                    c.getLong(c.getColumnIndex(DBHelperItem.TIME)));
            c.close();
            return download;
        }
        return null;
    }

    public long addDownloads(String fileName,String filePath,String length){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DBHelperItem.FILE_NAME,fileName);
        cv.put(DBHelperItem.FILE_PATH,filePath);
        cv.put(DBHelperItem.FILE_SIZE,length);
        cv.put(DBHelperItem.TIME,System.currentTimeMillis());
        long row=db.insert(TABLE_NAME,null,cv);
        return row;
    }

    public int getCount(){
        SQLiteDatabase db=getReadableDatabase();
        String[] downloads={DBHelperItem._ID};
        Cursor c=db.query(TABLE_NAME,
                downloads,
                null,null,
                null,null,null
        );
        int count=c.getCount();
        c.close();
        return count;
    }

    public void removeItem(int id){
        SQLiteDatabase db=getWritableDatabase();
        String[] whereArgs={String.valueOf(id)};
        db.delete(TABLE_NAME,"_ID=?",whereArgs);
    }
}
