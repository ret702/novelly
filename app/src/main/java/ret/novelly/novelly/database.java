package ret.novelly.novelly;


import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "Novelly.db";

    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // SQL statement to create Story table
        String CREATE_Story_TABLE = "CREATE TABLE Storys ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userID INTERGER, " +
                "storyID TEXT, " +
                "title TEXT, " +
                "story TEXT )";
        db.execSQL(CREATE_Story_TABLE);

        String Create_Paste_Table = "CREATE TABLE Pastes ( storyID TEXT, pasteID TEXT, userID TEXT, paste TEXT, title TEXT)";
        db.execSQL(Create_Paste_Table);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older Storys table if existed
        db.execSQL("DROP TABLE IF EXISTS Storys");
        db.execSQL("DROP TABLE IF EXISTS Pastes");

        // create fresh Storys table
        this.onCreate(db);
        db.close();
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) Story + get all Storys + delete all Storys
     */

    // Storys table name
    private static final String TABLE_StoryS = "Storys";

    private static final String TABLE_Pastes = "Pastes";

    // Storys Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_STORY = "story";
    private static final String KEY_STORYID = "storyID";
    //paste specific
    private static final String KEY_PASTEID = "pasteID";
    private static final String KEY_PASTE = "paste";
    private static final String KEY_USERID = "userID";

    private static final String[] COLUMNS_Story = {KEY_ID, KEY_TITLE, KEY_STORY, KEY_STORYID};
    private static final String[] COLUMNS_Paste = { KEY_TITLE, KEY_STORYID, KEY_PASTEID,KEY_PASTE, KEY_USERID};

    public void addStory(Story Story) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, Story.getTitle()); // get title
        values.put(KEY_STORY, Story.getUserStory()); // get story
        values.put(KEY_STORYID,Story.getID());

        // 3. insert
        db.insert(TABLE_StoryS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        // 4. close
        db.close();
    }

    public Story getStory(String storyID) {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_StoryS, // a. table
                        COLUMNS_Story, // b. column names
                        " storyID = ?", // c. selections
                        new String[]{String.valueOf(storyID)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build Story object
        Story Story = new Story();
        Story.setID(cursor.getString(cursor.getColumnIndex("storyID")));
        Story.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        Story.setUserStory(cursor.getString(cursor.getColumnIndex("story")));


        Log.d("getStory(" + storyID + ")", Story.toString());
        db.close();
        // 5. return Story
        return Story;
    }

    // Get All Storys
    public List<Story> getAllStorys() {
        List<Story> Storys = new LinkedList<Story>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_StoryS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build Story and add it to list
        Story Story = null;
        if (cursor.moveToFirst()) {
            do {
                Story = new Story();
                Story.setID(cursor.getString(cursor.getColumnIndex("storyID")));
                Story.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                Story.setUserStory(cursor.getString(cursor.getColumnIndex("story")));


                // Add Story to Storys
                Storys.add(Story);
            } while (cursor.moveToNext());
        }

        Log.d("getAllStorys()", Storys.toString());
        db.close();
        // return Storys
        return Storys;
    }


    public List<Pastes> getAllPastes() {
        List<Pastes> pastes = new LinkedList<Pastes>();
        Pastes paste = new Pastes();
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_Pastes;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build Story and add it to list
        Story Story = null;
        if (cursor.moveToFirst()) {
            do {

                paste.setID(cursor.getString(cursor.getColumnIndex("storyID")),
                        cursor.getString(cursor.getColumnIndex("pasteID")));
                paste.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
                paste.setUserPaste(cursor.getString(cursor.getColumnIndex("paste")));
                paste.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                pastes.add(paste);
            } while (cursor.moveToNext());
        }


        db.close();
        // return pastes
        return pastes;
    }


    // Updating single Story
    public int updateStory(Story Story) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", Story.getTitle()); // get title


        // 3. updating row
        int i = db.update(TABLE_StoryS, //table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[]{String.valueOf(Story.getID())}); //selection args

        // 4. close
        db.close();

        return i;

    }

    public void addPaste(String storyID, String pasteID, String userID, String paste, String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("storyID", storyID);
        values.put("pasteID", pasteID);
        values.put("userID", userID);
        values.put("paste", paste);
        values.put("title", title);

        db.insert(TABLE_Pastes, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        db.close();
    }



    public Pastes getPaste(String pasteID) {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_Pastes, // a. table
                        COLUMNS_Paste, // b. column names
                        " pasteID = ?", // c. selections
                        new String[]{String.valueOf(pasteID)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build Story object
        Pastes paste  = new Pastes();
        paste.setID(cursor.getString(cursor.getColumnIndex("pasteID")), cursor.getString(cursor.getColumnIndex("storyID")));
        paste.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        paste.setUserPaste(cursor.getString(cursor.getColumnIndex("paste")));
        paste.setUserID(cursor.getString(cursor.getColumnIndex("userID")));

        db.close();
        // 5. return Story
        return paste;
    }




    void deleteTable(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE " + table);
        db.close();
    }

    // Deleting single Story
    public void deleteStory(Story Story) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_StoryS,
                KEY_ID + " = ?",
                new String[]{String.valueOf(Story.getID())});

        // 3. close
        db.close();

        Log.d("deleteStory", Story.toString());

    }

    public void clearDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_StoryS + "'");
        db.delete(TABLE_StoryS, null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_Pastes + "'");
        db.delete(TABLE_Pastes, null, null);
        db.close();
    }

    @TargetApi(18)
    public void deleteDB(Context context) {
     context.deleteDatabase("Novelly.db");
    }

    public boolean isEmpty(String table) {
        boolean empty = true;
        // 1. build the query
        String query = "SELECT  * FROM " + table;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 0) {
                empty = true;
            } else {
                empty = false;
            }

        } catch (Exception e) {

        }

        // 3. go over each row, build Story and add it to list
        db.close();

        return empty;

    }
}
