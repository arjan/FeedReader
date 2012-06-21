package nl.miraclethings.feedreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FeedAdapter extends ArrayAdapter<FeedItem> {

	private FeedItem[] data;
	private FeedReaderActivity context;

	public FeedAdapter(FeedReaderActivity context, FeedItem[] data)
	{
		super(context, 0, data);
		this.data = data;
		this.context = context;
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        
        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.listitemlayout, parent, false);
        
        FeedItem currentItem = this.data[position];
        
        TextView itemTitle = (TextView) view.findViewById(R.id.itemTitle);
        itemTitle.setText(currentItem.title);
        
        TextView itemBody = (TextView) view.findViewById(R.id.itemBody);
        itemBody.setText(currentItem.date + " - " + currentItem.body);

        view.setTag(currentItem);
        
        return view;
    }
	
}
