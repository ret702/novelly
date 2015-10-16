package ret.novelly.novelly;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class UserPage extends Activity {
    String userID = "";
    String pasteID="";
    String storyID="";
    boolean isWinner=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        Bundle extra = getIntent().getExtras();
        userID = extra.getString("userID");
        storyID= extra.getString("storyID");
        pasteID= extra.getString("pasteID");
        database db = new database(this);
       Pastes paste=  db.getPaste(pasteID);


        if(isWinner)
        {

        }
        else{
         CheckBox CB_winner= ((CheckBox) findViewById(R.id.checkBox_winner));
            CB_winner.setVisibility(View.INVISIBLE);
        }
        ((TextView) findViewById(R.id.textView_up_booktext)).setText(paste.getUserPaste());
        ((TextView) findViewById(R.id.textView_up_booktitle)).setText(paste.getTitle());
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
