package com.sns.igscheduler.service;

import com.sns.igscheduler.Cache.CacheHelper;
import com.sns.igscheduler.model.Igpost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class SchedulePostService {

    public String Schedulepost(Igpost igpost) throws ParseException {
        String res = "";
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("\n------------------------------\nTask performed on: " + new Date() + "\nThread's name: " + Thread.currentThread().getName());
                System.out.println(igpost.getImageurl()+" "+igpost.getPostdate()+" "+igpost.getPosttime()+" "+igpost.getCaptions());
            }
        };
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormatter.parse(igpost.getPostdate()+" "+igpost.getPosttime());
        Timer timer = new Timer(igpost.getImageurl());
        //long delay = 1000L;
        CacheHelper.PostCache.put(igpost.getImageurl(),timer);
        timer.schedule(task, date);
        return  res;
    }
}
