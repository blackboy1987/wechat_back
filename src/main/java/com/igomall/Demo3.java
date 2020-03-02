package com.igomall;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo3 {

    public static void main(String[] args) {
        String str = "23";
        String regEx = "^\\d{3}$";
        boolean rs = str.matches(regEx);
        System.out.println(rs);
    }

}
