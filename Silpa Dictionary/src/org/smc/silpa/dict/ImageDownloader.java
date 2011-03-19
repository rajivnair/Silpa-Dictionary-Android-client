package org.smc.silpa.dict;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Faciliatates Asyncrounous download of Images from the internet.
 * 
 * Heavily borrowed from http://android-developers.blogspot.com/2010/07/multithreading-for-performance.html
 *
 */
public class ImageDownloader {

	public void download(String word, String lang, ImageView imageView) {
	

		String url = "http://silpa.org.in/Dictionary?image=y&text=" + word + "&dict=" + lang + "&imagewidth=400&imageheight=400&fontsize=22";


		BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
		task.execute(url);

	}
	
	static Bitmap downloadBitmap(String stringUrl) {
	    URL url = null;
	    HttpURLConnection connection = null;
	    InputStream inputStream = null;
	    
	    try {
	        url = new URL(stringUrl);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.setUseCaches(true);
	        inputStream = connection.getInputStream();
	        
	        return BitmapFactory.decodeStream(inputStream);
	    } catch (Exception e) {
	        //Log.w(TAG, "Error while retrieving bitmap from " + stringUrl, e);
	    } finally {
	        if (connection != null) {
	            connection.disconnect();
	        }
	    }
	    
	    return null;
	}

	class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
		private String url;
		private final WeakReference<ImageView> imageViewReference;

		public BitmapDownloaderTask(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		@Override
		// Actual download method, run in the task thread
		protected Bitmap doInBackground(String... params) {
			// params comes from the execute() call: params[0] is the url.
			return downloadBitmap(params[0]);
		}

		@Override
		// Once the image is downloaded, associates it to the imageView
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}

			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
					imageView.performClick();
				}
			}
		}
	}

}
