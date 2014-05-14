package com.example.gamenews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Build;

public class SimplifiedSelectorActivity extends Activity implements View.OnClickListener{
	public final static String EXTRA_MESSAGE = "com.example.gamenews.MESSAGE";
	private String promptText = "";
	private int currentViewID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplified_selector);

		findViewById(R.id.buttontop).setOnClickListener(this);
		findViewById(R.id.buttonmidleft).setOnClickListener(this);
		findViewById(R.id.buttonmidright).setOnClickListener(this);
		findViewById(R.id.buttonbotleft).setOnClickListener(this);
		findViewById(R.id.buttonbotright).setOnClickListener(this);
		
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		String defaultValue = "http://www.oneworldconsulting.com.au/images/tap.png";
		String topURL = sharedPref.getString(Integer.toString(R.id.imagetop)+"image", defaultValue);
		String midleftURL = sharedPref.getString(Integer.toString(R.id.imagemidleft)+"image", defaultValue);
		String midrightURL = sharedPref.getString(Integer.toString(R.id.imagemidright)+"image", defaultValue);
		String botleftURL = sharedPref.getString(Integer.toString(R.id.imagebotleft)+"image", defaultValue);
		String botrightURL = sharedPref.getString(Integer.toString(R.id.imagebotright)+"image", defaultValue);
		
		showImage(topURL, R.id.imagetop);
		showImage(midleftURL, R.id.imagemidleft);
		showImage(midrightURL, R.id.imagemidright);
		showImage(botleftURL, R.id.imagebotleft);
		showImage(botrightURL, R.id.imagebotright);
		
	}
	
	public void onClick(View v) {
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		String defaultValue = "";
		String feedURL = sharedPref.getString(Integer.toString(v.getId()), defaultValue);
		if (feedURL == "") {
			currentViewID = v.getId();
			if(currentViewID == R.id.buttontop) {
				currentViewID = R.id.imagetop;
			}
			if(currentViewID == R.id.buttonmidleft) {
				currentViewID = R.id.imagemidleft;
			}
			if(currentViewID == R.id.buttonmidright) {
				currentViewID = R.id.imagemidright;
			}
			if(currentViewID == R.id.buttonbotleft) {
				currentViewID = R.id.imagebotleft;
			}
			if(currentViewID == R.id.buttonbotright) {
				currentViewID = R.id.imagebotright;
			}
			register();
			//feedURL = search(v);
		} else {
			Intent intent = new Intent(this,ReaderActivity.class);
			intent.putExtra(EXTRA_MESSAGE, feedURL);
			startActivity(intent);
			finish();
		}
	}
	
	private void register(){
		/**/
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
		
		AlertDialog ad = new AlertDialog.Builder(this)
			.setTitle("Title")
			.setMessage("Please enter a game title: ")
			.setView(input)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					promptText = input.getText().toString();
					new ImageSearchTask(currentViewID).execute();
					new FeedSearchTask(currentViewID).execute();
					/*
					String feedURL = search(currentViewID);
					Intent intent = new Intent(SimplifiedSelectorActivity.this,ReaderActivity.class);
					intent.putExtra(EXTRA_MESSAGE, feedURL);
					startActivity(intent);
					finish();
					*/
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.create();
			ad.show();
		/**/
		/*
		DialogFragment register = new EntryDialogFragment();
		register.show(getFragmentManager(), "register");
		*/
	}
	/*
	private String search(int viewID) {
		String google = "http://www.google.com/imghp?as_q=";
		String rssmicro = "http://www.rssmicro.com/?q=";
		String extras = "&f=0&v=0&sd=-1&sit=t&p=1&pi=15&s=d&rst=1&lang=0";
		String charset = "UTF-8";
		String userAgent = "NoobBot";
		String title;
		String imageUrl = "";
		String url = "";
		
		try {
			Elements links = Jsoup.connect(google+URLEncoder.encode(promptText, charset))
								.userAgent(userAgent).get().select("li.g>h3>a");
			for (Element link: links) {
				title = link.text();
				imageUrl = link.absUrl("href");
				imageUrl = URLDecoder.decode(imageUrl.substring(
						imageUrl.indexOf('=') + 1, imageUrl.indexOf('&')),"UTF-8");
				
				if (!imageUrl.startsWith("http")) {
					continue;
				} else {
					break;
				}
			}
			Elements feedlinks = Jsoup.connect(rssmicro+URLEncoder.encode(promptText+extras, charset))
					.userAgent(userAgent).get().select("li.g>h3>a");
			for (Element link: feedlinks) {
				title = link.text();
				url = link.absUrl("href");
				url = URLDecoder.decode(url.substring(
						url.indexOf('=') + 1, url.indexOf('&')),"UTF-8");
				
				if (!url.startsWith("http")) {
					continue;
				} else {
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(Integer.toString(viewID)+"image", imageUrl);
		editor.putString(Integer.toString(viewID), url);
		return url;
	}
	*/
	private void showImage(String url, int id) {
		ImageView imgview = (ImageView)findViewById(id);
		imgview.setOnClickListener(this);
		new DownloadImageTask(imgview).execute(url);
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView image;
		
		public DownloadImageTask(ImageView image) {
			this.image = image;
		}
		
		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap icon = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				icon = BitmapFactory.decodeStream(in);
				in.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return icon;
		}
		
		protected void onPostExecute(Bitmap result) {
			image.setImageBitmap(result);
		}
		
	}
	
	private class FeedSearchTask extends AsyncTask<String, Void, String> {
		
		int viewID;
		
		public FeedSearchTask(int viewID){
			this.viewID = viewID;
		}
		
		protected String doInBackground(String... urls){
			String retVal = "";
			try {
				String formattedString = promptText.replace(" ", "%20");
				URL url = new URL("https://ajax.googleapis.com/ajax/services/feed/find?" +
						"v=1.0&q="+formattedString+"%20game%20rss"+"&userip=INSERT-USER-IP");
				URLConnection connection = url.openConnection();
				connection.addRequestProperty("Referer", "https://github.com/ThePermaNoob");

				String line;
				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					builder.append(line);
				}

				JSONObject json = new JSONObject(builder.toString());
				JSONArray ar = json.getJSONObject("responseData").getJSONArray("entries");
				/*
				for(int i = 0; i < ar.length(); i++) {
					
				}
				*/
				retVal = ar.getJSONObject(1).getString("url");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return retVal;
		}
		
		protected void onPostExecute(String feedurl){
			SharedPreferences sharedPref = SimplifiedSelectorActivity.this.getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(Integer.toString(viewID), feedurl);
			editor.commit();
			Intent intent = new Intent(SimplifiedSelectorActivity.this,ReaderActivity.class);
			intent.putExtra(EXTRA_MESSAGE, feedurl);
			startActivity(intent);
		}
	}
	
	private class ImageSearchTask extends AsyncTask<String, Void, String> {
		
		int viewID;
		
		public ImageSearchTask(int viewID){
			this.viewID = viewID;
		}
		
		protected String doInBackground(String... urls){
			String retVal = "";
			try {
				String formattedString = promptText.replace(" ", "%20");
				URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
						"v=1.0&q="+formattedString+"%20game"+"&userip=INSERT-USER-IP");
				URLConnection connection = url.openConnection();
				connection.addRequestProperty("Referer", "https://github.com/ThePermaNoob");

				String line;
				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					builder.append(line);
				}

				JSONObject json = new JSONObject(builder.toString());
				JSONArray ar = json.getJSONObject("responseData").getJSONArray("results");
				retVal = ar.getJSONObject(1).getString("url");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return retVal;
		}
		
		protected void onPostExecute(String imageUrl){
			SharedPreferences sharedPref = SimplifiedSelectorActivity.this.getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(Integer.toString(viewID)+"image", imageUrl);
			editor.commit();
			showImage(imageUrl, viewID);
		}
	}

}
