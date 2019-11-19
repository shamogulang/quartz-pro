package cn.yishijie.demo.simple;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author chenjianhui on 2019/11/19
 */
public class SimpleQuartz {

    public SimpleQuartz() {
       //do nothing
    }

    public static void main(String[] args) throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        //callOnce(scheduler);

        //call10TimesEvery5Seconds(scheduler);

        //callEvery5Seconds(scheduler);

        //callByYourSelf(scheduler);

        scheduler.start();

    }

    //不需要触发器，自己手动执行,当没有触发器时，一定要加 .storeDurably()，否则抛异常
    private static void callByYourSelf(Scheduler scheduler) throws Exception{
        JobDetail jobDetail = JobBuilder.newJob(SimpleQuartzJob.class)
                .withIdentity("j3", "jg3").storeDurably().build();
        scheduler.addJob(jobDetail,true);

        scheduler.triggerJob(new JobKey("j3","jg3"));
    }

    //每5秒执行一次
    private static void callEvery5Seconds(Scheduler scheduler) throws  Exception{
        JobDetail jobDetail = JobBuilder.newJob(SimpleQuartzJob.class)
                .withIdentity("j2", "jg2").build();

        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .startNow().withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
                .withIdentity("t2", "tg2").build();

        scheduler.scheduleJob(jobDetail, simpleTrigger);
        //发现重复次数为-1次，表示一直执行
        System.out.println("repeatCnt:" + simpleTrigger.getRepeatCount() + "次,internal:" + simpleTrigger.getRepeatInterval() / 1000+" seconds");
    }

    //每五秒执行一次，一共执行10次
    private static void call10TimesEvery5Seconds(Scheduler scheduler) throws Exception {
        JobDetail jobDetail = JobBuilder.newJob(SimpleQuartzJob.class)
                .withIdentity("j1", "jg1").build();

        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .startNow().withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(9,5))
                .withIdentity("t1", "tg1").build();

        scheduler.scheduleJob(jobDetail, simpleTrigger);
        System.out.println("repeatCnt:" + simpleTrigger.getRepeatCount() + "+1次,internal:" + simpleTrigger.getRepeatInterval() / 1000+" seconds");
    }

    //只执行一次
    private static void callOnce(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SimpleQuartzJob.class)
                .withIdentity("j0", "jg0").build();

        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .startAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(1L)))
                .withIdentity("t0", "tg0").build();

        scheduler.scheduleJob(jobDetail, simpleTrigger);
        //输出发现都是0，simpleTrigger.getRepeatCount()等于0说明是重复0次，就是说只调用一次
        //simpleTrigger.getRepeatInterval() / 1000表示多久后执行下一次
        System.out.println("repeatCnt:" + simpleTrigger.getRepeatCount() + "+1次,internal:" + simpleTrigger.getRepeatInterval() / 1000+" seconds");
    }
}
