package api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

public class JsonUntil {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) throws JsonProcessingException {
        if (object != null)
            return objectMapper.writeValueAsString(object);
        return "{error: Object null}";
    }

    public static ResponseTransformer json() {
        return JsonUntil::toJson;
    }

}
