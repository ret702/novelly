package ret.novelly.novelly;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class ViewStoryClass extends Activity {
    private String storyID;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstory);

        Bundle extra = getIntent().getExtras();
        database db = new database(getApplicationContext());

        db.getWritableDatabase();

        userID = appClass.userID;


        String pasteID;
        Pastes paste;
        Button pasteItBtn = new Button(this);

        try {
            if ((extra.getString("storyID") != null) && (extra.getString("pasteID") == null)) {
                storyID = extra.getString("storyID");
                ((TextView) findViewById(R.id.viewuserstory)).setText(db.getStory(storyID).getUserStory());

            } else if ((extra.getString("pasteID") != null) && (extra.getString("storyID") == null)) {

                pasteID = extra.getString("pasteID");
                paste = db.getPaste(pasteID);

                ((TextView) findViewById(R.id.viewuserstory)).setText(paste.getUserPaste());
                pasteItBtn = ((Button) findViewById(R.id.VS_btn_pasteit));
                pasteItBtn.setVisibility(View.VISIBLE);

                pasteItBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ViewStoryClass.this, "Vote Submitted!", Toast.LENGTH_LONG).show();

                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                startActivity((new Intent(ViewStoryClass.this, ViewPastes.class)).putExtra("storyID", storyID));
                            }
                        }, 1500);
                    }
                });
            }

        } catch (Exception e) {
            Log.v("error", e.getMessage());
        }



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
            (new database(getApplicationContext())).deleteTable("Pastes");
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
