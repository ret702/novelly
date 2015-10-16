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
    private String pasteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submittory);

        Bundle extra = getIntent().getExtras();
        final String userID = extra.getString("userID");
        try {
            storyID = extra.getString("storyID");
            isPaste = true;
            pasteID = UUID.randomUUID().toString();

        } catch (Exception e) {
            if (e.getClass() == NullPointerException.class) {
                isPaste = false;
                String storyID = UUID.randomUUID().toString();
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
                                            String title= ((EditText) findViewById(R.id.editText_Title)).getText().toString();
                                             if (isPaste) {
                                                 if (!(title.length()==0)) {
                                                     db.addPaste(storyID, pasteID, userID, userText,title);

                                                     Toast.makeText(submitStory.this, "Paste Submitted!", Toast.LENGTH_LONG).show();

                                                     Handler mHandler = new Handler();
                                                     mHandler.postDelayed(new Runnable() {
                                                         public void run() {
                                                             startActivity((new Intent(submitStory.this, UserPage.class)).putExtra("userID", userID).putExtra("pasteID",pasteID));
                                                         }
                                                     }, 1500);

                                                 }
                                                 else
                                                 {
                                                     Toast.makeText(submitStory.this, "Please Enter A Title", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                             else
                                             {
                                                 if (!(title.length()==0)) {
                                                     Story story = new Story();
                                                     story.setUserStory(userText);
                                                     story.setID(storyID);
                                                     story.setTitle(((EditText) findViewById(R.id.editText_Title)).getText().toString());
                                                     db.addStory(story);
                                                     db.close();
                                                     startActivity((new Intent(submitStory.this, UserPage.class)).putExtra("userID", userID).putExtra("storyID",storyID));
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
