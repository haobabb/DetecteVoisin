package m1.ProjetRes.detectevoisin;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class VoisinListeFragment extends ListFragment implements PeerListListener{
	private List<WifiP2pDevice> voisins = new ArrayList<WifiP2pDevice>();
	private WifiP2pDevice voisin;
	ProgressDialog progressDialog = null;
	private View mContentView=null;
	
	
	
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		this.setListAdapter(new WiFiPeerListAdapter(getActivity(),R.layout.voisin,voisins));
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mContentView = inflater.inflate(R.layout.detecte_voisins_liste, null);
		return mContentView;
	}
	
	public WifiP2pDevice getVoisin(){
		return voisin;
	}
	
/**	private static String getDeviceStatus(int deviceStatus)
	{
		Log.d("VoisinD��tection", "Peer status :" + deviceStatus);
		switch (deviceStatus)
		{
		case WifiP2pDevice.AVAILABLE:
			return "Available";
		case WifiP2pDevice.INVITED:
			return "Invited";
		case WifiP2pDevice.CONNECTED:
			return "Connected";
		case WifiP2pDevice.FAILED:
			return "Failed";
		case WifiP2pDevice.UNAVAILABLE:
			return "Unavailable";
		default:
			return "Unknown";

		}
	}**/
    private class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice>{
    	private List<WifiP2pDevice> voisins;
    	public WiFiPeerListAdapter(Context context, int textViewResourceId,
				List<WifiP2pDevice> objects) {
    		super(context, textViewResourceId, objects);
    		this.voisins=objects;
		}
    	public View getView(int position, View convertView, ViewGroup parent){
    		View v = convertView;
			if (v == null)
			{
				LayoutInflater vi = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.voisin, null);
			}
			WifiP2pDevice device = voisins.get(position);
			if (device != null)
			{
				TextView top = (TextView) v.findViewById(R.id.name_device);
				TextView bottom = (TextView) v
						.findViewById(R.id.mac_device);
				if (top != null)
				{
					top.setText(device.deviceName);
				}
				if (bottom != null)
				{
					bottom.setText(device.deviceAddress);
				}
			}

			return v;
    	}
    }
	public void viderListe() {
		// TODO Auto-generated method stub
		voisins.clear();
		((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
	}

	
	@Override
	public void onPeersAvailable(WifiP2pDeviceList peerList)
	{
		
		if (progressDialog != null && progressDialog.isShowing())
		{
			progressDialog.dismiss();
		}
		voisins.clear();
		voisins.addAll(peerList.getDeviceList());
		((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
		if (voisins.size() == 0)
		{
			Log.d(VoisinDetectionActivity.TAG, "No devices found");
			return;
		}

	}
	public void onInitiateDiscovery()
	{
		if (progressDialog != null && progressDialog.isShowing())
		{
			progressDialog.dismiss();
		}
		progressDialog = ProgressDialog.show(getActivity(),
				"Press back to cancel", "finding peers", true, true,
				new DialogInterface.OnCancelListener()
				{

					@Override
					public void onCancel(DialogInterface dialog)
					{

					}
				});
	}
	public List<WifiP2pDevice> getListeVoisins(){
	    return this.voisins;
	}
	
}
