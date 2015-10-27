package ret.novelly.novelly;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewStoryClass extends Activity {
    private String storyID;
    String userID;
    database db;
    boolean userChoice = false;
    String pasteID;
    boolean hasChosenPrev;
    Story story;
    Pastes paste;
    Button pasteItBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstory);
        Bundle extra = getIntent().getExtras();

        userID = appClass.userID;

        //if storyID !=null show story if paste and story ID show, it is a paste, so grab the paste info
        try {
            if ((extra.getString("storyID") != null) && (extra.getString("pasteID") == null)) {
                storyID = extra.getString("storyID");
                story = db.getStory(storyID);
                //grab text
                display("story");
            } else if ((extra.getString("pasteID") != null) && (extra.getString("storyID") != null)) {
                storyID = extra.getString("storyID");
                pasteID = extra.getString("pasteID");
                paste = db.getPaste(pasteID);
                display("paste");
            }
        } catch (Exception e) {
            Log.v("error", e.getMessage());
        }

    }


    public void display(String context) {

        TextView userStory = ((TextView) findViewById(R.id.VS_viewuserstory));
        TextView title = ((TextView) findViewById(R.id.VS_title));

        if (context == "story") {

            userStory.setText(story.getUserStory());
            title.setText(story.getTitle());

        } else if (context == "paste") {

            //grab text
            userStory.setText(paste.getUserPaste());
            title.setText(paste.getTitle());

            //show paste button
            pasteItBtn = ((Button) findViewById(R.id.VS_btn_pasteit));
            pasteItBtn.setVisibility(View.VISIBLE);
            pasteItBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ViewStoryClass.this, pasteConfirmation.class);
                    startActivityForResult(intent, 1);
                    overridePendingTransition(R.anim.pushin, R.anim.pushout);

                }
            });
        }
    }

    /*
    Animation code
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        overridePendingTransition(R.anim.pushin, R.anim.pushout);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                overridePendingTransition(R.anim.pushin, R.anim.pushout);
                //check if user has chosen a paste related to this story
                hasChosenPrev = db.validatePaste((new Vote(pasteID, storyID, "")));
                //if the user has chosen previously
                if (hasChosenPrev == true) {
                    Toast.makeText(ViewStoryClass.this, "Sorry you can only choose one paste per story.", Toast.LENGTH_LONG).show();
                } else if (hasChosenPrev == false) {
                    db.addVote((new Vote(pasteID, storyID, userID)));
                    storyNavigate(storyID, storyID);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                overridePendingTransition(R.anim.pushin, R.anim.pushout);
            }
        }

    }

    protected void storyNavigate(final String column, final String ID) {


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                startActivity((new Intent(ViewStoryClass.this, UserPage.class)).putExtra(column, ID));
            }
        }, 100);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_story_class, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.DeletePastes) {

        } else if (id == R.id.ViewPastes) {
            Intent intent = new Intent(ViewStoryClass.this, ViewPastes.class);
            intent.putExtra("storyID", storyID);
            startActivity(intent);
        } else if (id == R.id.AddPaste) {

            Intent intent = new Intent(ViewStoryClass.this, submitStory.class);
            intent.putExtra("userID", userID);
            intent.putExtra("storyID", storyID);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
