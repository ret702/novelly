package ret.novelly.novelly;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class database extends AsyncTask<String, Integer, Object[]> {
    //whether the user has choosen a paste in the same story before
    boolean hasChoosenPrev;
    ArrayList<Story> stories;
    String indexer;
    //TODO: Change all functions to background tasks

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

    //default constructor
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


    public void saveAvatar(File file) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Thumbs");
        ParseFile parsefile= new ParseFile(file);
        try {
            ParseObject parseObb = query.getFirst();
            parseObb.put("thumb",parsefile);
            parseObb.saveInBackground();
        }
        catch(Exception e) {
            ParseObject thumb = new ParseObject("Thumbs");
            thumb.put("thumb",parsefile);
            thumb.put("userID",appClass.userID);
            thumb.saveInBackground();

        }
    }

    // Get All Storys
    public Object[] getAllStorys() {
        ArrayList<ParseObject> parseObbs = new ArrayList<ParseObject>();
        HashMap<String, String> storyIDs = new HashMap<String, String>();
        ArrayList<String> storys = new ArrayList<String>();
        Object[] titleNStrings = new Object[2];
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Stories");
        //parse objects
        query.setLimit(10);
        try {
            parseObbs = (ArrayList<ParseObject>) query.find();
            for (ParseObject ob : parseObbs) {
                storys.add(ob.getString("title"));
                storyIDs.put(Integer.toString(storys.size() - 1), ob.getString("storyID"));
            }
            titleNStrings[0] = storys;
            titleNStrings[1] = storyIDs;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return titleNStrings;
    }


    public Object[] getAllPastes(String storyID) {

        ArrayList<String> pastes = new ArrayList<String>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pastes");
        List<ParseObject> parseObbs = new ArrayList<ParseObject>();
        HashMap<String, String> pasteIDs = new HashMap<String, String>();
        Object[] allPastes = new Object[2];

        query.whereEqualTo("storyID", storyID);
        try {
            parseObbs = query.find();
            for (ParseObject ob : parseObbs) {
                pastes.add(ob.getString("title"));
                pasteIDs.put(Integer.toString(pastes.size() - 1), ob.getString("pasteID"));
            }

            allPastes[0] = pastes;
            allPastes[1] = pasteIDs;

        } catch (Exception e) {
            Log.v("error", e.getMessage());
        }

        // return pastes
        return allPastes;
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
            if (test.size() == 0) {
                hasChoosenPrev = false;
            } else {
                hasChoosenPrev = true;
            }
        }

        return hasChoosenPrev;
    }

    protected void addVote(final Vote vote) {
        try {

            //query
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Votes");
            query.whereEqualTo("storyID", vote.getStoryID());
            query.whereEqualTo("userID", vote.getUserID());
            //add to votes class
            ParseObject voteClass = new ParseObject("Votes");
            voteClass.put("pasteID", vote.getPasteID());
            voteClass.put("storyID", vote.getStoryID());
            voteClass.put("userID", vote.getUserID());
            voteClass.saveInBackground();

//            //add to totalvotes in paste class
            ParseQuery<ParseObject> updateTotal = ParseQuery.getQuery("TotalVotes");
            updateTotal.whereEqualTo("pasteID", vote.getPasteID());

            updateTotal.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        object.increment("votes", 1);
                        object.saveInBackground();
                    } else {
                        ParseObject voteClass = new ParseObject("TotalVotes");
                        voteClass.put("pasteID", vote.getPasteID());
                        voteClass.put("storyID", vote.getStoryID());
                        voteClass.put("votes", 1);
                        voteClass.saveInBackground();
                    }

                }
            });
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
            String totalvotes = parseObb.getString("totalotes");
            if (totalvotes == null) {
                paste.setTotalVotes("0");
            } else {
                paste.setTotalVotes(totalvotes);
            }
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

        indexer = params[0];
        if (params[0] == "getstories") {
            return getAllStorys();
        } else if (params[0] == "pastes") {
            return getAllPastes(params[1].toString());
        }

        return null;
    }

    protected void onPostExecute(Object[] result) {
        Adapter adapter = new Adapter(context, R.id.mainlistview, (ArrayList<String>) result[0], (HashMap) result[1]);
        //set view IDS from story IDs
        listview.setAdapter(adapter);

    }


}
