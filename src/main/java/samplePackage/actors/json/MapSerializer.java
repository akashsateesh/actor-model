package samplePackage.actors.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Data
public class MapSerializer implements Serializable {
    private static final long serialVersionUID = 42L;
    private int a;
    @JsonSerialize
    private Map<String,Object> map;

    public static class Custom extends StdSerializer<Map<String,String>> {

        private ObjectMapper objectMapper;

        protected Custom(Class<Map<String, String>> t) {
            super(t);
            objectMapper = new ObjectMapper();
        }

        public Custom(){
            this(null);
        }

        @Override
        public void serialize(Map<String, String> stringStringMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString((SerializableString) stringStringMap);
        }
    }
}
