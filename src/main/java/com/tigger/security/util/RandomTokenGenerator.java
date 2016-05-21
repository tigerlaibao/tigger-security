package com.tigger.security.util;

import java.util.Random;

/**
 * 随即token生成
 * Created by laibao
 */
public class RandomTokenGenerator {

    private static final int tokenLength = 8 ;

    private static final char[] tokenCharArray = {
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            '0','1','2','3','4','5','6','7','8','9'
    };

    private static final int tokenCharArrayLength = tokenCharArray.length;

    private static Random random = new Random();

    public static String generate(){
        StringBuilder token = new StringBuilder();
        for(int i = 0 ; i < tokenLength ;i++){
            token.append(tokenCharArray[random.nextInt(tokenCharArrayLength)]);
        }
        return token.toString();
    }

}
