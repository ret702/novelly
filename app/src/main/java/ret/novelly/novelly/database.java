package ret.novelly.novelly;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class database extends AsyncTask<String, Integer, Object[]> {
    //whether the user has choosen a paste in the same story before
    boolean hasChoosenPrev;
    ArrayList<Story> stories;


    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) Story + get all Storys + delete all Storys
     */


    ListView listview;
    Context context;

    database(ListView listview, Context context) {
        this.listview = listview;
        this.context = context;
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

        final Story story = new Story();

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
    public Object[] getAllStorys() {
        ArrayList<ParseObject> parseObbs = new ArrayList<ParseObject>();
        HashMap storyIDs = new HashMap();
        ArrayList<String> items = new ArrayList<String>();
     Object[] test = new Object[2];
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Stories");
        //parse objects
        query.setLimit(10);
        query.whereEqualTo("userID", "4f1b3acd-23a7-44fb-9b77-1bce8bd4336d");
        try {
            parseObbs = (ArrayList<ParseObject>) query.find();

            for (ParseObject ob : parseObbs) {
                items.add(ob.getString("title"));
                storyIDs.put(Integer.toString(items.size()), ob.getString("storyID"));
            }
            test[0]=items;
            test[1]=storyIDs;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return test;
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
//        HashMap storyIDs = new HashMap();
//        ArrayList<String> items = new ArrayList<String>();
        ArrayList<ParseObject> test = new ArrayList<ParseObject>();
//
       if(params[0]=="getstories"){
        return getAllStorys();
        }

       return null;
    }

    protected void onPostExecute(Object[] result) {
        ArrayAdapter adapter = new ArrayAdapter<String>(context, R.layout.mainlisttextbox,(ArrayList<String>) result[0]);
        listview.setAdapter(adapter);

        return result[1];

    }




}
