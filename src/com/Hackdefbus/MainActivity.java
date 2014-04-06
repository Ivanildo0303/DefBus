	package com.Hackdefbus;

	 
	import java.util.ArrayList;
	import java.util.HashMap;
	import org.json.JSONArray;
	import org.json.JSONException;
	import org.json.JSONObject;
	import android.os.AsyncTask;
	import android.os.Bundle;
	import android.app.Activity;
	import android.app.ListActivity;
	import android.app.ProgressDialog;
	import android.content.Context;
	import android.content.Intent;
	import android.view.Menu;
	import android.view.View;
	import android.widget.AdapterView;
	import android.widget.ListAdapter;
	import android.widget.ListView;
	import android.widget.SimpleAdapter;
	import android.widget.TextView;
	import android.widget.Toast;
	import android.widget.AdapterView.OnItemClickListener;
	 
	public class MainActivity extends ListActivity {
	    private Context context;
	    private static String url = "http://api.olhovivo.sptrans.com.br/v0/Login/Autenticar?token=bda414f9197314b56f15f921c3650dce2b6f44ab259d6cf0436e4f6a89d72cae";
	 
	    private static final String VTYPE = "Type";
	    private static final String VCOLOR = "Color";
	    private static final String FUEL = "Fuel";
	    private static final String TREAD = "Tread";
	     
	    ArrayList<HashMap<String, String >> jsonlist = new ArrayList<HashMap<String, String>>();
	     
	    ListView lv ;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        new ProgressTask(MainActivity.this).execute();
	    }
	 
	    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
	        private ProgressDialog dialog;
	 
	        private ListActivity activity;
	 
	        // private List<Message> messages;
	        public ProgressTask(ListActivity activity) {
	            this.activity = activity;
	            context = activity;
	            dialog = new ProgressDialog(context);
	        }
	 
	        private Context context;
	 
	        protected void onPreExecute() {
	            this.dialog.setMessage("Progress start");
	            this.dialog.show();
	        }
	 
	        @Override
	        protected void onPostExecute  (final Boolean success) {
	            if (dialog.isShowing()) {
	                dialog.dismiss();
	            }
	            ListAdapter adapter = new SimpleAdapter(context, jsonlist,
	                    R.layout.list_item, new String[] { VTYPE, VCOLOR,
	                            FUEL, TREAD }, new int[] {
	                            R.id.vehicleType, R.id.vehicleColor, R.id.fuel,
	                            R.id.treadType });
	 
	            setListAdapter(adapter);
	 
	            // select single ListView item
	             lv = getListView();
	        }
	 
	        protected Boolean doInBackground(final String... args) {
	 
	            JSONParser jParser = new JSONParser();
	 
	            // get JSON data from URL
	            JSONArray json = jParser.getJSONFromUrl(url);
	 
	            for (int i = 0; i < json.length(); i++) {
	 
	                try {
	                    JSONObject c = json.getJSONObject(i);
	                    String vtype = c.getString(VTYPE);
	 
	                    String vcolor = c.getString(VCOLOR);
	                    String vfuel = c.getString(FUEL);
	                    String vtread = c.getString(TREAD);
	 
	                    HashMap<String, String> map = new HashMap<String, String>();
	 
	                    // Add child node to HashMap key & value
	                    map.put(VTYPE, vtype);
	                    map.put(VCOLOR, vcolor);
	                    map.put(FUEL, vfuel);
	                    map.put(TREAD, vtread);
	                    jsonlist.add(map);
	                }
	                catch (JSONException e) {
	                    e.printStackTrace();
	                }
	            }
	            return null;
	        }
	    }
	}


