package com.igomall;

import org.apache.commons.lang3.StringEscapeUtils;

public class Demo10 {

    public static void main(String[] args){

        String msg = "吉";
        System.out.println(StringEscapeUtils.escapeJava(msg));
        String result = StringEscapeUtils.unescapeJava("\\uD842\\uDFB7");
        System.out.println(result);
    }
}
