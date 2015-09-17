package ret.novelly.novelly;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;


public class submitStory extends AppCompatActivity {

    private int storyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_page);

        Bundle extra =getIntent().getExtras();
       // extra.getInt("storyID");
        Log.v("storyID", Integer.toString(storyID));
        final UUID storyID=UUID.randomUUID();
        final database db = new database(getApplicationContext());
        db.getWritableDatabase();

        Button subButton = (Button) findViewById(R.id.button_submitstory);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Story story = new Story();
                String userText;
                userText=   (((EditText) findViewById(R.id.storyTextbox)).getText()).toString();
                story.setUserStory(userText);
                story.setID(storyID);
                Log.e("test", userText);
                db.addStory(story);
                db.close();

            }
        });
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
