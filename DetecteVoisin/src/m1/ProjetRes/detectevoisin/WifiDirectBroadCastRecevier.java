package m1.ProjetRes.detectevoisin;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Build;
import android.util.Log;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class WifiDirectBroadCastRecevier extends BroadcastReceiver {
	
	//attribut
	private WifiP2pManager manager;
	private Channel channel;
	private VoisinDetectionActivity activity;
	
	//constructeur
	public WifiDirectBroadCastRecevier(WifiP2pManager manager,Channel channel,VoisinDetectionActivity activity){
		super();
		this.manager=manager;
		this.channel=channel;
		this.activity=activity;
	}

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action=intent.getAction();
		if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
		{

			// UI update to indicate wifi p2p status.
			int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
			if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
			{
				// Wifi Direct mode is enabled
				activity.setIsWifiP2pEnabled(true);
			} else
			{
				activity.setIsWifiP2pEnabled(false);
				activity.resetData();

			}
			Log.d(VoisinDetectionActivity.TAG, "P2P state changed - " + state);
		} else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
		{

			// request available peers from the wifi p2p manager. This is an
			// asynchronous call and the calling activity is notified with a
			// callback on PeerListListener.onPeersAvailable()
			if (manager != null)
			{
				manager.requestPeers(channel, (PeerListListener) activity
						.getFragmentManager().findFragmentById(m1.ProjetRes.detectevoisin.R.id.frag_liste));
			}
			Log.d(VoisinDetectionActivity.TAG, "P2P peers changed");
		} 
	}
	}
	

