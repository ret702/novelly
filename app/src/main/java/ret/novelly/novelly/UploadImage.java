package ret.novelly.novelly;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class UploadImage extends Activity {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private MenuItem item;
    final private int RESULT_ACCESS = 1;
    Bitmap originalImage;
    static String thumbPath;
    boolean invalid = false;

    Bitmap avatarThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_ACCESS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_ACCESS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Create intent to Open Image applications like Gallery, Google Photos
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // Start the Intent
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Thread t = new Thread(new Runnable() {
                public void run() {

                    // When an Image is picked
                    if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                            && null != data) {
                        // Get the Image from data
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        // Get the cursor
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgDecodableString = cursor.getString(columnIndex);
                        cursor.close();

                        //create thumb
                        avatarThumb = ThumbnailUtils.extractThumbnail(BitmapFactory
                                .decodeFile(imgDecodableString), 150, 150);

                        //path to sd
                        thumbPath= getApplicationInfo().dataDir + "/thumbs/";

                        File file = new File(thumbPath, "thumb.png");
                        FileOutputStream fOut = null;
                        //if thumb already exists delete it and create a new one
                        try {
                            if (file.exists()) {
                                file.delete();
                                file.createNewFile();
                                fOut = new FileOutputStream(file);
                                //compress images to png
                                avatarThumb.compress(Bitmap.CompressFormat.PNG, 0, fOut);

                                //byte[] b = baos.toByteArray();
                                //save data to parse
                                //database db = new database();
                                //db.saveAvatar(b);

                                fOut.flush();
                                fOut.close();

                            } else {
                                file.getParentFile().mkdirs();
                                file.createNewFile();
                                fOut = new FileOutputStream(file);
                                avatarThumb.compress(Bitmap.CompressFormat.PNG, 0, fOut);
                                fOut.flush();
                                fOut.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        refreshActionBarMenu(UploadImage.this);

                        //close streams
//                        try {
//                            baos.flush();
//                            baos.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        setResult(RESULT_OK);
                        finish();

                    } else {
                        Toast.makeText(UploadImage.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                }
            });
            t.start();
        } catch (Exception e) {

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            finish();
        }
    }



    @Override
    public void invalidateOptionsMenu() {
        invalid = true;
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    static void refreshActionBarMenu(Activity activity) {
        activity.invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return super.onPrepareOptionsMenu(menu);
    }

}
