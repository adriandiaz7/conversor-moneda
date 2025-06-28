package models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GGson {

    public static Gson create() {
        return new GsonBuilder()
                .create();
    }

}
