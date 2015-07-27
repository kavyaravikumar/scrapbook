package com.cameraapp.upendra.cameraapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.gridviewimagesdemo.R;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class MongoCRUDActivity extends Activity {

    private MongoClientURI uri = null;
    private MongoClient client = null;
    private DB db = null;
    private DBCollection collection = null;
    private ArrayList<String> message = null;

    public MongoCRUDActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        message = intent.getStringArrayListExtra(MainCameraActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message.get(0));
        setContentView(textView);

        try {

            uri = new MongoClientURI("mongodb://upensabnis:passw0rd@ds047468.mongolab.com:47468/scrapbook");
            client = new MongoClient(uri);
            db = client.getDB(uri.getDatabase());
            collection = db.getCollection("mynewcollection");

            String newFileName = "mongo-java-image";


            //File imageFile = new File("C:\\Upendra\\Softwares\\Editors\\workspaces\\netbeans-7.1.2\\MongoDBMaven\\resources\\server-density-and-mongodb.jpg");
            File imageFile = new File(message.get(0));


            // create a "photo" namespace
            GridFS gfsPhoto = new GridFS(db, "photo");

            // get image file from local drive
            GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);

            // set a new filename for identify purpose
            gfsFile.setFilename(newFileName);

            // save the image file into mongoDB
            gfsFile.save();

            // print the result
            DBCursor cursor = gfsPhoto.getFileList();
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }

            /*
            // get image file by it's filename
            GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);

            // save it into a new image file
            imageForOutput.writeTo("@drawable/JavaWebHostingNew.png");

            // remove the image file from mongoDB
            gfsPhoto.remove(gfsPhoto.findOne(newFileName));
            */
            System.out.println("Done");

        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mongo_crud, menu);
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
