package cn.yishijie.demo.cron;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author chenjianhui on 2019/11/19
 */
public class CronQuartz {
    //cron类型的任务，是通过一些字符串来表示执行的时机 1 * * * * ?

    public static void main(String[] args) throws Exception{
        //获取定时任务工厂类
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        //通过工厂获取对应的scheduler实例，有异常要抛出，我这里直接在方法上抛出了
        Scheduler sched = schedulerFactory.getScheduler();

        //执行的任务
        JobDetail jobDetail0 = JobBuilder.newJob((Class<? extends Job>) SimpleQuartzJob.class)
                .withIdentity("j0", "jg0").build();

        /**
         *  1 * * * * ? 表示每一次分钟执行一次
         *  0,30 * * ? * SAT,SUN 周末每隔30分钟执行一次
         */
        Trigger trigger  = TriggerBuilder.newTrigger().withIdentity("t0","tg0")
                            .startNow()
                              .withSchedule(CronScheduleBuilder.cronSchedule("1 * * * * ?")).build();

        //绑定job和触发器，一个job可以被多个触发器使用，一个触发器只能绑定一个job
        sched.scheduleJob(jobDetail0, trigger);

        //开启定时任务
        sched.start();

    }
}
