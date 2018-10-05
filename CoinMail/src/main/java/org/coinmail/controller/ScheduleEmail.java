package org.coinmail.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEmail {
	
	private String timemessage;

    private static final Logger log = LoggerFactory.getLogger(ScheduleEmail.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 20000)
    public String reportCurrentTime() {
       timemessage= "The time is now"+ dateFormat.format(new Date());
       return timemessage;
        
    }
}