package util;

import org.apache.commons.lang3.RandomStringUtils;

public class StringLogGenerator {
    public static String generateLog(int logLength){
        return RandomStringUtils.randomAlphabetic(logLength);
    }
}
