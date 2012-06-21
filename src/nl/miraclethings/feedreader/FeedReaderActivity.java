package nl.miraclethings.feedreader;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FeedReaderActivity extends Activity {
    private static final String TAG = "FeedReaderActivity";
	private static final String FEED_URL = "http://atos.net/en-us/newsroom/en-us/rss/Atos_All_PressReleases.xml";
	private FeedItemCollector collector;
	private ListView list;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.v(TAG, "FeedReader starting...");
        
        this.collector = new FeedItemCollector(FEED_URL);

        this.list = (ListView) findViewById(R.id.itemlist);
    	this.list.setOnItemClickListener(new OnItemClickListener	() {
    		@Override
    		public void onItemClick(AdapterView<?> arg0, View listitemview,
    				int arg2, long arg3) {
    			FeedReaderActivity.this.launchFeedItem( (FeedItem)listitemview.getTag() );
    		}
    	});

    	reloadFeed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menuReload:
        	reloadFeed();
        	return true;
        	
        case R.id.menuAbout:
        	showAboutDialog();
        	return true;
        }
        return false;
    }
    
	private void showAboutDialog() {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	/**
	 * Reloads the feed. Replaces all the items in the list.
	 */
	private void reloadFeed() {
		new ReloadFeedTask().execute();
	}

	protected void launchFeedItem(FeedItem item) {
		Intent intent = new Intent( Intent.ACTION_VIEW );
		intent.setData(Uri.parse(item.link));
		startActivity(intent);
	}
    
	class ReloadFeedTask extends AsyncTask<Void, Void, List<FeedItem>>
	{

		@Override
		protected List<FeedItem> doInBackground(Void... params) {

			try {
	        	List<FeedItem> results = collector.collect();
	        	Log.v(TAG, "Number of items retrieved: " + results.size());
	        	return results;
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return null;
	        }
		}
	
		@Override
		protected void onPostExecute(List<FeedItem> results) {
			if (results == null) {
				Toast.makeText(FeedReaderActivity.this, "Oeps, er ging iets fout.", 
						Toast.LENGTH_LONG).show();
				return;
			}
            FeedItem resultArray[] = new FeedItem[results.size()];
        	list.setAdapter(new FeedAdapter(FeedReaderActivity.this, results.toArray(resultArray)));
        	Toast.makeText(FeedReaderActivity.this, "Items geladen: " + results.size(), Toast.LENGTH_SHORT).show();
		}
	}
}









