package ret.novelly.novelly;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;


public class submitStory extends Activity {

    private String storyID;
    private boolean isPaste;
    private boolean isStory;
    private String pasteID;
    final String userID = appClass.userID;
    String userText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submittory);

        Bundle extra = getIntent().getExtras();

        try {
            //TODO: Convert to extra.containsKey()
            if (extra.getString("storyID") != null) {
                storyID = extra.getString("storyID");
                isPaste = true;
                isStory=false;
            } else {
                isPaste = false;
                isStory=true;
            }

        } catch (Exception e) {
            if (e.getClass() == NullPointerException.class) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
            }
        }


        Button subButton = (Button) findViewById(R.id.button_submitstory);
        subButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             database db = new database(getApplicationContext());
                                             db.getWritableDatabase();
                                             //get story/paste
                                             userText = (((EditText) findViewById(R.id.storyTextbox)).getText()).toString();
                                             //get title
                                             String title = ((EditText) findViewById(R.id.editText_Title)).getText().toString();
                                             if (isPaste) {
                                                 if (!(title.length() == 0)) {
                                                     Pastes paste = new Pastes(UUID.randomUUID().toString(),storyID,userID,userText,title);
                                                     db.addPaste(paste);
                                                     storyNavigate(pasteID, pasteID);
                                                 } else {
                                                     Toast.makeText(submitStory.this, "Please Enter A Title", Toast.LENGTH_SHORT).show();
                                                 }
                                             } else if(isStory) {
                                                 //if title not empty
                                                 //TODO: add textview validation i.e !=empty
                                                 if (!(title.length() == 0)) {
                                                     Story story = new Story(UUID.randomUUID().toString(),userID,userText,title);
                                                     db.addStory(story);
                                                     storyNavigate(storyID, storyID);
                                                 } else {
                                                     Toast.makeText(submitStory.this, "Please Enter A Title", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         }
                                     }
        );
    }

    protected void storyNavigate(final String column, final String ID) {

        Toast.makeText(submitStory.this, "Story Submitted!", Toast.LENGTH_LONG).show();

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                startActivity((new Intent(submitStory.this, UserPage.class)).putExtra("userID", userID).putExtra(column, ID));
            }
        }, 1500);


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
