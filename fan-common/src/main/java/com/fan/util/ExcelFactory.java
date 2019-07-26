package com.fan.util;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.List;


/**
 * 来源：CSDN
 * 原文：https://blog.csdn.net/jianggujin/article/details/80200400
 *
 * @author:Achievo
 * @Date:2018/12/6.
 * @Description:
 * @PACKAGE_NAME:com.aiot.util
 * @PROJECT_NAME:aiotplatform
 */
@Slf4j
public class ExcelFactory<T extends BaseRowModel> {
    //导出文件路径
//    private String exportFilePath;

    /**
     * @param in            文件输入流
     * @param customContent 自定义模型可以在
     *                      {@link AnalysisEventListener#invoke(Object, AnalysisContext) }
     *                      AnalysisContext中获取用于监听者回调使用
     * @param eventListener 用户监听
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static ExcelReader getExcelReader(InputStream in, Object customContent, AnalysisEventListener<?> eventListener) throws IOException, InvalidFormatException {
        // 如果输入流不支持mark/reset，需要对其进行包裹
        if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }
        // 确保至少有一些数据
        byte[] header8 = IOUtils.peekFirst8Bytes(in);
        ExcelTypeEnum excelTypeEnum = null;
        if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
            excelTypeEnum = ExcelTypeEnum.XLS;
        }
        if (DocumentFactoryHelper.hasOOXMLHeader(in)) {
            excelTypeEnum = ExcelTypeEnum.XLSX;
        }
        if (excelTypeEnum != null) {
            return new ExcelReader(in, excelTypeEnum, customContent, eventListener);
        }
        throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
    }

    /**
     * @param in            文件输入流
     * @param customContent 自定义模型可以在
     *                      {@link AnalysisEventListener#invoke(Object, AnalysisContext) }
     *                      AnalysisContext中获取用于监听者回调使用
     * @param eventListener 用户监听
     * @param trim          是否对解析的String做trim()默认true,用于防止 excel中空格引起的装换报错。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static ExcelReader getExcelReader(InputStream in, Object customContent, AnalysisEventListener<?> eventListener, boolean trim) throws IOException, InvalidFormatException {
        // 如果输入流不支持mark/reset，需要对其进行包裹
        if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }
        // 确保至少有一些数据
        byte[] header8 = IOUtils.peekFirst8Bytes(in);
        ExcelTypeEnum excelTypeEnum = null;
        if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
            excelTypeEnum = ExcelTypeEnum.XLS;
        }
        if (DocumentFactoryHelper.hasOOXMLHeader(in)) {
            excelTypeEnum = ExcelTypeEnum.XLSX;
        }
        if (excelTypeEnum != null) {
            return new ExcelReader(in, excelTypeEnum, customContent, eventListener, trim);
        }
        throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
    }

    /**
     * 导出EXCEL
     *
     * @param list
     * @param c
     */
    public void getExcelWrite(Class<T> c, List<T> list) {
        try {
            String className = c.getSimpleName();
            String exportFilePath = ConstantFan.EXPORT_FILE_PATH.concat(DateConvertEditor.getDate());
            //检查文件路径是否存在
            OperateFileUtil.checkFilePath(exportFilePath);
            exportFilePath = exportFilePath.concat(File.separator).concat(className).concat(ConstantFan.XLSX);
            OutputStream out = new FileOutputStream(exportFilePath);
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet = new Sheet(ConstantFan.SHEET_NO, ConstantFan.HEAD_LINE_MUN, c);
            sheet.setSheetName(ConstantFan.SHEET_NAME);
            writer.write(list, sheet);
            writer.finish();
        } catch (Exception e) {
            log.error("ExcelFactory getExcelWrite error", e);
        }
    }


    /**
     * 导出EXCEL
     *
     * @param data
     * @param c
     */
    public void getExcelWrite(Class<T> c, String data) {
        try {
            List<T> list = DataConvertUtil.stringToList(data, ConstantFan.JSON, c);
            this.getExcelWrite(c, list);
        } catch (Exception e) {
            log.error("ExcelFactory getExcelWrite error", e);
        }
    }

    /**
     * 因为第一行是表头，所以从第二行开始
     *
     * @param context
     * @return
     */
    public static int getRow(AnalysisContext context) {
        return context.getCurrentRowNum() + 1;
    }
}
