package m1.ProjetRes.detectevoisin;


import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@SuppressLint("NewApi")
public class VoisinDetectionActivity extends Activity {
	
	
	private WifiP2pManager manager;
	//private WifiP2pDevice device;
	private Channel channel;
	private WifiDirectBroadCastRecevier recevier=null;
	private final IntentFilter intentFilter= new IntentFilter();
	
	private boolean wifiDirectActive=false;
	public static final String TAG = "VoisinDetectonTest";

	private ArrayList<DetailHistoire> list = new ArrayList<DetailHistoire>();
	//Date date = new Date();
	//private DetailHistoire detailTest = new DetailHistoire(date, "testName", "testMac");
    
	  
	Handler handler = new Handler();  
    Runnable runnable = new Runnable() {  
          
        @Override  
        public void run() {  
        	Date date = new Date();
			final HistoireDetectionFragment fragmentHistoire = (HistoireDetectionFragment) getFragmentManager()
					.findFragmentById(m1.ProjetRes.detectevoisin.R.id.frag_histoire);
			final VoisinListeFragment fragment = (VoisinListeFragment) getFragmentManager()
			  .findFragmentById(m1.ProjetRes.detectevoisin.R.id.frag_liste);
			Log.d(VoisinDetectionActivity.TAG,fragment.getListeVoisins().toString());
	        //fragment.viderListe();
	        fragment.onInitiateDiscovery();
	        manager.discoverPeers(channel, new WifiP2pManager.ActionListener()
	           {
					@Override
					public void onSuccess()
					{
						Toast.makeText(VoisinDetectionActivity.this,
								"Discovery Initiated", Toast.LENGTH_SHORT).show();
					}
			
					@Override
					public void onFailure(int reasonCode)
					{
						Toast.makeText(VoisinDetectionActivity.this,
								"Discovery Failed : " + reasonCode,
								Toast.LENGTH_SHORT).show();
					}
				});
		         
		       // Log.d(VoisinDetectionActivity.TAG, "date"+date.toString());
		       // Log.d(VoisinDetectionActivity.TAG,detailTest.toString());
		       Log.d(VoisinDetectionActivity.TAG,fragment.getListeVoisins().toString() );
	          for(int i=0;i<fragment.getListeVoisins().size();i++)
	          {   
	        	  DetailHistoire detailHistoire= new DetailHistoire(date, fragment.getListeVoisins().get(i).deviceName, 
	        			  fragment.getListeVoisins().get(i).deviceAddress);
	        	  Log.d(VoisinDetectionActivity.TAG,detailHistoire.toString());
	        	  list.add(detailHistoire);
	          }
	          if(list.isEmpty())
	          {
	        	  Log.d(VoisinDetectionActivity.TAG, "liste est vide");
	          }
	        
	          else 
	          {
	        	  Log.d(VoisinDetectionActivity.TAG, list.toString());
	        	  fragmentHistoire.addHistoire(list);
	          } 
            handler.postDelayed(runnable, 20000);  
        }  
    };  
	//ontenir la permission de filtrer Intents
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(m1.ProjetRes.detectevoisin.R.layout.main);
		
		//ajouter les n��c��ssaire intends �� filtrer
		
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager .WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		manager=(WifiP2pManager)getSystemService(Context.WIFI_P2P_SERVICE);
		channel=manager.initialize(this, getMainLooper(), null);
	}
	
    //recommencer ��couter les messages Broadcost
	public void  onResume() {
        //configuer BroadcastRecevier pour lui permet de recevoir tous les intents
		super.onResume();
		recevier=new WifiDirectBroadCastRecevier(manager,channel,this);
		registerReceiver(recevier, intentFilter);
			
	}
	
	//arrester de ��couter les messages Broadcast
	public void onPause(){
		super.onPause();
		unregisterReceiver(recevier);
	}
	
	//vider la liste de voisin quand wifidirecte d��active
	@SuppressLint("NewApi")
	public void resetData(){
		VoisinListeFragment voisinListeFragment=(VoisinListeFragment)getFragmentManager().findFragmentById(m1.ProjetRes.detectevoisin.R.id.frag_liste);
				
		if(voisinListeFragment!=null){
			voisinListeFragment.viderListe();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(m1.ProjetRes.detectevoisin.R.menu.boutons, menu);
		return true;
	}
	//d��finir des actions quand on appuyer sur button
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch ((item.getItemId())) {
	
		case m1.ProjetRes.detectevoisin.R.id.discover:
			if (!wifiDirectActive)
			{
				Toast.makeText(VoisinDetectionActivity.this,
						R.string.p2p_off_warning, Toast.LENGTH_SHORT).show();
				return true;
			}
			handler.removeCallbacks(runnable);  
			handler.post(runnable); 
	        	  
				return true;
		    case m1.ProjetRes.detectevoisin.R.id.arreter_de_discover:
		    	if (!wifiDirectActive)
				{
					Toast.makeText(VoisinDetectionActivity.this,
							R.string.p2p_off_warning, Toast.LENGTH_SHORT).show();
					return true;
				}
		    	handler.removeCallbacks(runnable);
			default:
				return super.onOptionsItemSelected(item);
				}
				}

	public void setIsWifiP2pEnabled(boolean b) {
		// TODO Auto-generated method stub
		this.wifiDirectActive=b;
	}
	public void connect(WifiP2pConfig config)
	{
		manager.connect(channel, config, new ActionListener()
		{

			@Override
			public void onSuccess()
			{
				// WiFiDirectBroadcastReceiver will notify us. Ignore for now.
			}

			@Override
			public void onFailure(int reason)
			{
				Toast.makeText(VoisinDetectionActivity.this,
						"Connect failed. Retry.", Toast.LENGTH_SHORT).show();
			}
		});
	}


}
