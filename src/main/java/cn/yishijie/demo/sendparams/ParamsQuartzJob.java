package cn.yishijie.demo.sendparams;

import org.quartz.*;

/**
 * @author chenjianhui on 2019/11/19
 */
public class ParamsQuartzJob implements Job {
    //该任务适用于传参数的一些场景

    public static final String param0 = "name";
    public static final String param1 = "status";

    public ParamsQuartzJob() {
        //do nothing
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //获取定义的任务
        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        //获取任务的名称和group
        JobKey jobKey = jobDetail.getKey();

        //获取传入的参数
        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        System.out.println("status:"+jobDataMap.getInt(param1)+","+"name:"+jobDataMap.getString(param0));
    }
}
