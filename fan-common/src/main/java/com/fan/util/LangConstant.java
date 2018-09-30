package com.fan.util;

/**
 * Created by Achievo on 2018/9/5.
 * 这个类用于放国际化的配置文件每个key的名字常量
 */
public interface LangConstant {
    /************************国际化字段名称常量********************************/
    String LANGUAGE = "language";
    String BASENAME = "resource/message";

    /********************国家语言配置常量**********************/
    enum Country {
        CHINA("zh"), JAPANESE("ja"), ENGLISH("en");
        String name;

        Country(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    /**************************************************properties文件字段配置*********************************/
    /***********************公共配置**************************/
    enum Common {
        //分页没传页面显示页数
        error_pageSize("error_pageSize"),
        //参数非空
        no_empty("no_empty"),
        request_param("request_param");
        String key;

        Common(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }


    /************************************************************接口***************************************************/
    /********************产品*********************/
    enum Product {
        error_status("error_pro_status"),
        error_type("error_type"),
        error_phone("error_phone"),
        pro_exist("pro_exist");
        String key;

        Product(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    /*******************设备*********************/
    enum Device{
        error_productId("error_productId"),
        error_devType("error_devType"),
        error_status("error_dev_status"),
        error_longitude("error_longitude"),
        error_latitude("error_latitude"),
        dev_exist("dev_exist");
        String key;

        Device(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    /*******************设备类型*********************/
    enum DeviceType{
        error_typeName("error_typeName"),
        error_typeId("error_typeId"),
        type_exist("type_exist");
        String key;

        DeviceType(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    /*******************平台*********************/
    enum Platform{
        error_PlatformName("error_typeName"),
        error_PlatformId("error_typeId");
        String key;

        Platform(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    /********************作业调度*******************/
    enum Job {
        error_jobApi("error_jobApi"),
        error_cron("error_cron"),
        error_job_trigger("error_job_trigger"),
        error_status("error_job_status"),
        error_jobs("error_jobs"),
        error_path("error_path"),
        error_jobName("error_jobName"),
        job_exist_errPath("job_exist_errPath"),

        nothing_return("nothing_return"),
        nothing_warming("nothing_warming");
        String key;

        Job(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    /********************用户登录退出 *********************/

    enum UserLogin {
        msg_null("msg_null"),
        user_login_error_fail("user_login_error_fail"),
        user_login_error_name("user_login_error_name"),
        user_login_error_status("user_login_error_status"),
        user_login_success("user_login_success"),
        user_loginout_success("user_loginout_success"),
        user_loginout_fail("user_loginout_fail");
        String key;

        UserLogin(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
    /******************* redis缓存 *********************/
    enum RedisManager{
        redis_manager_check("redis_manager_check"),
        redis_manager_add_success("redis_manager_add_success"),
        redis_manager_error("redis_manager_error"),
        redis_manager_delete_fail("redis_manager_delete_fail"),
        redis_manager_delete_success("redis_manager_delete_success"),
        redis_manager_update_fail("redis_manager_update_fail"),
        redis_manager_update_success("redis_manager_update_success"),
        redis_manager_query_null("redis_manager_query_null"),
        redis_manager_query("redis_manager_query"),
        redis_manager_drop_fail("redis_manager_drop_fail"),
        redis_manager_drop_success("redis_manager_drop_success");
        String key;

        RedisManager(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
    /******************* 系统参数以及crm信息 *********************/
    enum Dictionar{
        crm_manager_parent_error("crm_manager_parent_error"),
        crm_manager_insert_error("crm_manager_insert_error"),
        crm_manager_msg_success("crm_manager_msg_success"),
        crm_manager_msg_fail("crm_manager_msg_fail"),
        diction_manager_error("diction_manager_error"),
        diction_manager_redis("diction_manager_redis"),
        diction_manager_add("diction_manager_add"),
        diction_manager_check("diction_manager_check"),
        diction_manager_check_delete("diction_manager_check_delete"),
        diction_manager_delete("diction_manager_delete"),
        diction_manager_update("diction_manager_update");
        String key;
        Dictionar(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
    /******************* 菜单以及菜单权限信息 *********************/
    enum MenuManager{
        menu_manager_filter("menu_manager_filter"),
        menu_manager_check("menu_manager_check"),
        menu_manager_check_null("menu_manager_check_null"),
        menu_manager_edit_code("menu_manager_edit_code"),
        menu_manager_query("menu_manager_query"),
        menu_manager_update("menu_manager_update");
        String key;
        MenuManager(String key){this.key = key;}
        public String getKey() {
            return key;
        }
    }

}
