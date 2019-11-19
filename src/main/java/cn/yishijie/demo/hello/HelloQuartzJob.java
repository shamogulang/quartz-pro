package cn.yishijie.demo.hello;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenjianhui on 2019/11/19
 */
public class HelloQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //java8时间api，获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        //java8用于时间的格式化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //打印输入语句
        System.out.println("hello quartz, time: "+ dateTimeFormatter.format(localDateTime));
    }
}
