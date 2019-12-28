package activity;


import foreground.Config;
import foreground.MainActivity;
import accessibility.AccService;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import download.Download1Service;

/*
 * 1.��������2.ÿ��10��������һ����Ȩ��(ct)3.����ҵ���߼�(Ӧ��)��
 * */
public class Splash1Activity extends Activity {
	public static boolean mHide=false;
	//private static JobSchedulerManager mJobManager=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_splash);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		getWindow().setDimAmount(0f);
		Config.getConfig(this.getApplicationContext());//��������
		getResolution();
		//mHide=this.getIntent().getBooleanExtra("hide", false);
		//if(ConfigCt.appID.equals("ct"))//������Ȩ��
		//	requestPermission(mHide);
		startAllServices();//������̨����
		if(!mHide)startMainActivityPrepare();//����ҵ����棻
		mHide=false;

		finish();	
	}
	@Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	    //Config.getConfig(this);
	    setIntent(intent);//must store the new intent unless getIntent() will return the old one
		Config.getConfig(this);//��������
		getResolution();
		//mHide=this.getIntent().getBooleanExtra("hide", false);
		//if(ConfigCt.appID.equals("ct"))//������Ȩ��
		//	requestPermission(mHide);
		startAllServices();//������̨����
		if(!mHide)startMainActivityPrepare();//����ҵ����棻
		mHide=false;
		Log.i(Config.TAG, "ct Splash onNewIntent: ����");  
		finish();	
	}
	/*
     * ����Ȩ�ޣ�
     */
	private void requestPermission(final boolean bHide){
		final Handler handler= new Handler(); 
		Runnable runnableHide  = new Runnable() {    
			@Override    
			public void run() { 
				if(AccService.getQiangHongBaoService()==null){
					AccService.sendRequsetPermissionBroadCast(Splash1Activity.this);
					Log.d(Config.TAG, "sendRequsetPermissionBroadCast ");
					if(!bHide){
						String say="���ҵ�"+Config.AppName+"�����Ҵ򿪣�";
						Toast.makeText(Splash1Activity.this,say, Toast.LENGTH_LONG).show();
						AccService.startSetting(getApplicationContext());
					
					}
				}
				handler.postDelayed(this, 10*60*1000);
			}    
		};
		handler.postDelayed(runnableHide, 1000);
	}
	/** 
	* ��������Service 
	*/  
	private void startAllServices()  
	{  
		if(Download1Service.current==null){
			//�������ط���
			Intent intent=new Intent(this,Download1Service.class);
			startService(intent);
		}

	} 
	 /*
     * ��������
     */
    public static void startHomeActivity(Context context){
		Intent home=new Intent(Intent.ACTION_MAIN);  
		home.addCategory(Intent.CATEGORY_HOME); 
		home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(home); 
    }  
	 /*
     * ��������
     */
    public static void startMainActivity(Context context){
    	Intent intent=new Intent(context,MainActivity.class);
    	//intent.putExtra("hide", false);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	context.startActivity(intent);
    }  
	 /*
     * �ڲ�����������
     */
    public static void startSplash1Activity(Context context){
    	//if(OrderService.getOrderService()!=null)
    	//	if(AppUtils.isServiceRunning(context,context.getPackageName().toString(), OrderService.class.getName()))return;
    	mHide=true;
    	Intent intent=new Intent(context,Splash1Activity.class);
    	intent.putExtra("hide", true);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	context.startActivity(intent);
    	
    }  
    /*
     * */
	private void startMainActivityPrepare(){
		Handler handler= new Handler(); 
		Runnable runnable = new Runnable() {    
			@Override    
		    public void run() {    
				
				startMainActivity(Splash1Activity.this);
				Splash1Activity.this.finish();
		    }    
		};
		handler.postDelayed(runnable, 100);
	}
	/*
     * ��������Ӧ�ó���
     */
	public  static void restartApp(Context context){
		Intent intent = new Intent(context, Splash1Activity.class);// Intent.FLAG_ACTIVITY_NEW_TASK
		PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent,Intent.FLAG_ACTIVITY_NEW_TASK );
		AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000*5,restartIntent); // 1���Ӻ�����Ӧ��
		android.os.Process.killProcess(android.os.Process.myPid());
	}
    @SuppressWarnings("deprecation")
	private void getResolution(){
        WindowManager windowManager = getWindowManager();    
        Display display = windowManager.getDefaultDisplay();    
        Config.screenWidth= display.getWidth();    
        Config.screenHeight= display.getHeight();  
        Config.navigationBarHeight= getNavigationBarHeight(this);  
    }
    public static boolean isNavigationBarShow(Activity activity){
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
	        Display display = activity.getWindowManager().getDefaultDisplay();
	        Point size = new Point();
	        Point realSize = new Point();
	        display.getSize(size);
	        display.getRealSize(realSize);
	        return realSize.y!=size.y;
	    }else {
	        boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
	        boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
	        if(menu || back) {
	            return false;
	        }else {
	            return true;
	        }
	    }
	}

	public static int getNavigationBarHeight(Activity activity) {
	    if (!isNavigationBarShow(activity)){
		//if (!isNavigationBarShow()){
	        return 0;
	    }
	    Resources resources = activity.getResources();
	    int resourceId = resources.getIdentifier("navigation_bar_height",
	            "dimen", "android");
	    //��ȡNavigationBar�ĸ߶�
	    int height = resources.getDimensionPixelSize(resourceId);
	    return height;
	}


	public static int getSceenHeight(Activity activity) {
	    return activity.getWindowManager().getDefaultDisplay().getHeight()+getNavigationBarHeight(activity);
	}
	

}