package ret.novelly.novelly;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class UserPage extends Activity {
    String userID = "";
    String pasteID = "";
    String storyID = "";
    boolean isWinner = false;
    boolean isPaste = false;
    boolean isStory = false;
    Pastes paste;
    Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        Bundle extra = getIntent().getExtras();
        database db = new database();
        userID = appClass.userID;

        try {
            if ((extra.getString("pasteID") != null)) {
                storyID = extra.getString("storyID");
                pasteID = extra.getString("pasteID");
                paste = new Pastes();
                paste = db.getPaste(pasteID);
                isPaste = true;
                display("paste",true);
            } else if ((extra.getString("storyID") != null)) {
                storyID = extra.getString("storyID");
                story = new Story();
                story = db.getStory(storyID);
                isStory = true;
                display("story",false);
            }

        } catch (Exception e) {
        }
        //show checkbox if the paste/story is a winner

    }


    protected void display(String indentifier, boolean winner) {
        if (isWinner) {
            CheckBox CB_winner = ((CheckBox) findViewById(R.id.checkBox_winner));
            CB_winner.setVisibility(View.INVISIBLE);
        }
        if (indentifier == "story") {
            ((TextView) findViewById(R.id.textView_up_booktext)).setText(story.getUserStory());
            ((TextView) findViewById(R.id.textView_up_booktitle)).setText(story.getTitle());
        } else if (indentifier == "paste") {
            ((TextView) findViewById(R.id.textView_up_booktext)).setText(paste.getUserPaste());
            ((TextView) findViewById(R.id.textView_up_booktitle)).setText(paste.getTitle());

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
