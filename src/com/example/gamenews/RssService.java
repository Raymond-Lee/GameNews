package com.example.gamenews;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class RssService extends IntentService {
	private static String RSS_LINK = "http://www.pcworld.com/index.rss";
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    public static final String URL = "url";
 
    public RssService() {
        super("RssService");
    }
 
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("RssApp", "Service started");
        setRssLink(intent.getStringExtra(URL));
        List<RssItem> rssItems = null;
        try {
            PcWorldRssParser parser = new PcWorldRssParser();
            rssItems = parser.parse(getInputStream(RSS_LINK));
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }
 
    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w("RssApp", "Exception while retrieving the input stream", e);
            return null;
        }
    }
    
    private void setRssLink(String url) {
    	RSS_LINK = url;
    }
}