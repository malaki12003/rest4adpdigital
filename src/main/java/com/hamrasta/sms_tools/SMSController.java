package com.hamrasta.sms_tools;


import com.hamrasta.sms_tools.util.SmsUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SMSController {

    @PostMapping("/send")
    public void send(@RequestBody Message message) {
        SmsUtil.send(message.phone, message.content,message.username,message.password,message.sourceNo);
    }
    public static class Message{
        private String phone ,content,username , password , sourceNo;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSourceNo() {
            return sourceNo;
        }

        public void setSourceNo(String sourceNo) {
            this.sourceNo = sourceNo;
        }
    }
}

