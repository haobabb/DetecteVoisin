package m1.ProjetRes.detectevoisin;

import java.io.File;

import android.R.anim;
import android.content.Context;
import android.os.Environment;

public class LogHistoire {
	private static String PATH_LOGCAT;  
	private static LogHistoire INSTANCE = null;
	//priva mLogDumpe = null;  
	private int mPid;  

	public void init(Context context) {  
        
            PATH_LOGCAT = context.getFilesDir().getAbsolutePath()  
                    + File.separator + "HistoireDetection"; 
            File file = new File(PATH_LOGCAT);  
            if (!file.exists()) {  
                file.mkdirs();  
            }  
        }  
	
	 public static LogHistoire getInstance(Context context) {  
	        if (INSTANCE == null) {  
	            INSTANCE = new LogHistoire(context);  
	        }  
	        return INSTANCE;  
	    }
	
		public LogHistoire(Context context) {
		     init(context);
		     mPid=android.os.Process.myPid();  
	    }

}
