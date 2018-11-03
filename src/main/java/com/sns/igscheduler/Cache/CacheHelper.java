package com.sns.igscheduler.Cache;

import com.sns.igscheduler.model.Igpost;
import com.sns.igscheduler.service.IgpostService;
import com.sns.igscheduler.service.SchedulePostService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class CacheHelper {
    @Autowired
    protected static IgpostService igpostService;
    @Autowired
    private static SchedulePostService schedulePostService;

    public static ConcurrentHashMap<String, Timer> PostCache = new ConcurrentHashMap<>();

    public static void startInitialTasks() throws ParseException {
        try {
            List<Igpost> igposts = igpostService.getAll();
            if(igposts.size()>0) {
                for (Igpost igpost :
                        igposts) {
                    // TODO: 31-Jul-18 implement isDone and then check the flag or compare with current Date-time
                    //if(igpost.getPostdate().compareTo(new Date().toString()))
                    schedulePostService.Schedulepost(igpost);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
