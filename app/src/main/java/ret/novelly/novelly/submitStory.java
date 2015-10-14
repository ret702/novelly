package ret.novelly.novelly;


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


public class submitStory extends AppCompatActivity {

    private UUID storyID;
    private boolean isPaste;
    private UUID pasteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitStory);

        Bundle extra = getIntent().getExtras();
        final String userID = extra.getString("userID");
        try {
            storyID = UUID.fromString(extra.getString("storyID"));
            isPaste = true;
            pasteID = UUID.randomUUID();

        } catch (Exception e) {
            if (e.getClass() == NullPointerException.class) {
                isPaste = false;
                UUID storyID = UUID.randomUUID();
            }
        }


        final database db = new database(getApplicationContext());
        db.getWritableDatabase();

        Button subButton = (Button) findViewById(R.id.button_submitstory);
        subButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             String userText;
                                             userText = (((EditText) findViewById(R.id.storyTextbox)).getText()).toString();

                                             if (isPaste) {
                                                 db.addPaste(storyID, pasteID, UUID.fromString(userID), userText);

                                                 Toast.makeText(submitStory.this, "Paste Submitted!", Toast.LENGTH_LONG).show();

                                                 Handler mHandler = new Handler();
                                                 mHandler.postDelayed(new Runnable() {
                                                     public void run() {
                                                         startActivity((new Intent(submitStory.this, UserPage.class)).putExtra("userID", userID));
                                                     }
                                                 }, 1500);
                                             }
                                             else
                                             {
                                                 if (!(((EditText) findViewById(R.id.editText_Title)).toString().isEmpty())) {
                                                     Story story = new Story();
                                                     story.setUserStory(userText);
                                                     story.setID(storyID);
                                                     story.setTitle(((EditText) findViewById(R.id.editText_Title)).toString());
                                                     db.addStory(story);
                                                     db.close();
                                                     startActivity((new Intent(submitStory.this, UserPage.class)).putExtra("userID", userID));
                                                 }
                                                 else
                                                 {
                                                     Toast.makeText(submitStory.this, "Please Enter A Title", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         }
                                     }

        );
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
