package com.example.taskmanager.util;

import com.example.taskmanager.model.RoleEnum;
import com.example.taskmanager.model.User;
import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@UtilityClass
public final class Utils {

    public static boolean isAuth(User user, RoleEnum checkRole) {
        return user.getRole().equals(checkRole.name());
    }

    public static String millisecondToTime(Long millisecond) {
        Date date = new Date(millisecond);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Moscow")));
        return formatter.format(date);
    }
}
