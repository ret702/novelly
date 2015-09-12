package ret.novelly.novelly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewStoryClass extends AppCompatActivity {
    private int position;
    private int storyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstory);
        Bundle extra =getIntent().getExtras();
         position=  extra.getInt("position");
        database db = new database(getApplicationContext());
        storyID=  db.getStory((position)).getID();
        ((TextView)findViewById(R.id.viewuserstory)).setText(db.getStory((position)).getUserStory());

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
        }
        else if (id== R.id.editstory)
        {
            Intent intent = new Intent(ViewStoryClass.this, submitStory.class );
            intent.putExtra("storyID", storyID);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
