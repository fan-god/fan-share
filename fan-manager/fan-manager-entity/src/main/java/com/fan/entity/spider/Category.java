//package com.fan.entity.spider;
//
//import com.geccocrawler.gecco.annotation.HtmlField;
//import com.geccocrawler.gecco.annotation.Text;
//import com.geccocrawler.gecco.spider.HrefBean;
//import com.geccocrawler.gecco.spider.HtmlBean;
//
//import java.util.List;
//
///**
// * @author fan
// * @title: Category
// * @projectName fan-share
// * @description: TODO
// * @date 2019/8/19/001910:02
// */
//public class Category implements HtmlBean {
//    @Text
//    @HtmlField(cssPath = "dt a")
//    private String parentName;
//
//    @HtmlField(cssPath = "ul li")
//    private List<HrefBean> categorys;
//
//    public String getParentName() {
//        return parentName;
//    }
//
//    public void setParentName(String parentName) {
//        this.parentName = parentName;
//    }
//
//    public List<HrefBean> getCategorys() {
//        return categorys;
//    }
//
//    public void setCategorys(List<HrefBean> categorys) {
//        this.categorys = categorys;
//    }
//}
