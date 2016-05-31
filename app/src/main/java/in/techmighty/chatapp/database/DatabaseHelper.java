package in.techmighty.chatapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.StringBuilderPrinter;

import java.util.ArrayList;
import java.util.List;

import in.techmighty.chatapp.model.ChatModel;
import in.techmighty.chatapp.model.MessageModel;
import in.techmighty.chatapp.model.UserModel;

/**
 * Created by ankit varia on 30/05/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = DatabaseHelper.class.getSimpleName();

    //Database Main Variable
    private static final String DATABASE_NAME = "ChatApp";
    private static final int DATABASE_VERSION = 1;

    //Ohter Variables
    private static DatabaseHelper sInstance;


    //TABLE_NAME
    private static final String TABLE_CHAT_APP = "TABLE_CHAT_APP";

    //COLUMN_NAME
    private static final String CHAT_KEY_ID = "CHAT_KEY_ID";
    private static final String CHAT_USER_NAME = "CHAT_USER_NAME";
    private static final String CHAT_NAME = "CHAT_NAME";
    private static final String CHAT_BODY = "CHAT_BODY";
    private static final String CHAT_IMAGE_URL = "CHAT_IMAGE_URL";
    private static final String CHAT_MSG_TIME = "CHAT_MSG_TIME";
    private static final String CHAT_FAV = "CHAT_FAV";


    //Virtual Column Name
    private static final String CHAT_FAV_TOTAL = "CHAT_FAV_TOTAL";
    private static final String CHAT_TOTAL = "CHAT_TOTAL";


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }

        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_CHAT_APP = "CREATE TABLE " + TABLE_CHAT_APP + "("

                + CHAT_KEY_ID + " INTEGER PRIMARY KEY,"

                + CHAT_BODY + " TEXT, "

                + CHAT_USER_NAME + " TEXT, "

                + CHAT_NAME + " TEXT, "

                + CHAT_MSG_TIME + " TEXT, "

                + CHAT_IMAGE_URL + " TEXT, "

                + CHAT_FAV + " BOOLEAN "

                + ")";

        db.execSQL(CREATE_TABLE_CHAT_APP);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        int upgradeTo = oldVersion + 1;
        while (upgradeTo <= newVersion) {
            switch (upgradeTo) {

            }
        }

    }

    public void deleteChatHistory(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHAT_APP, null, null);
    }

    public boolean insertChatHistory(MessageModel msgModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CHAT_BODY, msgModel.getBody());
        contentValues.put(CHAT_USER_NAME, msgModel.getUserName());
        contentValues.put(CHAT_NAME, msgModel.getName());
        contentValues.put(CHAT_MSG_TIME, msgModel.getMsgTime());
        contentValues.put(CHAT_IMAGE_URL, msgModel.getImgUrl());
        contentValues.put(CHAT_FAV, false);


        long row = db.insert(TABLE_CHAT_APP, null, contentValues);

        if (row > 0) {
            Log.e(TAG, "cardDetails inserted successfully");
            return true;
        }
        Log.e(TAG, "cardDetails inserted error");
        return false;
    }

    public ChatModel getChatHistory() {


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(true, TABLE_CHAT_APP, null, null,
                null, null, null, null, null);
        ChatModel chatModel = new ChatModel();
        try {

            List<MessageModel> msgModelList = new ArrayList<MessageModel>();

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    MessageModel msgModel = new MessageModel();

                    msgModel.setBody(cursor.getString(cursor.getColumnIndex(CHAT_BODY)));
                    msgModel.setName(cursor.getString(cursor.getColumnIndex(CHAT_NAME)));
                    msgModel.setUserName(cursor.getString(cursor.getColumnIndex(CHAT_USER_NAME)));
                    msgModel.setMsgTime(cursor.getString(cursor.getColumnIndex(CHAT_MSG_TIME)));
                    msgModel.setImgUrl(cursor.getString(cursor.getColumnIndex(CHAT_IMAGE_URL)));
                    msgModel.setFavMsg(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(CHAT_FAV))));

                    msgModelList.add(msgModel);
                } while (cursor.moveToNext());
            }

            cursor.close();
            chatModel.setChatCount(msgModelList.size());
            chatModel.setMessageModelList(msgModelList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return chatModel;


    }

    public boolean updateChatFavStatus(long position) {
        SQLiteDatabase db = this.getWritableDatabase();

        String condition = CHAT_KEY_ID + " = (" + position + ")";

        ContentValues contentValues = new ContentValues();
        contentValues.put(CHAT_FAV, 1);


        long rows = db.update(TABLE_CHAT_APP, contentValues, condition, null);

        if (rows > 0)
            Log.e(TAG, "status " + 1 + " updated for " + position);
        else
            Log.e(TAG, "status " + 0 + " Error in updating  for " + position);

        return rows > 0;
    }

    public List<UserModel> getUserInfo() {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select " + CHAT_USER_NAME + ", " + CHAT_NAME + ", count(" + CHAT_USER_NAME + ") as "+ CHAT_TOTAL +", sum(" + CHAT_FAV + ") as "+ CHAT_FAV_TOTAL+" from " + TABLE_CHAT_APP + " group by " + CHAT_USER_NAME + "";
        Log.e(TAG, query);
        Cursor cursor = db.rawQuery(query, null);
        List<UserModel> userChatList = new ArrayList<UserModel>();
        try {



            if (cursor != null && cursor.moveToFirst()) {
                do {
                    UserModel userModel = new UserModel();

                    userModel.setUserName(cursor.getString(cursor.getColumnIndex(CHAT_USER_NAME)));
                    userModel.setName(cursor.getString(cursor.getColumnIndex(CHAT_NAME)));
                    userModel.setTotalMessages(cursor.getString(cursor.getColumnIndex(CHAT_TOTAL)));
                    userModel.setFavMessages(cursor.getString(cursor.getColumnIndex(CHAT_FAV_TOTAL)));

                    userChatList.add(userModel);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return userChatList;
    }
}

