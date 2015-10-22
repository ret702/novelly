package ret.novelly.novelly;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class UserPage extends Activity {
    String userID = "";
    String pasteID = "";
    String storyID = "";
    boolean isWinner = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        Bundle extra = getIntent().getExtras();
        Pastes paste = new Pastes();
        Story story = new Story();
        database db = new database(this);
        db.getWritableDatabase();

        boolean isPaste = false;
        boolean isStory = false;
        userID = appClass.userID;
        try {
            if ( (extra.getString("pasteID") != null)) {
                storyID = extra.getString("storyID");
                pasteID = extra.getString("pasteID");
                paste = db.getPaste(pasteID);
                isPaste = true;
            } else if ((extra.getString("storyID") != null) ) {
                storyID = extra.getString("storyID");
                story = db.getStory(storyID);
                isStory = true;
            }

        } catch (Exception e) {
        }
    //show checkbox if the paste/story is a winner
        if (isWinner) {
        } else {
            CheckBox CB_winner = ((CheckBox) findViewById(R.id.checkBox_winner));
            CB_winner.setVisibility(View.INVISIBLE);
        }

        if (isPaste) {
            ((TextView) findViewById(R.id.textView_up_booktext)).setText(paste.getUserPaste());
            ((TextView) findViewById(R.id.textView_up_booktitle)).setText(paste.getTitle());
        } else if (isStory) {
            ((TextView) findViewById(R.id.textView_up_booktext)).setText(story.getUserStory());
            ((TextView) findViewById(R.id.textView_up_booktitle)).setText(story.getTitle());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_page, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
