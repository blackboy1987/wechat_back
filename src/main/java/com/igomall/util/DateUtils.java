package com.igomall.util;

import java.time.LocalDate;

/**
 * 采用jdk8的日期类进行操作时间
 */
public final class DateUtils {

    private DateUtils(){}



    public static void a(){
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
    }


    public static void main(String[] args) {
        a();
    }

}
