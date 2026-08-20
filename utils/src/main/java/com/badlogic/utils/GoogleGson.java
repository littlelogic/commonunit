package com.badlogic.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

public class GoogleGson {

    {

        /*
            implementation 'com.google.code.gson:gson:2.8.9'

         */

        /// com.google.gson.JsonObject jsonObject; // google的
        /// org.json.JSONObject jsonObject2;// 系统的
        /// com.alibaba.fastjson.JSONObject jsonObject3;// 阿里的
    }
    public static final String Tag = "GoogleGson";

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()               // 美化输出
            .serializeNulls()                  // 保留 null 字段
            .setDateFormat("yyyy-MM-dd HH:mm") // 日期格式
            .create();

    // 示例：用户更新资料，将email设置为null
    public static class User {

        int age;
        boolean active;
        private String name; // 如果为null，表示要清除邮箱
        public User() {

        }
        public User(int age,boolean active,String name) {
            this.age = age;
            this.active = active;
            this.name = name;
        }
    }

    public static void testA(){
        // 使用serializeNulls()可以明确表示 "在序列化时保留 null"
        Gson gson = new GsonBuilder().serializeNulls().create();
        User request = new User(12, false,null);
        String json = gson.toJson(request);
        Log.i(Tag,"GoogleGson-testA user2.json:"+json);
        // 输出: {"name":"Alice","email":null}

        ///Gson gson = new Gson();
        User user2 = gson.fromJson(json, User.class);
        Log.i(Tag,"GoogleGson-testA user2.name:"+user2.name);
    }

    /// Java 对象 → JSON（序列化）
    public static void testB(){
        Gson gson = new Gson();

        User user = new User();
        user.name = "张三";
        user.age = 20;
        user.active = true;

        String json = gson.toJson(user);
        Log.i(Tag,"GoogleGson-testB json:"+json);
        // {"name":"张三","age":20,"active":true}
    }

    /// JSON → Java 对象（反序列化）
    public static void testC(){
        String json = "{\"name\":\"李四\",\"age\":25,\"active\":false}";

        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        Log.i(Tag,"GoogleGson-testC-name:"+user.name);   // 李四
        Log.i(Tag,"GoogleGson-testC-age:"+user.age);    // 25
        Log.i(Tag,"GoogleGson-testC-active:"+user.active); // false
    }

    /// 泛型反序列化（List / Map）
    public static void testD(){
        String json = "[{\"name\":\"小明\",\"age\":18},{\"name\":\"小红\",\"age\":19}]";

        Gson gson = new Gson();
        Type listType = new TypeToken<List<User>>(){}.getType();
        List<User> users = gson.fromJson(json, listType);
        Log.i(Tag,"GoogleGson-testD-name:"+users.get(0).name); // 小明
    }

    /// JsonObject / JsonArray（树模型操作）
    public static void testE(){
        String json = "{\"name\":\"王五\",\"skills\":[\"Java\",\"Android\"]}";

        // 解析为树模型
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        Log.i(Tag,"GoogleGson-testE-name:"+obj.get("name").getAsString());// 王五

        JsonArray skills = obj.getAsJsonArray("skills");
        for (int i = 0; i < skills.size(); i++) {
            Log.i(Tag,"GoogleGson-testE-skills:"+skills.get(i).getAsString());
        }
    }

    /// @SerializedName 注解（字段别名）
    public static void testF(){

        class Person {
            @SerializedName("user_name")
            String name;

            ///@SerializedName(value = "主字段名", alternate = {"别名1", "别名2", ...})
            /*value：序列化时使用的名字（即 Java 转 JSON 的输出 key）。
            alternate：反序列化时可接受的备用字段名（即 JSON → Java 时的可选 key）*/
            @SerializedName(value = "user_age", alternate = {"age", "years"})
            int age;
        }

        String json = "{\"user_name\":\"Tom\",\"years\":30}";
        Person p = new Gson().fromJson(json, Person.class);
        System.out.println(p.name); // Tom
        System.out.println(p.age);  // 30
    }

    /// 处理 null 值 & 默认值 (使用serializeNulls)
    public static void testG(){
        User user = new User();
        user.age = 20;
        user.active = true;

        // 使用serializeNulls()可以明确表示 "在序列化时保留 null"
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(user);
        Log.i(Tag,"GoogleGson-testG-json:"+json);
        // 输出结果: {"name":null,"age":20,"active":true}

        Log.i(Tag,"GoogleGson-testG-json2:"+(new Gson()).toJson(user));
        // 输出结果: {"age":20,"active":true}
    }

    /// 流式解析（大文件）
    public static void testH(){
        try {
            String json = "[{\"name\":\"A\"},{\"name\":\"B\"}]";
            JsonReader reader = new JsonReader(new StringReader(json));

            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                while (reader.hasNext()) {
                    String key = reader.nextName();
                    String value = reader.nextString();
                    System.out.println(key + "=" + value);
                }
                reader.endObject();
            }
            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
