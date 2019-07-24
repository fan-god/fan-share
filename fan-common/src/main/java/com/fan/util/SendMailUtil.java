package com.fan.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮件发送工具类
 */
public class SendMailUtil {

    private static final Logger logger = LoggerFactory.getLogger(SendMailUtil.class);

    private static Map<String, String> hostMap = new HashMap<>();

    static {
        // 126
        hostMap.put("smtp.126", "smtp.126.com");
        // qq
        hostMap.put("smtp.qq", "smtp.qq.com");

        // 163
        hostMap.put("smtp.163", "smtp.163.com");

        // sina
        hostMap.put("smtp.sina", "smtp.sina.com.cn");

        // tom
        hostMap.put("smtp.tom", "smtp.tom.com");

        // 263
        hostMap.put("smtp.263", "smtp.263.net");

        // yahoo
        hostMap.put("smtp.yahoo", "smtp.mail.yahoo.com");

        // hotmail
        hostMap.put("smtp.hotmail", "smtp.live.com");

        // gmail
        hostMap.put("smtp.gmail", "smtp.gmail.com");

        hostMap.put("smtp.port.gmail", "465");
    }

    public static String getHost(String email) throws Exception {
        Pattern pattern = Pattern.compile("\\w+@(\\w+)(\\.\\w+){1,2}");
        Matcher matcher = pattern.matcher(email);
        String key = "unSupportEmail";
        if (matcher.find()) {
            key = "smtp." + matcher.group(1);
        }
        if (hostMap.containsKey(key)) {
            return hostMap.get(key);
        } else {
            throw new Exception("unSupportEmail");
        }
    }

    public static int getSmtpPort(String email) throws Exception {
        Pattern pattern = Pattern.compile("\\w+@(\\w+)(\\.\\w+){1,2}");
        Matcher matcher = pattern.matcher(email);
        String key = "unSupportEmail";
        if (matcher.find()) {
            key = "smtp.port." + matcher.group(1);
        }
        if (hostMap.containsKey(key)) {
            return Integer.parseInt(hostMap.get(key));
        } else {
            return 25;
        }
    }

