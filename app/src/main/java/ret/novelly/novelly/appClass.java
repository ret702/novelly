package ret.novelly.novelly;

import android.app.Application;
import android.os.AsyncTask;
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
    protected static String userID = UUID.randomUUID().toString();

    @Override
    public void onCreate() {
        super.onCreate();



// Might use might not, idk
//
//        //Async Stuff
//        String result="";
//        SntpClient.GetNTPAsynctask ntpInstance = new SntpClient.GetNTPAsynctask();
//        try {
//            result = ntpInstance.execute().get();
//        } catch (Exception e) {
//
//        }
//        //get epoch time
//        Calendar date =  Calendar.getInstance();
//        int timeZoneOffset= 25200000;
//        date.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
//        date.setTimeInMillis(Long.parseLong(result) - (timeZoneOffset));




        //Parse Stuff
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "8rw0sGCaHLlMEOdD4wPK3youSyvnxk0ZFYkjDbRe", "1D8GpLW324cI2Fgn0htFuSHgCWcHqtlQcNnBq8EB");






    }
}