package ret.novelly.novelly;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.wearable.internal.StorageInfoResponse;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class database extends AsyncTask<String, Integer, Object[]> {
    //whether the user has choosen a paste in the same story before
    boolean hasChoosenPrev;
    ArrayList<Story> stories;
    String param;


    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) Story + get all Storys + delete all Storys
     */


    ListView listview;
    Context context;

    database(View view, Context context) {

        if (view.getClass() == ListView.class) {
            this.listview = (ListView) view;
        }
        this.context = context;
    }

    database() {

    }

    public void addStory(Story story) {
        ParseObject Stories = new ParseObject("Stories");

        Stories.put("storyID", story.getID());
        Stories.put("userID", story.getUserID());
        Stories.put("story", story.getUserStory());
        Stories.put("title", story.getTitle());
        Stories.saveInBackground();
    }

    public Story getStory(String storyID) {

        Story story = new Story();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Stories");
        query.whereEqualTo("storyID", storyID);
        try {
            ParseObject parseObb = query.getFirst();
            story.setUserID(parseObb.getString("userID"));
            story.setID(parseObb.getString("storyID"));
            story.setUserStory(parseObb.getString("story"));
            story.setTitle(parseObb.getString("title"));
        } catch (Exception e) {
        }

        return story;
    }

    // Get All Storys
    public Object[] getAllStorys() {
        ArrayList<ParseObject> parseObbs = new ArrayList<ParseObject>();
        HashMap<String, String> storyIDs = new HashMap<String, String>();
        ArrayList<String> items = new ArrayList<String>();
        Object[] titleNStrings = new Object[2];
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Stories");
        //parse objects
        query.setLimit(10);
        query.whereEqualTo("userID", "4f1b3acd-23a7-44fb-9b77-1bce8bd4336d");
        try {
            parseObbs = (ArrayList<ParseObject>) query.find();

            for (ParseObject ob : parseObbs) {
                items.add(ob.getString("title"));
                storyIDs.put(Integer.toString(items.size() - 1), ob.getString("storyID"));
            }
            titleNStrings[0] = items;
            titleNStrings[1] = storyIDs;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return titleNStrings;
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
        query.whereEqualTo("userID", vote.getUserID());

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

    protected void addVote(Vote vote) {
        try {

            //query
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Votes");
            query.whereEqualTo("storyID", vote.getStoryID());
            query.whereEqualTo("userID", vote.getUserID());
            //add to votes class
            ParseObject voteOb = query.getFirst();
            ParseObject voteClass = new ParseObject("Votes");
            voteClass.put("pasteID", vote.getPasteID());
            voteClass.put("storyID", vote.getStoryID());
            voteClass.put("userID", vote.getUserID());
            voteClass.saveInBackground();

            //add to totalvotes in paste class
            ParseQuery<ParseObject> updateTotal = ParseQuery.getQuery("Pastes");
            updateTotal.whereEqualTo("storyID", vote.getStoryID());
            ParseObject object = updateTotal.getFirst();
            object.increment("totalvotes");
            object.saveInBackground();

        } catch (Exception e) {

        }

    }

    // Updating single Story
    public int updateStory(Story Story) {

        return 0;
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

        Pastes paste = new Pastes();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pastes");
        query.whereEqualTo("pasteID", pasteID);
        try {
            ParseObject parseObb = query.getFirst();
            paste.setUserID(parseObb.getString("userID"));
            paste.setID(parseObb.getString("storyID"));
            paste.setUserPaste(parseObb.getString("paste"));
            paste.setTitle(parseObb.getString("title"));
            paste.setID(parseObb.getString("pasteID"));
        } catch (Exception e) {
        }


        return paste;
    }


    void deleteTable(String table) {

    }

    // Deleting single Story
    public void deleteStory(Story Story) {


    }

    public void clearDB() {
    }


    public void deleteDB(Context context) {

    }

    public boolean isEmpty(String table) {

        return false;
    }

    @Override
    protected Object[] doInBackground(String... params) {

        param = params[0];
        if (params[0] == "getstories") {
            return getAllStorys();
        }

        return null;
    }

    protected void onPostExecute(Object[] result) {
        if (param == "getstories") {
            Adapter adapter = new Adapter(context, R.id.mainlistview, (ArrayList<String>) result[0], (HashMap) result[1]);
            //set view IDS from story IDs

            listview.setAdapter(adapter);
        }


    }


}
