package nl.miraclethings.feedreader;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import android.util.Log;

public class FeedItemCollector {
	
	private static final String TAG = "FeedItemCollector";
	private String url;
	
	public FeedItemCollector(String url) {
		this.url = url;
	}
	
	/**
	 * This retrieves the URL.
	 * 
	 * @return a list of feed items
	 */
	public List<FeedItem> collect() throws Exception {
		
		List<FeedItem> result = new ArrayList<FeedItem>();
		 
		String html = Jsoup.connect(this.url).timeout(600000).execute().body();
		Document doc = Jsoup.parse(html, "", Parser.xmlParser());
		
		Elements items = doc.select("item");

		Log.v(TAG, "Ik heb nu zoveel items: " + items.size());
		
		for (int i=0; i<items.size(); i++) {
			Element item = items.get(i);
			String title = item.select("title").text();
			String date = item.select("dc|date").text();
			String body = item.select("description").text();
			// strip out the HTML tags
			body = Jsoup.parse(body).text().substring(0, 100) + "...";
			
			String link = item.select("link").text();
			
			Log.v(TAG, link);
			
			result.add(new FeedItem(title, date, body, link));
		}
		
		return result;
	}
}










