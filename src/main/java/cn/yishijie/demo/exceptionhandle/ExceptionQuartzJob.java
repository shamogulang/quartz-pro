package cn.yishijie.demo.exceptionhandle;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author chenjianhui on 2019/11/19
 */
public class ExceptionQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
         try {
             int i = 10;
             int j = 0;
             //模拟业务场景中出现了异常，官方给的样例中
             //有将j改成1的，这样下一次执行时，就不会
             //抛异常，但是感觉没啥用，就不模拟了
             i = i/j;
         }catch (Exception e){
             System.out.println(e.getMessage());
             JobExecutionException e2 = new JobExecutionException(e);
             //取消所有跟该job绑定的触发器，以后不再执行这个任务了
             e2.setUnscheduleAllTriggers(true);
             //这个一定要往外抛出，否则如果任务定义是永久循环，比如每五秒执行一次的话，它会继续执行，一直抛异常
             throw  e2;
         }
    }
}
