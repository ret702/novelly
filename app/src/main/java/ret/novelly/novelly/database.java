package ret.novelly.novelly;


import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class database extends SQLiteOpenHelper {
    //whether the user has choosen a paste in the same story before
    boolean hasChoosenPrev;

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
    private static final String[] COLUMNS_Paste = {KEY_TITLE, KEY_STORYID, KEY_PASTEID, KEY_PASTE, KEY_USERID};

    public void addStory(Story story) {
        ParseObject Stories = new ParseObject("Stories");

        Stories.put("storyID", story.getID());
        Stories.put("userID", story.getUserID());
        Stories.put("story", story.getUserStory());
        Stories.put("title", story.getTitle());
        Stories.saveInBackground();
    }

    public Story getStory(String storyID) {

        final Story story= new Story();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Stories");
        query.whereEqualTo("storyID", storyID);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    story.setUserID(object.getString("userID"));
                    story.setID(object.getString("storyID"));
                    story.setUserStory(object.getString("story"));
                    story.setTitle(object.getString("title"));

                } else {

                }
            }
        });

        return story;
    }

    // Get All Storys
    public List<Story> getAllStorys() {

        ArrayList<Story> stories = new ArrayList<Story>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Stories");
        //parse objects
        List<ParseObject> parseObbs = new ArrayList<ParseObject>();

        try {
            parseObbs = query.find();
            for (ParseObject object : parseObbs) {
                Story pasteObb = new Story();
                pasteObb.setID(object.getString("storyID"));
                pasteObb.setUserID(object.getString("userID"));
                pasteObb.setUserStory(object.getString("story"));
                pasteObb.setTitle(object.getString("title"));
                stories.add(pasteObb);
            }

        } catch (Exception e) {
            Log.v("error", e.getMessage());
        }

        // return pastes
        return stories;
    }


    public List<Pastes> getAllPastes(String storyID) {

        ArrayList<Pastes> pastes = new ArrayList<Pastes>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pastes");
        List<ParseObject> parseObbs = new ArrayList<ParseObject>();

        query.whereEqualTo("storyID", storyID);
        try {
            parseObbs = query.find();
            for (ParseObject object : parseObbs) {
                Pastes pasteObb = new Pastes();
                pasteObb.setID(object.getString("pasteID"));
                pasteObb.setUserID(object.getString("userID"));
                pasteObb.setUserPaste(object.getString("paste"));
                pasteObb.setTitle(object.getString("title"));
                pastes.add(pasteObb);
            }

        } catch (Exception e) {
            Log.v("error", e.getMessage());
        }

        // return pastes
        return pastes;
    }


    protected boolean validatePaste(Vote vote) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Votes");
        query.whereEqualTo("storyID", vote.getStoryID());
        query.whereEqualTo("userID",vote.getUserID());

        List<ParseObject> test = new ArrayList<ParseObject>();

        try {
            test = query.find();
        } catch (ParseException e) {

        } finally {
            if (test == null) {
                hasChoosenPrev = false;
            } else {
                hasChoosenPrev = true;
            }
        }


        return hasChoosenPrev;
    }

    protected void addVote(final Vote vote) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Votes");
        query.whereEqualTo("storyID", vote.getStoryID());
        query.whereEqualTo("userID", vote.getUserID());
        //add to votes class
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    //user has already chosen a paste for story

                } else {
                    ParseObject voteClass = new ParseObject("Votes");
                    voteClass.put("pasteID", vote.getPasteID());
                    voteClass.put("storyID", vote.getStoryID());
                    voteClass.put("userID", vote.getUserID());
                    voteClass.saveInBackground();
                }
            }
        });
        //add to totalvotes in paste class
        ParseQuery<ParseObject> updateTotal = ParseQuery.getQuery("Pastes");
        updateTotal.whereEqualTo("storyID", vote.getStoryID());
        updateTotal.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    object.increment("totalvotes");
                    object.saveInBackground();

                } else {
                }
            }
        });

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

    public void addPaste(Pastes paste) {
        ParseObject pastes = new ParseObject("Pastes");

        pastes.put("storyID", paste.getStoryID());
        pastes.put("pasteID", paste.getID());
        pastes.put("userID", paste.getUserID());
        pastes.put("paste", paste.getUserPaste());
        pastes.put("title", paste.getTitle());
        pastes.saveInBackground();
    }


    public Pastes getPaste(String pasteID) {

        final Pastes pastes = new Pastes();


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pastes");
        query.whereEqualTo("pasteID", pasteID);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    pastes.setUserID(object.getString("userID"));
                    pastes.setID(object.getString("pasteID"));
                    pastes.setUserPaste(object.getString("paste"));
                    pastes.setTitle(object.getString("title"));

                } else {

                }
            }
        });


        return pastes;
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
            cursor.close();

        } catch (Exception e) {

        }


        // 3. go over each row, build Story and add it to list

        db.close();

        return empty;

    }
}
