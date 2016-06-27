package com.ciplamed.ciplamedlanding;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;




public class DownloadPDF extends Activity {
	
	String url,pname;
	File file;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
           // setContentView(R.layout.activity_main);
            //String extStorageDirectory = Environment.getExternalStorageDirectory()
           // .toString();
            Bundle extras=getIntent().getExtras();
            url=extras.getString("fullpi").replace(" ", "%20");
            pname=extras.getString("prodname").replace(" ", "_");
            File folder = new File("/sdcard/download", "pdf");
            folder.mkdir();
           file = new File(folder, pname+".pdf");
            try {
                    file.createNewFile();
            } catch (IOException e1) {
                    e1.printStackTrace();
            }
            //Downloader.DownloadFile("http://ciplamed.com/"+url, file);
            DownloadFile downloadFile = new DownloadFile();
            downloadFile.execute("http://ciplamed.com/"+url);
    
            
    }
    public void showPdf()
        {
            File file = new File("/sdcard/download/pdf/"+pname+".pdf");
            PackageManager packageManager = getPackageManager();
            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            testIntent.setType("application/pdf");
            List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        }

    class DownloadFile extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... sUrl) {
            try {
                URL url = new URL(sUrl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // this will be useful so that you can show a typical 0-100% progress bar
                int fileLength = connection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return "finished";
        }
        ProgressDialog progressBar;
    	
    	protected void onPreExecute(){
    	
    		progressBar = new ProgressDialog(DownloadPDF.this);
    		progressBar.setCancelable(true);
    		progressBar.setMessage("Download PDF...");
    		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		progressBar.setMax(100);
    		progressBar.show();
    	
    		
    	}
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressBar.setProgress(progress[0]);
        }
        
        protected void onPostExecute(String result){
    		progressBar.dismiss();
    		Log.d("Anish", "In POst Execute");
    		showPdf();
    		
    	}
    }
}

