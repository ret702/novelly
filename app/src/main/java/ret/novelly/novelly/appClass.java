package ret.novelly.novelly;

import android.app.Application;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.google.android.gms.ads.doubleclick.CustomRenderedAd;
import com.parse.Parse;
import com.parse.ParseObject;

import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class appClass extends Application {
    protected static String userID = "4f1b3acd-23a7-44fb-9b77-1bce8bd4336d";
    static Typeface font;
    @Override
    public void onCreate() {
        super.onCreate();



        //Parse Stuff
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "8rw0sGCaHLlMEOdD4wPK3youSyvnxk0ZFYkjDbRe", "1D8GpLW324cI2Fgn0htFuSHgCWcHqtlQcNnBq8EB");




    }
}