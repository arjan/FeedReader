package nl.miraclethings.feedreader;

public class FeedItem {

	public String title;
	public String date;
	public String body;
	public String link;

	public FeedItem(String title, String date, String body, String link) {
		this.title = title;
		this.date = date;
		this.body = body;
		this.link = link;
	}

}
