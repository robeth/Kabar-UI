package com.aiti.kabarui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiti.rss.RSSItem;
 
public class NewsAdapter extends BaseAdapter {
 
    private Activity activity;
    private List<RSSItem> data;
    private static LayoutInflater inflater=null;
 
    public NewsAdapter(Activity a, List<RSSItem> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        RSSItem item = data.get(position);
 
        // Setting all values in listview
        title.setText(item.getTitle());
        artist.setText(item.getDescription());
        duration.setText(item.getPubdate());
        thumb_image.setImageResource(R.drawable.ic_launcher);
        return vi;
    }
}
