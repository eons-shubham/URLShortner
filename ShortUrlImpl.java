package com.crio.shorturl;
import java.math.BigInteger;
import java.util.*;

public class ShortUrlImpl implements ShortUrl {
    Map<String, String> map;
    Map<String, Integer> histogram;
    Queue<String> free;
    static Long COUNTER = 0L;

    public ShortUrlImpl () {
        histogram = new HashMap<>();
        map = new HashMap<>();
        free = new LinkedList<>();
    }
    private static String formatter (String output) {
        output = "http://short.url/" +("000000000"+output).substring(output.length());
        return output;
    }
    @Override
    public String registerNewUrl(String longUrl) {
        if (map.containsKey(longUrl)) {
            histogram.put(longUrl, histogram.get(longUrl) + 1);
            return map.get(longUrl);
        }
        else if (!free.isEmpty()) {
            map.put(longUrl, free.poll());
            map.get(longUrl);
            return map.get(longUrl);
        }
        else {
            Long longval = COUNTER++;
            map.put (longUrl, formatter(BigInteger.valueOf(longval).toString(36)));
            histogram.put(longUrl, 0);
            return map.get(longUrl);
        }

    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if (map.values().contains(shortUrl))
            return null;
        map.put(longUrl, shortUrl);
        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        for (Map.Entry<String, String> entry: map.entrySet()) {
            if (entry.getValue().equals(shortUrl)) {
                return entry.getKey();
            }

        }
        return null;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        // TODO Auto-generated method stub
        if (histogram.containsKey(longUrl)) histogram.put(longUrl, histogram.get(longUrl) + 1);
        else histogram.put(longUrl, 0);
        return histogram.get(longUrl);
    }

    @Override
    public String delete(String longUrl) {
       map.remove(longUrl);
       return null;
    }

}
