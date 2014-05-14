package com.example.gamenews;

import java.util.ArrayList;

import com.aphidmobile.flip.FlipViewController;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(SelectorActivity.EXTRA_MESSAGE);
	    
	    ArrayList<String> notes = new ArrayList<String>();
	    notes.add(message);
	    notes.add("Come");
	    notes.add("On");
	    notes.add("Flip");
	    notes.add("Me");
	        
	    //You can also use FlipViewController.VERTICAL
	    FlipViewController flipView = new FlipViewController(this, FlipViewController.VERTICAL);
	        
	    //We're creating a NoteViewAdapter instance, by passing in the current context and the
	    //values to display after each flip
	    flipView.setAdapter(new NoteViewAdapter(this, notes));
	        
	    setContentView(flipView);
	}

	public class NoteViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;        
        private ArrayList<String> notes;

        public NoteViewAdapter(Context currentContext, ArrayList<String> allNotes) {
            inflater = LayoutInflater.from(currentContext);        
            notes = allNotes;
        }

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = convertView;

            if (convertView == null) {
                layout = inflater.inflate(R.layout.activity_main, null);
            }            

            //Get's value from our ArrayList by the position
            String note = notes.get(position);

            TextView tView = (TextView) layout.findViewById(R.id.note);

            tView.setText(note);
            return layout;
        }
    }
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	*/
	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	*/

}
