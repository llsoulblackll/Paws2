package com.app.pawapp.Util.Gson.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Locale;

public class WCFDateSerializer implements JsonSerializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        //EVEN AFTER ALLOWING NULLS IN OUR GSON FACTORY, IT IS SMART ENOUGH TO NOT PASS THEM TO THE SERIALIZER, noise
        System.out.println(String.format(Locale.getDefault(),"/Date(%d)/", src.getTime()));
        return new JsonPrimitive(String.format(Locale.getDefault(),"/Date(%d)/", src.getTime()));
    }
}
