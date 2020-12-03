package com.ifreegroup.simple.delay.controller;

import com.ifreegroup.simple.delay.plugin.DelayedSender;
import com.ifreegroup.simple.delay.ttl.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: DelayController
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/3
 */
@RestController
public class DemoController {
    @Autowired
    private Sender sender;
    @Autowired
    private DelayedSender delayedSender;

    /***
     * 通过ttl+死信方式模拟延时消息
     */
    @GetMapping("/sendTtlDelay")
    public void sendDelayTTL() {
        sender.send();
    }

    /***
     * 通过插件方式模拟延迟消息
     */
    @GetMapping("/sendPluginDelay")
    public void sendDelayPlugin(){
        delayedSender.send();
    }
}
