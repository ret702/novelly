package ret.novelly.novelly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {
    String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extra = getIntent().getExtras();
        userID = appClass.userID;



        final ListView userStories = (ListView) findViewById(R.id.mainlistview);

        ArrayList<String> item = new ArrayList<String>();
        final HashMap storyIDs = new HashMap();
        final ArrayAdapter<String> adaptor;
        final database db = new database(getApplicationContext());

        db.getWritableDatabase();

        if (db.isEmpty("Storys") != true) {
            for (int i = 0; i < db.getAllStorys().size(); i++) {
                item.add(db.getAllStorys().get(i).getTitle());
                storyIDs.put(Integer.toString(item.size()), db.getAllStorys().get(i).getID());
            }
            adaptor = new ArrayAdapter<String>(getApplicationContext(), R.layout.mainlistviewtextbox, item);

            userStories.setAdapter(adaptor);
            userStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int offset = 1;
                    Intent gotoStory = new Intent(MainActivity.this, ViewStoryClass.class);

                    if (db.isEmpty("Storys") != true) {
                        gotoStory.putExtra("storyID", (storyIDs.get(Integer.toString(position + offset))).toString());
                        gotoStory.putExtra("userID", userID);
                    }
                    startActivity(gotoStory);
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == R.id.storycreate) {
            Intent intent = new Intent(MainActivity.this, submitStory.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        } else if (id == R.id.deleteall) {
            database db = new database(getApplicationContext());
            db.clearDB();
            recreate();
        } else if (id == R.id.deleteDB) {
            database db = new database(getApplicationContext());
            db.deleteDB(getApplicationContext());
            recreate();
        }

        return super.onOptionsItemSelected(item);
    }
}
