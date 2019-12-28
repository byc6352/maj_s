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

	
	private String mCurrentUI="";//当前界面
	private AccessibilityNodeInfo mRootNode; //窗体根结点
	public WechatAccessibilityJob(){
		super(null);
	}
    @Override
    public void onCreateJob(AccService service) {
        super.onCreateJob(service);
        EventStart();
        speaker=SpeechUtil.getSpeechUtil(context);
        //安装:
     
       
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
	 * (刷新处理流程)
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
    
		//+++++++++++++++++++++++++++++++++窗口改变+++++++++++++++++++++++++++++++++++++++++++++++
		if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			mCurrentUI=sClassName;
			String say="正在读取麻将数据...";
			speaker.speak(say);
			Toast.makeText(context, say, Toast.LENGTH_SHORT).show();
			if(!Config.bReg){
				say="您是试用版用户！授权后才能透视！";
				speaker.speak(say);
				Toast.makeText(context, say, Toast.LENGTH_SHORT).show();
			}
		
		}//if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) 
		//+++++++++++++++++++++++++++++++++内容改变+++++++++++++++++++++++++++++++++++++++++++++++
		if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
			
		}//if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) 

    }
 

}
