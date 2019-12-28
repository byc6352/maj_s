/**
 * 
 */
package foreground.job;

import java.util.Timer;
import java.util.TimerTask;

import foreground.Config;
import util.SpeechUtil;

import foreground.MainActivity;

import accessibility.BaseAccessibilityJob;
import accessibility.AccService;

import com.bysc.maj.R;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

/**
 * @author byc
 *
 */
public class WechatAccessibilityJob extends BaseAccessibilityJob  {
	
	private static WechatAccessibilityJob current;
	private SpeechUtil speaker ;

	
	private String mCurrentUI="";//��ǰ����
	private AccessibilityNodeInfo mRootNode; //��������
	public WechatAccessibilityJob(){
		super(null);
	}
    @Override
    public void onCreateJob(AccService service) {
        super.onCreateJob(service);
        EventStart();
        speaker=SpeechUtil.getSpeechUtil(context);
        //��װ:
     
       
    }
   
    @Override
    public void onStopJob() {
    	super.onStopJob();
    }
    public static synchronized WechatAccessibilityJob getJob() {
        if(current == null) {
            current = new WechatAccessibilityJob();
        }
        return current;
    }
	/*
	 * (ˢ�´�������)
	 * @see accessbility.AccessbilityJob#onWorking()
	 */
	@Override
	public void onWorking(){
		//Log.i(TAG2, "onWorking");
		//installApp.onWorking();
	} 
    //----------------------------------------------------------------------------------------
    @Override
    public void onReceiveJob(AccessibilityEvent event) {
    	if(!mIsEventWorking)return;
    	if(!mIsTargetPackageName)return;
    	
    	final int eventType = event.getEventType();
    	if(event.getClassName()==null)return;
    	String sClassName=event.getClassName().toString();    	
    
		//+++++++++++++++++++++++++++++++++���ڸı�+++++++++++++++++++++++++++++++++++++++++++++++
		if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			mCurrentUI=sClassName;
			String say="���ڶ�ȡ�齫����...";
			speaker.speak(say);
			Toast.makeText(context, say, Toast.LENGTH_SHORT).show();
			if(!Config.bReg){
				say="�������ð��û�����Ȩ�����͸�ӣ�";
				speaker.speak(say);
				Toast.makeText(context, say, Toast.LENGTH_SHORT).show();
			}
		
		}//if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) 
		//+++++++++++++++++++++++++++++++++���ݸı�+++++++++++++++++++++++++++++++++++++++++++++++
		if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
			
		}//if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) 

    }
 

}
