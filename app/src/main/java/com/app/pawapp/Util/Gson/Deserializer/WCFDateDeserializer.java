package com.app.pawapp.Util.Gson.Deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class WCFDateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String d = json.getAsJsonPrimitive().getAsString();
        System.out.println(d);
        if(d.indexOf('(') > -1 && d.indexOf(')') > -1) {
            if(d.indexOf('-') > -1 && (d.indexOf('(') + 1) != d.indexOf('-'))
                return new Date(Long.parseLong(d.substring(d.indexOf('(') + 1, d.indexOf('-'))));
            if (d.indexOf('-') > -1 && d.lastIndexOf('-') > -1 && d.indexOf('-') != d.lastIndexOf('-'))
                return new Date(Long.parseLong(d.substring(d.indexOf('(') + 1, d.lastIndexOf('-'))));
            else if (d.indexOf('+') > -1)
                return new Date(Long.parseLong(d.substring(d.indexOf('(') + 1, d.lastIndexOf('+'))));
            else
                return new Date(Long.parseLong(d.substring(d.indexOf('(') + 1, d.indexOf(')'))));
        }

        try {
            return DateFormat.getDateInstance(DateFormat.LONG).parse(d);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
