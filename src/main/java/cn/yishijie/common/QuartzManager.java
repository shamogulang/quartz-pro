package cn.yishijie.common;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author chenjianhui on 2019/11/19
 */
@Component
public class QuartzManager {

    Logger logger = LoggerFactory.getLogger(QuartzManager.class);
    private static final String JOB_PARAMS = "jobParams";
    @Autowired
    private Scheduler scheduler;


    /**
     * 添加一次性任务
     * @param beginTime
     * @param name
     * @param jobClass
     * @param params
     */
    public void addSimpleJob( long beginTime, String name,Class<? extends Job> jobClass, String params){
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(name).usingJobData(JOB_PARAMS,params).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name).startAt(new Date(beginTime)).build();
        try{
            scheduler.scheduleJob(jobDetail,trigger);
        }catch (Exception e){
            logger.error(String.format("Quartz addSimpleJob [%s] exception, detail:", name),e);
        }
    }

    /**
     * 传入任务的名称
     * @param name
     */
    public void deleteJob(String name){
        JobKey jobKey = new JobKey(name);
        try {
            scheduler.deleteJob(jobKey);
        }catch (Exception e){
            logger.error(String.format("Quartz deleteJob [%s] exception, detail:", name),e);
        }
    }

    /**
     * 添加cron类型的任务
     * @param name
     * @param jobClass
     * @param cronSchedule
     * @param params
     */
    public void addCronJob(String name,Class<? extends Job> jobClass,String cronSchedule, String params){
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(name)
                .usingJobData(JOB_PARAMS,params)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(name)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
                .build();
        try{
            scheduler.scheduleJob(jobDetail,trigger);
        }catch (Exception e){
            logger.error(String.format("Quartz addCronJob [%s] exception, detail:", name),e);
        }
    }
}
