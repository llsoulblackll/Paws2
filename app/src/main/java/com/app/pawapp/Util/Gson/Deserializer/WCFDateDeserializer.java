package com.app.pawapp.Util.Gson.Deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

public class WCFDateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String d = json.getAsJsonPrimitive().getAsString();
        if(d.indexOf('-') > -1)
            return new Date(Long.parseLong(d.substring(d.indexOf('(') + 1, d.indexOf('-'))));
        else if(d.indexOf('+') > -1)
            return new Date(Long.parseLong(d.substring(d.indexOf('(') + 1, d.indexOf('+'))));
        else
            return new Date(Long.parseLong(d.substring(d.indexOf('(') + 1, d.indexOf(')'))));
    }
}
