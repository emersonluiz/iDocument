package br.com.controlunion.iDocument.util;

import java.io.IOException;

import br.com.controlunion.iDocument.model.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static Document jsonToString(String jsonString)
            throws JsonParseException, JsonMappingException, IOException {

        //Map<String, String> map = new HashMap<String, String>();
        Document document = new Document();
        ObjectMapper mapper = new ObjectMapper();

        document = mapper.readValue(jsonString, Document.class);

        return document;
    }

}
