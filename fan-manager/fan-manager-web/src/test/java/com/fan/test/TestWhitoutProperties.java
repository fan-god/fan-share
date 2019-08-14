package com.fan.test;

import com.fan.entity.CarInfo;
import com.fan.entity.User;
import com.fan.entity.excelmodel.TestExcelModel;
import com.fan.util.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fan
 * @title: TestWhitoutProperties
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/12/001213:59
 */
public class TestWhitoutProperties {

    public static void main(String[] args) throws Exception {
//        Pattern pattern = Pattern.compile("v([+-]?\\d*\\.\\d*)");
//        Matcher matcher = pattern.matcher("fan-manager-web/app/user/v1.0/listPageAll");
//        System.out.println(matcher.find());
//        String group1 = matcher.group(0);
//        String group2 = matcher.group(1);
//        System.out.println(group1);
//        System.out.println(group2);

//        Integer age = 0;
//        Date birthDate = DateUtils.parseDate("1991-07-01","yyyy-MM-dd");
//        if (null != birthDate) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(birthDate);
//            Calendar calendarNow = Calendar.getInstance();
//            calendarNow.setTime(new Date());
//            age = calendarNow.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
//        }
//        System.out.println(age);

//        ExcelFactory excelFactory = new ExcelFactory();
//        List<TestExcelModel> list = Lists.newArrayList();
//        TestExcelModel m1 = TestExcelModel.builder().age(13).email("ctv@bnj.com").name("ftgvyubh").build();
//        TestExcelModel m2 = TestExcelModel.builder().age(11).email("dwfevgrtgbhn@163.com").name("qaqzzxswx").build();
//        TestExcelModel m3 = TestExcelModel.builder().age(23).email("1129985465@qq.com").name("wf").build();
//        list.add(m1);
//        list.add(m2);
//        list.add(m3);
//        excelFactory.getExcelWrite(TestExcelModel.class,list);

        System.out.println(SignUtil.getSHA1("张三李四"));
        System.out.println(SignUtil.getMD5("张三李四"));
        System.out.println(SignUtil.getSHA256("张三李四"));
        System.out.println(SignUtil.getHmacSHA256("张三李四","张三李四"));
        System.out.println(SignUtil.encodeBase64("张三李四"));
        System.out.println(SignUtil.decodeBase64("5byg5LiJ5p2O5Zub"));

    }

    static void swap(int[] array, int index1, int index2) {
        int t = array[index1];
        array[index1] = array[index2];
        array[index2] = t;
    }

    private void shuffleAlgorithm(){
//        洗牌算法
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            swap(arr, i, RandomUtils.nextInt(0, i));
        }
        for (int element : arr) {
            System.out.print(element + " ");
        }
    }

    private void test1(){
        User user = new User();
        user.setPhone("ertfyuio");
        user.setUsername("ghjk");
        user.setAddress("sdfghjk");
        user.setPassword("1111111");
        Map<String, Object> map = DataConvertUtil.beanToMap(user);
        map.forEach((k, v) -> {
            System.out.printf("%s:%s%n", k, v);
        });
        User u = DataConvertUtil.mapToBean(map, User.class);
        System.out.println(u);

    }

    private void test2(){
        CarInfo carInfo = CarInfo.builder().color("red").brandName("奥迪").displacement(2.0).build();
        String xml = XmlUtil.beanToXml(carInfo, CarInfo.class);
        System.out.println(xml);
        Map<String, String> map = XmlUtil.xmlToMap(xml);
        map.forEach((k, v) -> {
            System.out.printf("%s:%s%n", k, v);
        });
    }

    private void test3() throws Exception {
        // 存放在二维码中的内容
        String text = "http://m16808311q.iask.in/fan-manager-web/api/testing/v1.0/sendEmailBySweepCode";
        // 嵌入二维码的图片路径
        String imgPath = "D:\\fan-share/美女2.jpg";
//
        //生成二维码
        QRCodeUtil.createQRCode(text, imgPath, "测试", true);
        // 解析二维码
        String str = QRCodeUtil.decode("测试");
        // 打印出解析出的内容
        System.out.println(str);

        String url = "http://baidu.com";
        String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        QRCodeUtil.createQRCode(url, path, fileName);
    }
}
