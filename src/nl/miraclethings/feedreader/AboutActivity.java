package nl.miraclethings.feedreader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }

    public void okClick(View b) {
    	finish();
    }
}