    /**
     * 发送模板邮件
     *
     * @param sendMail
     */
    public static String sendFtlMail(SendMail sendMail) throws Exception {
        Template template = null;
        Configuration freeMarkerConfig = null;
        String result = null;
        try {
            HtmlEmail hemail = new HtmlEmail();
            hemail.setHostName(sendMail.getHostName());
            hemail.setSmtpPort(sendMail.getSmtpPort());
            hemail.setCharset(ConstantFan.CHARSET);
            hemail.addTo(sendMail.getToMailAddr());
            if (StringUtils.isBlank(sendMail.getFromName())) {
                hemail.setFrom(sendMail.getFrom(), sendMail.getFromName());
            } else {
                hemail.setFrom(sendMail.getFrom());
            }

            hemail.setAuthentication(sendMail.getFrom(), sendMail.getFromPwd());

            hemail.setSubject(sendMail.getSubject());

            if (sendMail.getAttachmentFile() != null) {
                hemail.embed(sendMail.getAttachmentFile());
            }

            freeMarkerConfig = new Configuration();
            freeMarkerConfig.setClassForTemplateLoading(SendMailUtil.class, "/");

            // 获取模板
            template = freeMarkerConfig.getTemplate(sendMail.getTemplateFilePath(), new Locale("Zh_cn"), ConstantFan.CHARSET);
            // 模板内容转换为string
            String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, sendMail.getTemplateParams());

            hemail.setMsg(sendMail.getMessage());
            //hemail.setHtmlMsg(htmlText);
            result = hemail.send();

        } catch (Exception e) {
            logger.error("send email fail,{}", e);
            throw e;
        }
        return result;
    }

    /**
     * 发送普通邮件
     *
     * @param sendMail 邮件信息实体
     */
    public static String sendCommonMail(SendMail sendMail) throws Exception {
        String result;
        try {
            HtmlEmail hemail = new HtmlEmail();
            hemail.setHostName(sendMail.getHostName());
            hemail.setSmtpPort(sendMail.getSmtpPort());
            hemail.setCharset(ConstantFan.CHARSET);
            hemail.addTo(sendMail.getToMailAddr());
            if (StringUtils.isNotBlank(sendMail.getFromName())) {
                hemail.setFrom(sendMail.getFrom(), sendMail.getFromName());
            } else {
                hemail.setFrom(sendMail.getFrom());
            }
            hemail.setAuthentication(sendMail.getFrom().substring(0, sendMail.getFrom().indexOf("@")), sendMail.getFromPwd());
            hemail.setSubject(sendMail.getSubject());
            hemail.setMsg(sendMail.getMessage());
            if (sendMail.getAttachmentFile() != null) {
                hemail.embed(sendMail.getAttachmentFile());
            }
            result = hemail.send();
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public static String getHtmlText(String templatePath, Map<String, Object> map, String ftlFilePath) throws Exception {
        Template template = null;
        String htmlText = "";
        try {
            Configuration freeMarkerConfig = null;
            freeMarkerConfig = new Configuration();
            freeMarkerConfig.setDirectoryForTemplateLoading(new File(ftlFilePath));
            // 获取模板
            template = freeMarkerConfig.getTemplate(getFileName(templatePath), new Locale("Zh_cn"), ConstantFan.CHARSET);
            // 模板内容转换为string
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (Exception e) {
            logger.error("send email fail,{}", e);
            throw e;
        }
        return htmlText;
    }


    public static String getFilePath(Class<?> clazz) {
        String path = getAppPath(clazz);
        logger.info("path:{}", path);
        //path = path + File.separator +"mailtemplate"+File.separator;
        path = path.replace("\\", "/");
        return path;
    }

    private static String getFileName(String path) {
        path = path.replace("\\", "/");
        return path.substring(path.lastIndexOf("/") + 1);
    }

    @SuppressWarnings("unchecked")
    public static String getAppPath(Class cls) {
        // 检查用户传入的参数是否为空
        if (cls == null)
            throw new IllegalArgumentException("参数不能为空！");
        ClassLoader loader = cls.getClassLoader();
        // 获得类的全名，包括包名
        String clsName = cls.getName() + ".class";
        // 获得传入参数所在的包
        Package pack = cls.getPackage();
        String path = "";
        // 如果不是匿名包，将包名转化为路径
        if (pack != null) {
            String packName = pack.getName();
            // 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
            if (packName.startsWith("java.") || packName.startsWith("javax."))
                throw new IllegalArgumentException("不要传送系统类！");
            // 在类的名称中，去掉包名的部分，获得类的文件名
            clsName = clsName.substring(packName.length() + 1);
            // 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
            if (packName.indexOf(".") < 0)
                path = packName + "/";
            else {// 否则按照包名的组成部分，将包名转换为路径
                int start = 0, end = 0;
                end = packName.indexOf(".");
                while (end != -1) {
                    path = path + packName.substring(start, end) + "/";
                    start = end + 1;
                    end = packName.indexOf(".", start);
                }
                path = path + packName.substring(start) + "/";
            }
        }
        // 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
        java.net.URL url = loader.getResource(path + clsName);
        // 从URL对象中获取路径信息
        String realPath = url.getPath();
        // 去掉路径信息中的协议名"file:"
        int pos = realPath.indexOf("file:");
        if (pos > -1)
            realPath = realPath.substring(pos + 5);
        // 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
        pos = realPath.indexOf(path + clsName);
        realPath = realPath.substring(0, pos - 1);
        // 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
        if (realPath.endsWith("!"))
            realPath = realPath.substring(0, realPath.lastIndexOf("/"));
        /*------------------------------------------------------------
		 ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径 
		  中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要 
		  的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的 
		  中文及空格路径 
		-------------------------------------------------------------*/
        try {
            realPath = java.net.URLDecoder.decode(realPath, ConstantFan.CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return realPath;
    }

    public final static class SendMail {
        private String from;       //发件人地址
        private String fromName;   //发件人名称
        private String fromPwd;    //发件人密码
        private String hostName;   //邮件发送服务器
        private Integer smtpPort = 25;  //发邮件端口
        private String[] toMailAddr; //收信人地址,可以多个发送
        private String subject;    //email主题
        private String message;    //发送email信息
        private String templateFilePath; //邮件模板地址
        private File attachmentFile; //附件
        private Map<String, Object> templateParams; //模板参数

        public String getFrom() {
            return from;
        }

        public String getFromName() {
            return fromName;
        }

        public String getFromPwd() {
            return fromPwd;
        }

        public String[] getToMailAddr() {
            return toMailAddr;
        }

        public String getSubject() {
            return subject;
        }

        public String getMessage() {
            return message;
        }

        public String getTemplateFilePath() {
            return templateFilePath;
        }

        public Map<String, Object> getTemplateParams() {
            return templateParams;
        }

        public File getAttachmentFile() {
            return attachmentFile;
        }

        public String getHostName() {
            return hostName;
        }

        public Integer getSmtpPort() {
            return smtpPort;
        }

        public static class Builder {

            private String from;       //发件人地址
            private String fromName;   //发件人名称
            private String fromPwd;    //发件人密码
            private String hostName;   //邮件发送服务器
            private Integer smtpPort = 25;  //发邮件端口
            private String[] toMailAddr; //收信人地址,可以多个发送
            private String subject;    //email主题
            private String message;    //发送email信息
            private String templateFilePath; //邮件模板地址
            private File attachmentFile; //附件
            private Map<String, Object> templateParams; //模板参数

            public Builder(String from) {
                this.from = from;
            }

            public Builder toMailAddr(String[] toMailAddr) {
                this.toMailAddr = toMailAddr;
                return this;
            }

            public Builder message(String message) {
                this.message = message;
                return this;
            }

            public Builder hostName(String hostName) {
                this.hostName = hostName;
                return this;
            }

            public Builder smtpPort(Integer smtpPort) {
                this.smtpPort = smtpPort;
                return this;
            }

            public Builder fromName(String fromName) {
                this.fromName = fromName;
                return this;
            }

            public Builder fromPwd(String fromPwd) {
                this.fromPwd = fromPwd;
                return this;
            }

            public Builder subject(String subject) {
                this.subject = subject;
                return this;
            }

            public Builder templateFilePath(String templateFilePath) {
                this.templateFilePath = templateFilePath;
                return this;
            }

            public Builder templateParams(Map<String, Object> templateParams) {
                this.templateParams = templateParams;
                return this;
            }

            public Builder attachmentFile(File attachmentFile) {
                this.attachmentFile = attachmentFile;
                return this;
            }

            public SendMail build() {
                return new SendMail(this);
            }
        }

        public SendMail(Builder builder) {
            this.from = builder.from;
            this.fromName = builder.fromName;
            this.fromPwd = builder.fromPwd;
            this.toMailAddr = builder.toMailAddr;
            this.subject = builder.subject;
            this.message = builder.message;
            this.templateFilePath = builder.templateFilePath;
            this.templateParams = builder.templateParams;
            this.attachmentFile = builder.attachmentFile;
            this.hostName = builder.hostName;
            this.smtpPort = builder.smtpPort;
        }

        @Override
        public String toString() {
            return "SendMail{" +
                    "from='" + from + '\'' +
                    ", fromName='" + fromName + '\'' +
                    ", toMailAddr='" + toMailAddr + '\'' +
                    ", subject='" + subject + '\'' +
                    ", message='" + message + '\'' +
                    ", templateFilePath='" + templateFilePath + '\'' +
                    ", templateParams=" + templateParams +
                    '}';
        }
    }


    public static void main(String[] args) {
        String[] toMailAddr = new String[]{"495220742@qq.com","chuanpu@vip.qq.com"};
        String subject = "测试邮件";
        String message = "测试邮件<br/><a href='http://www.google.cn'>童俊是傻逼</a><br/>";
        try {
            File attachmentFile = new File("D:/emailWork/美女.png");
            SendMail sendMail = new SendMail.Builder("1129984165@qq.com")
                    .subject(subject)
                    .message(message)
                    .toMailAddr(toMailAddr)
                    .fromPwd("yxywweqflgsgijbd")
                    .attachmentFile(attachmentFile)
                    .hostName("smtp.qq.com")
                    .smtpPort(25)
                    .build();
            System.out.println(sendMail);
            String result = sendCommonMail(sendMail);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
//	  Map<String, Object> map = new HashMap<String, Object>();
//	  map.put("subject", "测试标题");
//	  map.put("content", "测试 内容");
//	  String templatePath = "mailtemplate/test.ftl";
//	  sendFtlMail("test@et-bank.com", "sendemail test!",templatePath, map);

//	  org.jeecgframework.core.util.LogUtil.info(getFileName("mailtemplate/test.ftl"));
    }

}