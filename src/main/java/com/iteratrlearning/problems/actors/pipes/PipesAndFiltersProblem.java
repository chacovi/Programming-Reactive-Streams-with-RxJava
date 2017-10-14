package com.iteratrlearning.problems.actors.pipes;

import java.util.Arrays;
import java.util.Base64;

public class PipesAndFiltersProblem {
    public static void main(String[] args) {
        String[] forbiddenWords = {"isn't", "i'm", "don't"};
        String message = "I am feeling \\alpha";
        String messageLowerCase = message.toLowerCase();
        if(Arrays.stream(forbiddenWords).noneMatch(messageLowerCase::contains)) {
            String unicodeConverted =  message.replaceAll("\\\\alpha", "α").replaceAll("\\\\beta", "β");
            System.out.println(unicodeConverted);
            byte[] encodedBytes = Base64.getEncoder().encode(unicodeConverted.getBytes());
            System.out.println("encodedBytes: " + new String(encodedBytes));
        }
    }
}
