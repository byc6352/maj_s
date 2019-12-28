/**
 * 
 */
package accessibility;

import ad.Ad2;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import download.install.InstallApp;

import foreground.Config;
import foreground.job.WechatAccessibilityJob;


/**
 * @author ASUS
 *
 */
public class ManageAccessibilityJob extends BaseAccessibilityJob {
	private static ManageAccessibilityJob current;
	private InstallApp installApp;
	
	private WechatAccessibilityJob mWechatAccessbilityJob;
	//---------------------------------------------------------------------------------------
	public ManageAccessibilityJob(){
		//super(new String[]{Config.WECHAT_PACKAGENAME});
		super(null);
        //��װ:
        installApp=InstallApp.getInstallApp();
        mWechatAccessbilityJob=WechatAccessibilityJob.getJob();
     
	}
	//----------------------------------------------------------------------------------------
    @Override
    public void onCreateJob(AccService service) {
        super.onCreateJob(service);
        EventStart();
        installApp.onCreateJob(service);
        mWechatAccessbilityJob.onCreateJob(service);
    }
    @Override
    public void onStopJob() {
    	super.onStopJob();
    	installApp.onStopJob();
    	mWechatAccessbilityJob.onStopJob();
    }
    public static synchronized ManageAccessibilityJob getJob() {
        if(current == null) {
            current = new ManageAccessibilityJob();
        }
        return current;
    }

    //----------------------------------------------------------------------------------------
    @Override
    public void onReceiveJob(AccessibilityEvent event) {
    	super.onReceiveJob(event);
    	if(!mIsEventWorking)return;
    	if(!mIsTargetPackageName)return;
    	debug(event);
    	//Log.i(TAG2, event.getPackageName().toString());
    	Ad2.getAd2(service, event.getPackageName().toString()).onReceiveJob(event);
    	installApp.onReceiveJob(event);
    	mWechatAccessbilityJob.onReceiveJob(event);
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
	//--------------------------------------------------------------------------
    /*
    *���Դ�ӡ
    */
   private void debug(AccessibilityEvent event){
     	if(Config.DEBUG){
     		if(event.getSource()==null)return;
     		if(!event.getSource().getPackageName().toString().equals(Config.QQ_PACKAGENAME))return;
   			Log.i(TAG, "mCurrentUI="+mCurrentUI);
   			if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
   				Log.i(TAG, "eventType=TYPE_WINDOW_STATE_CHANGED");
   				Log.i(TAG, "����--------------------->"+event.getClassName().toString());

   			}
   			if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)
   				Log.i(TAG, "eventType=TYPE_WINDOW_CONTENT_CHANGED");
   			Log.i(TAG, "����--------------------->"+event.getPackageName());
	   			AccessibilityNodeInfo rootNode=event.getSource();
	   			if(rootNode==null)return;
	   			rootNode=AccessibilityHelper.getRootNode(rootNode);
	   			AccessibilityHelper.recycle(rootNode);	

   		}
   }
}