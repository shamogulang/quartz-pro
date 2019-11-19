package cn.yishijie.demo.sendparams;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author chenjianhui on 2019/11/19
 */
public class ParamsQuartz {

    public static void main(String[] args) throws Exception{
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();


        JobDetail jobDetail = JobBuilder.newJob(ParamsQuartzJob.class)
                .withIdentity("j0", "jg0").build();

        jobDetail.getJobDataMap().put("name","jeffchan");
        jobDetail.getJobDataMap().put("status",1);

        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .startNow().withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
                .withIdentity("t0", "tg0").build();

        scheduler.scheduleJob(jobDetail, simpleTrigger);

        scheduler.start();
    }
}
