package cn.yishijie.demo.hello;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author chenjianhui on 2019/11/19
 */
public class HelloQuartz {
    //这个是简单的输出任务，每隔一分钟打印hello quartz

    public static void main(String[] args) throws Exception{
        //获取定时任务工厂类
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        //通过工厂获取对应的scheduler实例，有异常要抛出，我这里直接在方法上抛出了
        Scheduler sched = schedulerFactory.getScheduler();

        //job任务类，绑定HelloQuartzJob中需要执行的任务
        JobDetail jobDetail0 = JobBuilder.newJob((Class<? extends Job>) HelloQuartzJob.class)
                .withIdentity("j0", "jg0").build();

        //定义触发器，同时指定每分钟执行一次，没有指定开始时间，会马上开始,我这里指定了当前时间加上一分钟，
        //比如，你是定义了在 2019-11-19 11:24:23,那么在 2019-11-19 11:25:01会调用第一次(他是不计后面的那个23的)
        //如果不关闭这个任务，那么以后每隔一秒都会调用一次
        Trigger trigger  = TriggerBuilder.newTrigger().withIdentity("t0","tg0")
                            .startAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(1L)))
                              .withSchedule(CronScheduleBuilder.cronSchedule("1 * * * * ?")).build();

        //绑定job和触发器，一个job可以被多个触发器使用，一个触发器只能绑定一个job
        sched.scheduleJob(jobDetail0, trigger);

        //开启定时任务
        sched.start();

        // 关闭定时任务，如果不关，会一直每隔一分钟执行一次
        // sched.shutdown(true);

        // 结果：  hello quartz, time: 2019-11-19 11:46:01
    }
}
