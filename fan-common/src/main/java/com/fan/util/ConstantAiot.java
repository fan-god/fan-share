package com.fan.util;

import java.util.HashMap;
import java.util.Map;


/**
* @ClassName: ConstantAiot
* @Description: 该 类用于存取系统常量 
* @author boway
* @date 2018年7月24日
*
*/
public class ConstantAiot {

	/**
	 * 记录info级别日志
	 */
	public static Boolean LOG_WRITE_INFO = true;
	
	/**
	* @Fields EXPIRATION : 该属性作为reids中CRM过期时间的字段，存放在MongoDB中
	*/
	public static final String CRNEXPIRATION ="crnExpiration";
	
	/**
	* @Fields ARGSEXPIRATION :该属性作为reids中ARGS过期时间的字段，存放在MongoDB中
	*/
	public static final String ARGSEXPIRATION ="argsExpiration";
	
	public static final String ARGS = "args";

	public static int ARGS_DB_INDEX = 3;
	
	public static final String CRM = "crm";
	
	public static final String CHARSET ="UTF-8";
	
	public static final String JSON ="json";
	
	public static final String PROTOBUF ="protobuf";

	public static final String XML ="xml";

	public static final String USER_CONTEXT = "userInfo";
	
	public static final String SESSION = "session";

	public static final String MESSAGE = "message";
 
	public static Double OVER_GPRS_THRESHOLD = 80.0;
	public static final String OVER_GPRS_THRESHOLD_KEY = "over.gprs.threshold.key";

	/**
	 * reids对应的Map本地缓存
	 * */
	
