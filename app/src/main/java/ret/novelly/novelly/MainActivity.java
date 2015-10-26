package ret.novelly.novelly;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {
    String userID = "";
    static ArrayList<String> items = new ArrayList<String>();
    HashMap storyIDs = new HashMap();
    ListView userStories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userStories= (ListView) findViewById(R.id.mainlistview);
        userID = appClass.userID;

        database db = new database(userStories,this);

        db.execute("getstories");





    }



    public void display() {

        userStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           View test= getSelectedView ();

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int offset = 1;
                Intent gotoStory = new Intent(MainActivity.this, ViewStoryClass.class);
                gotoStory.putExtra("storyID",test.);
                gotoStory.putExtra("userID", userID);
                startActivity(gotoStory);
            }
        });
    }

    public void onResume() {
        super.onResume();

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

        } else if (id == R.id.deleteDB) {

        }

        return super.onOptionsItemSelected(item);
    }


}
