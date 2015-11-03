package ret.novelly.novelly;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewPastes extends BaseActivity {
    ListView pastes;
    database db;
    String storyID;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pastes = (ListView) findViewById(R.id.mainlistview);
        db = new database(pastes, this);
        Bundle extra = getIntent().getExtras();
        storyID = extra.getString("storyID");
        userID = appClass.userID;
// param 1 getter column, param 2 id to search for
        db.execute("pastes", storyID);
        navigate();


    }

    public void navigate() {
        pastes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int offset = 1;
                Intent gotoStory = new Intent(ViewPastes.this, ViewStoryClass.class);
                gotoStory.putExtra("pasteID",view.getTag().toString() );
                gotoStory.putExtra("storyID", storyID);
                startActivity(gotoStory);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pastes, menu);
        return super.onCreateOptionsMenu(menu);
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
