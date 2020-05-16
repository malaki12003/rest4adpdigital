package com.hamrasta.sms_tools;


import com.hamrasta.sms_tools.util.SmsUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SMSController {

    @PostMapping("/send")
    public void send(@RequestParam(value = "phone") String phone, @RequestParam(value = "content") String content) {
        SmsUtil.send(phone, content);
    }
}
