package com.example.proj1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private String htmlStrings = "";
	public String summonerName = "";
	public String summonerString = "";

	TextView httpStuff;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		httpStuff = (TextView) findViewById(R.id.testHttp);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// On the "Search" button press, the app will store the name
	public void storeName(View v) {
        EditText ev = (EditText) findViewById(R.id.editText1);
        if (v.getId() == R.id.button1) {
        	summonerName = ev.getText().toString();
        	
        	try {
    			new RetrieveHTMLString().execute();
    		} catch (Exception e) {
    			httpStuff.setText("exeption caught");
    			return;
    		}
        }
    }
	
	private class RetrieveHTMLString extends AsyncTask<String, Void, String> {
	    protected String doInBackground(String... urls) {
	    	String htmlString;
	    	GetInfo test = new GetInfo();
	        try {
	        	htmlString = test.getInternetData(summonerName);
	            return htmlString;
	        } catch (Exception e) {
	            return null;
	        }
	    }
	    protected void onPostExecute(String response) {
	    	htmlStrings = response;
	    	// Parse and print data here
	    	
	    	Document doc = Jsoup.parse(htmlStrings);
	    	Elements summNames = doc.select("u");
	    	summonerString += "";
	    	for(Element e: summNames){
	    		summonerString += e.text();
	    		summonerString += "\n";
	    		
	    	}
	    	if(summonerString != ""){
	    		httpStuff.setText("The following summoners are in a game with " + summonerName +
	    				":\n\n" + summonerString);
	    		summonerString = "";
	    	}else{
	    		httpStuff.setText("The Summoner you searched for is not currently in a game.");
	    	}
	    	
	    	
	    	/*
	    	Log.d("test", "setting textView");
			httpStuff.setText(htmlStrings);
			*/
	    }
	}
}
