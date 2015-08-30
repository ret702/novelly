package ret.novelly.novelly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //load stories /imagesview dynamically

      final ListView userStories = (ListView) findViewById(R.id.mainlistview);

        ArrayList<String> item = new ArrayList<String>();

        final ArrayAdapter<String> adaptor;
        database db = new database(getApplicationContext());

        for(int i=1; i<= db.getAllStorys().size(); i++)
        {
            item.add(db.getStory(i).getUserStory());
        }
        adaptor= new ArrayAdapter<String>(getApplicationContext(),R.layout.mainpagelayout,item);
        userStories.setAdapter(adaptor);
        userStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int offSet=1;
                Intent gotoStory = new Intent(MainActivity.this, ViewStoryClass.class);
                gotoStory.putExtra("position", (position+offSet));
                startActivity(gotoStory);
            }
        });
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
        }

        return super.onOptionsItemSelected(item);
    }
}
