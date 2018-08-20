package com.youquweb.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getId(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
        return sdf.format(new Date());
    }
}
