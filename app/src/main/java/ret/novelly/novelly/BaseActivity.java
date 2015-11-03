package ret.novelly.novelly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity {
    // need a boolean to check if we need to change icon so we aren't re-setting the icon every call


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String path = getApplicationInfo().dataDir + "/thumbs/";
        //TODO:dynamically set avatar here
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);

        Bitmap thumbnail = BitmapFactory.decodeFile(path + "thumb.png");
        MenuItem item = menu.findItem(R.id.btnUserpage);
        if (thumbnail != null) {
            BitmapDrawable icon = new BitmapDrawable(thumbnail);
            item.setIcon(icon);
        }

        return super.onCreateOptionsMenu(menu);
    }

    public void goHome(MenuItem item) {
        if (item.getItemId() == R.id.btnHome) {
            startActivity((new Intent(BaseActivity.this, MainActivity.class)));
        }
    }

    public void goUser(MenuItem item) {
        if (item.getItemId() == R.id.btnUserpage) {
            startActivity((new Intent(BaseActivity.this, UserPage.class)));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the User's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
