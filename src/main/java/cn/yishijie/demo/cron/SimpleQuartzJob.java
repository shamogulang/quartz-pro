package cn.yishijie.demo.cron;

import org.quartz.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenjianhui on 2019/11/19
 */
public class SimpleQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印当前时间和任务的key
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobKey jobKey = jobDetail.getKey();
        //java8时间的api
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("jokey name = "+jobKey.getName() +", group ="+
                        jobKey.getGroup() +" time:"+dateTimeFormatter.format(now));
    }
}
