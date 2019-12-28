/**
 * 
 */
package download.install;

import activity.Splash1Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author byc
 *
 */
public class MonitorSysReceiver extends BroadcastReceiver{    
    private static String TAG="";
    @Override      
    public void onReceive(Context context, Intent intent){    
        //���հ�װ�㲥     
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {       
            //TODO  
        	Splash1Activity.startSplash1Activity(context);
        	Log.i(TAG, "PACKAGE_ADDED:"+getResultData());
        }       
        //����ж�ع㲥      
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {       
            //TODO   
        	Splash1Activity.startSplash1Activity(context);
        	Log.i(TAG, "PACKAGE_REMOVED:"+getResultData());
        }    
    }    
}  
