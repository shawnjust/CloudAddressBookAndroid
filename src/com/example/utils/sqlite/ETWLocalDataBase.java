package com.example.utils.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 本地数据库
 * @author Ren Jiayue
 *
 */
final public class ETWLocalDataBase extends SQLiteOpenHelper{
	/**
	 * 数据库名称
	 */
	private final static String DATABASE_NAME = "ETW.db";
	/**
	 * 数据库版本号
	 */
	private final static int DATABASE_VERSION = 1;
	/**
	 * 数据库实例
	 */
	private static ETWLocalDataBase localDB;
	/**
	 * 表名
	 */
	private final static String[] TABLE_NAMES = {"HISTORY"};
	/**
	 * 历史记录表列名
	 */
	private final static String[] HISTORYCOLUMNS = { "_id", "Name" ,"Times"};
	
	
	
	private ETWLocalDataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
	 * 获得数据库实例
	 * @param context 上下文
	 * @return 数据库实例
	 */
		public static ETWLocalDataBase getInstance(Context context) {
			if (localDB == null)
				synchronized (ETWLocalDataBase.class) {
					if (localDB == null)
						localDB = new ETWLocalDataBase(context);
				}
			return localDB;
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE "+TABLE_NAMES[0]+"("
					+ HISTORYCOLUMNS[0]+" INTEGER primary key autoincrement,"
					+ HISTORYCOLUMNS[1]+" char(20),"
					+ HISTORYCOLUMNS[2]+" INTEGER );");
			
			Log.i("db", "create succeed");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES[0]);
			db.execSQL("DROP TABLE IF EXISTS WARDROBE");
			onCreate(db);
		}
		
		
		
		/**
		 * 获得搜索历史记录的数据库游标
		 * @param key 关键字
		 * @param top 截取前多少条
		 * @return 游标
		 */
		public Cursor getHistoryProductCursor(String key, int top){
			SQLiteDatabase db = this.getReadableDatabase();
			
			String sql = "SELECT  * FROM "+TABLE_NAMES[0];
			if(key!=null){
				sql = sql+ " WHERE "+HISTORYCOLUMNS[1]+" LIKE '"+key+"%'";
			}
			if(top <= 0){
				sql = sql + " ORDER BY "+HISTORYCOLUMNS[2]+" DESC";
			}else{
				sql = sql +" ORDER BY "+HISTORYCOLUMNS[2]+" DESC"
						+" LIMIT "+top+" OFFSET 0";
			}
			Cursor cursor = db.rawQuery(sql, null);
			
			cursor.moveToFirst();
			return cursor;
		}
		
		
		/**
		 * 清空历史记录表
		 */
		public void deleteAllHistory(){
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("delete from "+TABLE_NAMES[0]);
		}
		/**
		 * 怎加排名权重（每搜索一次相应关键字权重加1）
		 * @param key 关键字
		 */
		public void increaseHistoryRank(String key){
			if(!isHistoryExist(key)){
				insertHistory(key);
				return;
			}
			SQLiteDatabase db = this.getWritableDatabase();

			String sql = "update "+TABLE_NAMES[0]+" set "+HISTORYCOLUMNS[2]+"= "
			+TABLE_NAMES[0]+"."+HISTORYCOLUMNS[2]+"+1 where "+HISTORYCOLUMNS[1]+" = '"+key+"'";
			db.execSQL(sql);
			Log.i("db","increaseHistoryRank:"+getHistoryProductCursor(null, -1).getCount());
		}
		/**
		 * 是否存在这条历史记录
		 * @param key 关键字
		 * @return 是否存在
		 */
		private boolean isHistoryExist(String key){
			SQLiteDatabase db = this.getReadableDatabase();
			
			String sql = "SELECT  * FROM "+TABLE_NAMES[0];
			sql = sql+ " WHERE "+HISTORYCOLUMNS[1]+" = '"+key+"'";
			
			Cursor cursor = db.rawQuery(sql, null);
			
			return cursor.getCount()>0;
		}
		
		/**
		 * 插入一条历史记录
		 * @param key 关键字
		 */
		private void insertHistory(String key){
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues cv = new ContentValues();//实例化ContentValues
			cv.put(HISTORYCOLUMNS[1],key);
			cv.put(HISTORYCOLUMNS[2],1);//添加要更改的字段及内容
			db.insert(TABLE_NAMES[0], null, cv);
			Log.i("db","insertHistory:"+getHistoryProductCursor(null, -1).toString());
		}
		
		
		
		/**
	     * 关闭数据源
	     */
	    public void closeConnection() {
	        if (ETWLocalDataBase.localDB!=null)
	            this.close();
	    }


}
