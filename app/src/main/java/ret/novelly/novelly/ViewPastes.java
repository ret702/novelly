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

public class ViewPastes extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pastes);

        ListView pastes = (ListView) findViewById(R.id.listView_PastesView);
        final database db = new database(pastes,this);
        Bundle extra = getIntent().getExtras();
        final String storyID = extra.getString("storyID");
        final String userID = appClass.userID;
        ArrayList<String> item = new ArrayList<String>();
        final HashMap pasteID = new HashMap();
        final ArrayAdapter<String> adaptor;



        List<Pastes> pasteArr = db.getAllPastes(storyID);
        int numOfPaste = pasteArr.size();
        for (int i = 0; i < numOfPaste; i++) {
            item.add(pasteArr.get(i).getTitle());
            pasteID.put(Integer.toString(item.size()), pasteArr.iterator().next().getID());
        }

        adaptor = new ArrayAdapter<String>(getApplicationContext(), R.layout.mainlisttextbox, item);
        pastes.setAdapter(adaptor);
        pastes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int offset = 1;
                Intent gotoStory = new Intent(ViewPastes.this, ViewStoryClass.class);
                gotoStory.putExtra("pasteID", (pasteID.get(Integer.toString(position + offset))).toString());
                gotoStory.putExtra("storyID", storyID);
                startActivity(gotoStory);
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pastes, menu);
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
