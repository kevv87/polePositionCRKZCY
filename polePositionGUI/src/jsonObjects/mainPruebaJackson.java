package jsonObjects;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class mainPruebaJackson {

    public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"poscarro1\":1," +
                "\"tipoCarro1\":\"a\"," +
                "\"velocidadCarro1\":2," +
                "\"posCarro2\":3,"+
                "\"tipoCarro2\":\"b\","+
                "\"velocidadCarro2\":4,"+
                "\"posBalas\":[1,2],"+
                "\"accion\":\"A\""+
                "}";

        // map json to midgamemsg
        try{
            midGameMsg msg = mapper.readValue(jsonString, midGameMsg.class);

            System.out.println(msg);

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
