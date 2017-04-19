package android.com.skyh.tool;

public class PrefName {


   public  static final String IMG_SERVER_URL = "http://10.208.4.26/";
   public  static final String DEFAULT_SERVER_URL = "http://10.208.4.26:7001";
   //public  static final String DEFAULT_SERVER_URL = "http://192.168.1.106:8080";
   //public  static final String DEFAULT_SERVER_URL = "http://10.208.72.55:8080";

   public  static final String LOGIN_SERVER_URL = "/djoa/xtdl/dl.json";//用户登录
   public  static final String LOGIN_TREE_SERVER_URL = "/djoa/xtdl/getTree.json";//取到登陆树
   public  static final String LOGIN_TWO_SERVER_URL = "/djoa/xtdl/getSelect.json";//取到二级用户列表

   public  static final String FIND_DYDY_SERVER_URL = "/djoa/api/findListDydhb.json";//查找党员大会
   public  static final String FIND_DK_SERVER_URL = "/djoa/api/findListDkxxjl.json";//查找党课
   public  static final String FIND_ZWH_SERVER_URL = "/djoa/api/findListZbwyh.json";//查找支委会
   public  static final String FIND_DXZH_SERVER_URL = "/djoa/api/findListDxzhy.json";//查找党小组会

  public  static final String INSERT_DYDY_SERVER_URL = "/djoa/api/insertDydhb.json";//插入党员大会
  public  static final String INSERT_DXZH_SERVER_URL = "/djoa/api/insertDxzhy.json";//插入党小组会
 public  static final String INSERT_ZHW_SERVER_URL = "/djoa/api/insertZbwyh.json";//插入支委会
 public  static final String INSERT_DKXX_SERVER_URL = "/djoa/api/insertDkxxjl.json";//插入党课学习


 public  static final String DYDY_BYID_SERVER_URL = "/djoa/api/findByidDydhb.json";//党员大会BYID
  public  static final String DXZH_BYID_SERVER_URL = "/djoa/api/findByidDxzhy.json";//党小组会BYID
 public  static final String ZHW_BYID_SERVER_URL = "/djoa/api/findByidZbwyh.json";//支委会BYID
 public  static final String DKXX_BYID_SERVER_URL = "/djoa/api/findByidDkxxjl.json";//党课学习BYID

 public  static final String FTP_SERVER_URL = "/djoa/api/findFTPUtils.json";//FTP文件上传

    //登录信息




    public static final String HYID = "HYID";

    public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String POST_FILE = "file";
	public static final String POST_FILE1 = "file1";
	public static final String POST_FILE2= "file2";
	public static final String POST_PHOTO = "filephoto";
	public static final String POST_MORE_FILE = "morefile";
	public static final String POST_SIGN = "postSign";
	public static final String POST_LONG_TIMEOUT = "postLongTimeout";
    public static final String GET_WITHOUTRESULT = "GETWITHOUTRESULT";
    public static final String PREF_BOOL_HAS_SHOW_HELP = "hasShowHelp";


}