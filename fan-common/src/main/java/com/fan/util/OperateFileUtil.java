package com.fan.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * @author:Achievo
 * @Date:2018/12/6.
 * @Description: 操作文件上传下载工具
 * @PACKAGE_NAME:com.aiot.util
 * @PROJECT_NAME:aiotplatform
 */
@Component
@Slf4j
public class OperateFileUtil {

    /**
     * 单个上传
     *
     * @param file
     * @param session
     * @return
     */
    public static String uploadFile(MultipartFile file, HttpSession session) {
        MultipartFile[] files = {file};
        return uploadFile(files, session).get(0);
    }

    /**
     * 批量上传
     *
     * @param files
     * @param session
     * @return
     */
    public static List<String> uploadFile(MultipartFile[] files, HttpSession session) {
        List<String> list = Lists.newArrayList();
        try {
            String basePath = session.getServletContext().getRealPath(File.separator);
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String newPath = splicingPath(basePath, ConstantFan.IMPORT_FILE_PATH, getFileNameByDate());
                //目标文件
                File targetFile = new File(newPath);
                //如果文件目录不存在则创建目录
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                //转存文件
                targetFile = new File(newPath, getNewFileName(fileName));
                file.transferTo(targetFile);
                //将所有上传成功的文件的路径存储起来，便于读取文件
                log.info(targetFile.getAbsolutePath());
                list.add(targetFile.getAbsolutePath());
            }
            return list;
        } catch (Exception e) {
            log.error("FileUploadUtil uploadFile error:", e);
            return null;
        }
    }

    private static String getFileNameByDate() {
        return DateConvertEditor.getDate();
    }

    private static String getNewFileName(String oldName) {
        return UUID.randomUUID().toString().concat("-").concat(oldName);
    }

    /**
     * 拼接形成新的路径
     *
     * @param args
     * @return
     */
    private static String splicingPath(String... args) {
        StringBuffer sb = new StringBuffer();
        for (String arg : args) {
            sb.append(arg).append(File.separator);
        }
        sb.deleteCharAt(sb.length());
        return sb.toString();
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static void deleteFile(String path) {
        try {
            FileUtils.forceDelete(new File(path));
        } catch (Exception e) {
            log.error("FileUploadUtil deleteFile error:", e);
        }
    }

    /**
     * 下载
     *
     * @param fileName
     * @param filePath
     * @return
     */
    public static ResponseEntity<byte[]> downLoadFile(String fileName, String filePath) {
        try {
            HttpStatus status = HttpStatus.CREATED;
            String header = HttpCilentUtil.getRequest().getHeader("User-Agent").toUpperCase();
            if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
                // IE下载文件名空格变+号问题
                fileName = URLEncoder.encode(fileName, ConstantFan.CHARSET).replace("+", "%20");
                status = HttpStatus.OK;
            } else {
                fileName = new String(fileName.getBytes(ConstantFan.CHARSET), "ISO8859-1");
            }
            HttpHeaders headers = new HttpHeaders();
            File file = new File(filePath);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(file.length());
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, status);
        } catch (Exception e) {
            log.error("FileUploadUtil downLoadFile error:", e);
            return null;
        }
    }

    /**
     * 校验上传文件类型
     *
     * @param files
     * @param fileTypes
     * @return
     */
    public static boolean checkFileType(MultipartFile[] files, String fileTypes) {
        try {
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                String fileSuffixName = filename.substring(filename.lastIndexOf(".") + 1);
                if (!fileTypes.contains(fileSuffixName.toLowerCase())) {
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("FileUploadUtil checkFileType error:", e);
            return false;
        }
        return true;
    }

    /**
     * 检查路径是否存在不存咋
     *
     * @param filePath
     */
    public static void checkFilePath(String filePath) {
        File file = new File(filePath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 从网上下载文件
     */
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    public static void downLoadFileFromLine(String lineUrl, String savePath){
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(lineUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(ConstantFan.REQUEST_METHOD_GET);
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            is = conn.getInputStream();//连接得到输入流内容
            OperateFileUtil.checkFilePath(savePath);
            fos = new FileOutputStream(new File(savePath, lineUrl.substring(lineUrl.lastIndexOf("/") + 1)));//获取网址中的图片名
            IOUtils.write(IOUtils.toByteArray(is), fos);
        } catch (Exception e) {
            log.error("downLoadFileFromLine error:{}", e);
        }finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(is);
        }
    }
}
