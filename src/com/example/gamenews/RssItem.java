package com.example.gamenews;

public class RssItem {
	 
    private final String title;
    private final String link;
 
    public RssItem(String title, String link) {
        this.title = title;
        this.link = link;
    }
 
    public String getTitle() {
        return title;
    }
 
    public String getLink() {
        return link;
    }
}
