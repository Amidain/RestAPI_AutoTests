package util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonValidator {
    static ObjectMapper mapper = new ObjectMapper()
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

    public static boolean isValid(String json) {
        try {
            mapper.readTree(json);
        } catch (JacksonException e) {
            return false;
        }
        return true;
    }
}
