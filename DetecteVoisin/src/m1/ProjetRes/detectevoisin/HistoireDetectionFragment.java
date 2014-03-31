package m1.ProjetRes.detectevoisin;

import java.util.ArrayList;
import java.util.List;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HistoireDetectionFragment extends ListFragment{
	private List<DetailHistoire> histoires = new ArrayList<DetailHistoire>();
	ProgressDialog progressDialog = null;
	private View mContentView=null;
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		this.setListAdapter(new HistoireDetectionListAdapter(getActivity(),R.layout.histoire_detecte_liste,histoires));
	}
	
	private class HistoireDetectionListAdapter extends ArrayAdapter<DetailHistoire>{
		private List<DetailHistoire> histoires;
		public HistoireDetectionListAdapter(Context context, int textViewResourceId,
				List<DetailHistoire> objects){
			super(context, textViewResourceId, objects);
			this.histoires=objects;		
		}
		public View getView(int position, View convertView, ViewGroup parent){
    		View v = convertView;
			if (v == null)
			{
				LayoutInflater vi = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.detail_histoire, null);
			}
			DetailHistoire detailHistoire = histoires.get(position);
			if (detailHistoire != null)
			{
				TextView top = (TextView) v.findViewById(R.id.name_device_histoire);
				TextView middle = (TextView)v.findViewById(R.id.date);
				TextView bottom = (TextView) v
						.findViewById(R.id.mac_device_histoire);
				if (top != null)
				{
					top.setText(detailHistoire.getName());
				}
				if (bottom != null)
				{
					bottom.setText(detailHistoire.getMac());
				}
				if(middle != null)
				{
					middle.setText(detailHistoire.getDate());
				}
			}

			return v;
    	}
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mContentView = inflater.inflate(R.layout.histoire_detecte_liste, null);
		return mContentView;
	}
	
	public void addHistoire(List<DetailHistoire> histoireList)
	{
	    histoires.clear();
		histoires.addAll(histoireList);
		((HistoireDetectionListAdapter) getListAdapter()).notifyDataSetChanged();
		if (histoires.size() == 0)
		{
			Log.d(VoisinDetectionActivity.TAG, "No devices found");
			return;
		}

	}
	
}