	public static Map<String,Integer> redisDicMap = new HashMap<String, Integer>();
	public static String[] REDIS_MAP ={};
	public static final String REDIS_SESSION_INDEX = "sessionIndex";
	public static final String REDIS_SESSION_OUTTIME="sessionOutTime";
	public static final String REDIS_ARGS_INDEX = "argsIndex";
	public static final String REDIS_ARGS_OUTTIME="argsOutTime";
	public static final String REDIS_CRM_INDEX = "crmIndex";
	public static final String REDIS_CRM_OUTTIME="crmOutTime";
	public static final String HOME_ERROR = "error";
	//检测session下标库是否发生了改变
	public static boolean SESSION_IS_UPDATE=false;
	//首页默认显示条数
	public static final Integer HOME_COUNT = 20;
	//秒数
	public static final Integer SESSION_SECOND=60;
		public static Map<String,String> devDicMap = new HashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("0","刷卡开门");
				put("1","蓝牙开门");
				put("2","访客密码");
				put("3","远程开门");
				put("error","开门方式未知");
			}
		};
	/**
	 * 定时器分组
	 * JOB_GROUP_DEFAULT 任务组名
	 * TRIGGER_GROUP_DEFAULT 触发器组名
	 */
	public static final String JOB_GROUP_DEFAULT = "server1";
	public static final String TRIGGER_GROUP_DEFAULT = "server1";


	public static final String IOT_FLOW_OVER_THRESHOLD = "iot.flow.over.threshold";

	/**有方物联卡常量**/
	public static final String YOUFANG_URL = "youfang.url";
	public static final String YOUFANG_ACCOUNT = "youfang.account";
	public static final String YOUFANG_KEY = "youfang.key";
	public static final String YOUFANG_SUCCESS = "youfang.success";
	public static final String YOUFANG_FAIL = "youfang.fail";

	/**中景元物联卡常量*/
	public static final String ZHONGJINGY_URL = "zhongjingy.url";
	public static final String ZHONGJINGY_ACCOUNT = "zhongjingy.account";
	public static final String ZHONGJINGY_ACCOUNT_PWD = "zhongjingy.account.pwd";
	public static final String ZHONGJINGY_KEY = "zhongjingy.key";
	public static final String ZHONGJINGY_SUCCESS = "zhongjingy.success";
	public static final String ZHONGJINGY_FAIL = "zhongjingy.fail";
	public static final String ZHONGJINGY_SIGN_FAIL_CNT = "zhongjingy.sign.fail.cnt";

	/**中移物联卡常量*/
	public static final String ZHONGYI_URL = "zhongyi.url";
	public static final String ZHONGYI_APPID = "zhongyi.appid";
	public static final String ZHONGYI_PASSWORD = "zhongyi.password";
	public static final String ZHONGYI_TRANSID = "zhongyi.transid";
	public static final String ZHONGYI_EBID = "zhongyi.ebid";
	public static final String ZHONGYI_TOKEN = "zhongyi.token";
	public static final String ZHONGYI_SUCCESS = "zhongyi.success";
	public static final String ZHONGYI_FAIL = "zhongyi.fail";


    //1:未激活,2:已打开,3:已关闭,4:在线,5:离线,6:删除,7:禁用,8:其他
	public static final String DEV_CARD_STATUS = "[{\"cardStatus\":1,\"cardStatusDesc\":\"未激活\"}," +
			"{\"cardStatus\":2,\"cardStatusDesc\":\"已打开\"}," +
			"{\"cardStatus\":3,\"cardStatusDesc\":\"已关闭\"}," +
			"{\"cardStatus\":4,\"cardStatusDesc\":\"在线\"}," +
			"{\"cardStatus\":5,\"cardStatusDesc\":\"离线\"}," +
			"{\"cardStatus\":6,\"cardStatusDesc\":\"删除\"}," +
			"{\"cardStatus\":7,\"cardStatusDesc\":\"禁用\"}," +
			"{\"cardStatus\":8,\"cardStatusDesc\":\"其他\"}]";

	public static final String DEV_CARD_STATUS_KEY = "dev.card.status.key";

	//物联卡状态 ：0=停用，1=正常,2=未激活，3=不可用
	public static final String IOT_CARD_STATUS_0 = "0";
	public static final String IOT_CARD_STATUS_1 = "1";
	public static final String IOT_CARD_STATUS_2 = "2";
	public static final String IOT_CARD_STATUS_3 = "3";

	/**邮件发送常量*/
	/**
	 * 发件人邮件
	 */
	public static final String SEND_MAIL_FROM = "send.mail.from";
	/**
	 * 发件人别名
	 */
	public static final String SEND_MAIL_FROMNAME = "send.mail.fromname";
	/**
	 * 发件人邮箱密码
	 */
	public static final String SEND_MAIL_FROM_PWD = "send.mail.from.pwd";
	/**
	 * 发件邮件服务器
	 */
	public static final String SEND_MAIL_HOST_NAME = "send.mail.host.name";
	/**
	 * 发件邮箱smtp端口
	 */
	public static final String SEND_MAIL_SMTP_PORT = "send.mail.smtp.port";
	/**
	 * 邮件模板生产的临时目录
	 */
	public static final String SEND_MAIL_TEMP_PATH = "send.mail.temp.path";

	/**
	 * 默认删除90以前的物联卡信息
	 */
	public static final int DELETE_SIMCARD_DAY_AMOUNT = -90;
	public static final String DELETE_SIMCARD_DAY_AMOUNT_KEY = "delete.simcard.day.amount.key";

	/**
	 * 是否记录日志
	 */
	public static final Boolean LOG_WRITE = true;
	public static final String LOG_WRITE_KEY = "log.write.key";

	/**
	 * 刷新所有物联卡
	 */
	public static final int REFRESH_ALL_SIMCARD = 0;
	public static final String REFRESH_ALL_SIMCARD_KEY = "refresh.all.simcard";

	/**
	 * 刷新物联卡开始时间
	 */
	public static final int REFRESH_SIMCARD_START_HOUR = 7;
	public static final String REFRESH_SIMCARD_START_HOUR_KEY = "refresh.simcard.start.hour";

	/**
	 * 刷新物联卡结束时间
	 */
	public static final int REFRESH_SIMCARD_END_HOUR = 22;
	public static final String REFRESH_SIMCARD_END_HOUR_KEY = "refresh.simcard.end.hour";

	/**
	 * 刷新物联卡分页查询，一页大小
	 */
	public static final int REFRESH_SIMCARD_PAGE_SIZE = 200;
	public static final String REFRESH_SIMCARD_PAGE_SIZE_KEY = "refresh.simcard.page.size";

	/**
	 * 预警邮件主题信息
	 */
	public static final String SEND_MAIL_WARN_SUBJECT = "物联卡流量预警通知";
	/**
	 * 预警邮件主题信息key
	 */
	public static final String SEND_MAIL_WARN_SUBJECT_KEY = "send.mail.warn.subject.key";
	/**
	 * 预警邮件提示信息
	 */
	public static final String SEND_MAIL_WARN_MESSAGE = "物联卡流量预警通知,有【%s】个物联卡流量快使用完,详情请看附件.";
	/**
	 * 预警邮件提示信息key
	 */
	public static final String SEND_MAIL_WARN_MESSAGE_KEY = "send.mail.warn.message.key";
	/**
	 * 预警邮件模板路径
	 */
	public static final String SEND_MAIL_WARN_TEMPLATEFILEPATH = "/com/aiot/timetask/notify/mailtemplate/sendWarnMailTemplate.html";
	/**
	 * 预警邮件模板路径key
	 */
	public static final String SEND_MAIL_WARN_TEMPLATEFILEPATH_KEY = "send.mail.warn.templatefilepath.key";

	/**
	 * 逗号分隔符
	 */
	public static final String SEPARATOR_DOUHAO = ",";

	/**短信发送常量*/
	/**
	 * 预警短信内容模板
	 */
	public static final String SEND_SMS_WARN_MESSAGE = "物联卡流量预警通知,截止【%s】，已有【%s】个物联卡流量快使用完,详情请看收邮件或登录AIOT平台查看.";
	/**
	 * 预警短信内容模板key
	 */
	public static final String SEND_SMS_WARN_MESSAGE_KEY = "send.sms.warn.message.key";


	/**--------------------日期格式常量--------------------*/
	public static final String DATE_PATTERN_8 = "yyyyMMdd";
	public static final String DATE_PATTERN_10 = "yyyy-MM-dd";
	public static final String DATE_PATTERN_14 = "yyyyMMddHHmmss";
	public static final String DATE_PATTERN_17 = "yyyyMMddHHmmssSSS";
	public static final String DATE_PATTERN_19 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN_19_2 = "yyyy/MM/dd HH:mm:ss";
	/**--------------------日期格式常量--------------------*/


	public static final String USER_TYPE_ADMIN = "0";
	public static final String USER_TYPE_DEV = "1";
	public static final String USER_TYPE_IOT = "2";
	public static final String USER_TYPE_DEVANDIOT = "3";


	public static final String USER_TYPE_ADMIN_NAME = "管理员";
	public static final String USER_TYPE_IOT_NAME = "物联卡";


	/*************************API******************************/
	public static final String APINAME = "apiName";
	public static final String APPKEY = "appkey";
	public static final String SIGN = "apiName";
	public static final String TIMESTAMP = "timestamp";
	public static final String API_EXEC_S = "success";
	public static final String API_EXEC_F = "fail";
}
