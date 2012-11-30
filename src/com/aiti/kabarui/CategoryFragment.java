package com.aiti.kabarui;
 
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
 
public class CategoryFragment extends Fragment {
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_DURATION = "duration";
 
    ListView list;
    NewsAdapter adapter;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.category, container,false);
		 
        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
 
        // looping through all song nodes &lt;song&gt;
        for (int i = 0; i < 7; i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            // adding each child node to HashMap key =&gt; value
            map.put(KEY_ID, ""+i);
            map.put(KEY_TITLE, "Judul Berita");
            map.put(KEY_ARTIST, "Penulis" );
            map.put(KEY_DURATION, "Tanggal" );
 
            // adding HashList to ArrayList
            songsList.add(map);
        }
 
        list=(ListView)v.findViewById(R.id.list_berita);
 
        // Getting adapter by passing xml data ArrayList
        adapter=new NewsAdapter(getActivity(), songsList);
        list.setAdapter(adapter);
 
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
 
        });
        
        return v;
	}
    
    
}