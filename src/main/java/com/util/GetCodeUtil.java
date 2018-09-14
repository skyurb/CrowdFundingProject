package com.util;

import java.util.Random;

public class GetCodeUtil {
    public static String getCode(){
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }
}

