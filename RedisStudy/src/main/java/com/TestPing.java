package com;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestPing {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("124.71.29.134",6379);
        jedis.auth("123456");
        System.out.println(jedis.ping());
        JSONObject jsonObject = new JSONObject();
        jedis.flushDB();
        jsonObject.put("name","wjh");
        jsonObject.put("password","123456");
        Transaction transaction = jedis.multi();
        String res = jsonObject.toJSONString();
        try {
            transaction.set("user1",res);
            transaction.set("user2",res);
            int i = 1/0;
            transaction.exec();
        }catch (Exception e) {
            transaction.discard();//放弃事务
            e.printStackTrace();
        }finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close();
        }


    }
}
