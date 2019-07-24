package com.fan.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.RAMJobStore;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by Achievo on 2018/8/17.
 */
@Slf4j
@Component
public class QuartzManagerUtil {
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    //暂停
    public final static Integer STAT_SHUTDOWN = 0;
    //启动
    public final static Integer STAT_START = 1;
    //触发器名称默认长度
    public final static Integer DEFAULT_LEN = 15;
    //作业包路径
    public final static String BASE_URL = "com.aiot.timetask.";
    //作业运行成功
    public final static String JOB_EXEC_RESULT_SUCC = "success";
    //作业运行失败
    public final static String JOB_EXEC_RESULT_FAIL = "fail";
    //作业运行失败
    public final static Long MIS_FIRE_THRESHOLD = 5000L;

    /**
     *默认作业组
     * ConstantFan.JOB_GROUP_DEFAULT)=server1
     *默认触发器组
     * ConstantFan.TRIGGER_GROUP_DEFAULT=server1
     */

    /**
     * @param jobName 任务名
     * @param c       任务
     * @param cron    时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @Title: QuartzManager.java
     */
    public static void addJob(String jobName, String triggerName, Class c, String cron, Boolean isInit) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(c).withIdentity(jobName, ConstantFan.JOB_GROUP_DEFAULT).build();// 任务名，任务组，任务执行类
            //解决弥补执行
            RAMJobStore jobStore = new RAMJobStore();
            jobStore.setMisfireThreshold(MIS_FIRE_THRESHOLD);
            // 触发器
            TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            tb.withIdentity(triggerName, ConstantFan.TRIGGER_GROUP_DEFAULT);
            tb.startNow();
            // 触发器时间设定
            tb.withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing());
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) tb.build();
            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
            if (isInit) {
                //初始化先停掉,页面手动控制
                stopJob(jobName);
            }
        } catch (Exception e) {
            log.info("QuartzManagerUtil add error:{}", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param c                任务
     * @param cron             时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务
     * @Title: QuartzManager.java
     */
    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class c, String cron, Boolean isInit) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(c).withIdentity(jobName, jobGroupName).build();// 任务名，任务组，任务执行类
            // 触发器
            TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger();
            tb.withIdentity(triggerName, triggerGroupName);
            tb.startNow();
            tb.withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing());
            CronTrigger trigger = (CronTrigger) tb.build();
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
            if (isInit) {
                //初始化先停掉,页面手动控制
                stopJob(jobName);
            }
        } catch (Exception e) {
            log.info("QuartzManagerUtil add error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName
     * @param cron
     * @Description: 修改一个启动任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @Title: QuartzManager.java
     */
    public static void modifyJobWhenStop(String jobName, String triggerName, String cron) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, ConstantFan.TRIGGER_GROUP_DEFAULT);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!Objects.equals(oldTime, cron)) {
                JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, ConstantFan.JOB_GROUP_DEFAULT));
                Class<? extends Job> c = jobDetail.getJobClass();
                removeJob(jobName, triggerName);
                addJob(jobName, triggerName, c, cron, true);
            }
        } catch (SchedulerException e) {
            log.info("QuartzManagerUtil modifyJobWhenStart error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param cron
     * @Description: 修改一个停止任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @Title: QuartzManager.java
     */
    public static void modifyJobWhenStart(String triggerName, String cron) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, ConstantFan.TRIGGER_GROUP_DEFAULT);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!Objects.equals(oldTime, cron)) {
                //做一个新的触发器
                TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger();
                //触发器名，默认触发器组
                tb.withIdentity(triggerName, ConstantFan.TRIGGER_GROUP_DEFAULT);
                tb.startNow();
                //设置触发器时间
                tb.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) tb.build();
                // 修改一个任务的触发时间
                sched.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            log.info("QuartzManagerUtil modifyJobWhenStop error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @Description: 修改一个自定义组任务的触发时间
     * @Title: QuartzManager.java
     */
    public static void modifyJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!Objects.equals(oldTime, cron)) {
                JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                Class<? extends Job> c = jobDetail.getJobClass();
                removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                addJob(jobName, jobGroupName, triggerName, triggerGroupName, c, cron, true);
            }
        } catch (Exception e) {
            log.info("QuartzManagerUtil modify error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @Title: QuartzManager.java
     */
    public static void removeJob(String jobName, String triggerName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, ConstantFan.TRIGGER_GROUP_DEFAULT);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, ConstantFan.JOB_GROUP_DEFAULT));// 删除任务
        } catch (Exception e) {
            log.error("QuartzManagerUtil remove error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @Description: 移除一个任务(自定义)
     * @Title: QuartzManager.java
     */
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            log.error("QuartzManagerUtil remove error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动某个默认组定时任务
     * @Title: QuartzManager.java
     */
    public static void startJob(String jobName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, ConstantFan.JOB_GROUP_DEFAULT);
            sched.resumeJob(jobKey);
        } catch (Exception e) {
            log.error("QuartzManagerUtil start error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动某个组定时任务
     * @Title: QuartzManager.java
     */
    public static void startJob(String jobName, String jobGroupName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            sched.resumeJob(jobKey);
        } catch (Exception e) {
            log.error("QuartzManagerUtil start error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     * @Title: QuartzManager.java
     */
    public static void startJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            log.error("QuartzManagerUtil start all error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭某个默认组的定时任务
     * @Title: QuartzManager.java
     */
    public static void stopJob(String jobName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, ConstantFan.JOB_GROUP_DEFAULT);
            sched.pauseJob(jobKey);
        } catch (Exception e) {
            log.error("QuartzManagerUtil shutdown error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭某个自定义组的定时任务
     * @Title: QuartzManager.java
     */
    public static void stopJob(String jobName, String jobGroupName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            sched.pauseJob(jobKey);
        } catch (Exception e) {
            log.error("QuartzManagerUtil shutdown error:{}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            log.error("QuartzManagerUtil shutdown all error:{}", e);
            throw new RuntimeException(e);
        }
    }

    public static Class<?> classFactory(String classUrl) {
        try {
            Class<?> c = Class.forName(BASE_URL + classUrl);
            return c;
        } catch (ClassNotFoundException e) {
            log.error("QuartzManagerUtil classFactory error:{}", e);
            return null;
        }
    }
}
