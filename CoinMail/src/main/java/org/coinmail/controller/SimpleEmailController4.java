package org.coinmail.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class SimpleEmailController4 {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;
    
    private String timemessage;

    private static final Logger log = LoggerFactory.getLogger(ScheduleEmail.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 20000)
    public String reportCurrentTime() {
       timemessage= "The time is now"+ dateFormat.format(new Date());
       return timemessage;
        
    }

    @RequestMapping("/simpleemail4")
    @ResponseBody
    String home() {
        try {
            sendEmail();
            return "Email Sent!";
        } catch (Exception ex) {
            return "Error in sending email: " + ex;
        }
    }

    @Scheduled(fixedRate = 20000)
    private void sendEmail() throws Exception {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        Map<String, Object> model = new HashMap();
        model.put("user", "enter_in_controller_username");
        model.put("timemessage", timemessage);
        // set loading location to src/main/resources
        // You may want to use a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        
        Template t = freemarkerConfig.getTemplate("welcome.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo("theoriviere.riv@gmail.com");
        helper.setText(text, true); // set to html
        helper.setSubject("New test email template");

        sender.send(message);
    }
}