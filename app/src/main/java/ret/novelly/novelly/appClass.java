package ret.novelly.novelly;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;

import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.io.IOException;
import java.util.Date;

public class appClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

       //Async Stuff
        SntpClient.GetNTPAsynctask ntpInstance = new SntpClient.GetNTPAsynctask();
        String result="";
        Parse.initialize(this, "8rw0sGCaHLlMEOdD4wPK3youSyvnxk0ZFYkjDbRe", "1D8GpLW324cI2Fgn0htFuSHgCWcHqtlQcNnBq8EB");
        try {
            result = ntpInstance.execute().get();
        } catch (Exception e) {

        }
        Date currentTime = new Date(Long.parseLong(result) * 1000);




        //Parse Stuff
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject parseTime = new ParseObject("time");





    }
}