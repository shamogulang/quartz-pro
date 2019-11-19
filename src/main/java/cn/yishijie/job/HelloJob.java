package cn.yishijie.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author chenjianhui on 2019/11/19
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("hello quartz...name:"+jobExecutionContext.getJobDetail().getKey().getName());
    }
}
