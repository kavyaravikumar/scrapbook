package com.cameraapp.upendra.cameraapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


public class SaveAsyncTask extends AsyncTask<MyContact, Void, Boolean> {

	private MongoClientURI uri = null;
	private MongoClient client = null;
	private DB db = null;
	private DBCollection collection = null;
	private ArrayList<String> message = null;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GridViewDemo/";

	@Override
	protected Boolean doInBackground(MyContact... arg0) {

		try {
			uri = new MongoClientURI("mongodb://upensabnis:passw0rd@ds047468.mongolab.com:47468/scrapbook");
			client = new MongoClient(uri);
			db = client.getDB(uri.getDatabase());
			collection = db.getCollection("mynewcollection");

			String newFileName = "mongo-java-image";
			MyContact contact = arg0[0];

/*			Iterator itr = contact.listOfImagesPath.iterator();

			while (itr.hasNext()){
				File imageFile = new File((String)itr.next());

				String imgcurTime = dateFormat.format(new Date());

				// create a "photo" namespace
				GridFS gfsPhoto = new GridFS(db, "mynewcollection");

				// get image file from local drive
				GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);

				// set a new filename for identify purpose
				gfsFile.setFilename(imgcurTime);

				// save the image file into mongoDB
				gfsFile.save();

				// print the result
				DBCursor cursor = gfsPhoto.getFileList();
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}
			}*/

			GridFS gfsPhoto = new GridFS(db, "mynewcollection");
            // get image file by it's filename
            GridFSDBFile imageForOutput = gfsPhoto.findOne("2015-07-19 21:11:50");

			String _path = GridViewDemo_ImagePath + "JavaWebHostingNew.jpg";

			// save it into a new image file
            imageForOutput.writeTo(_path);

            // remove the image file from mongoDB
            //gfsPhoto.remove(gfsPhoto.findOne("2015-07-19 21:11:50"));
			System.out.println("Done");
			return true;

		} /*catch (MongoException e) {
			e.printStackTrace();
			return false;
		}*/
		catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (MongoException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
