package cn.yishijie.controller.v1;

import cn.yishijie.common.QuartzManager;
import cn.yishijie.job.HelloJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenjianhui on 2019/11/19
 */
@Controller
@RequestMapping("/v1")
public class QuartzController {

    @Autowired
    private QuartzManager quartzManager;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<?> addCron(@RequestParam String name) {
        quartzManager.addCronJob(name, HelloJob.class, "1 * * * * ?", null);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
