package com.bugull.mongo.cache;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Frank Wen(xbwen@hotmail.com)
 */
public class ConstructorCache {
    
    private final static Logger logger = Logger.getLogger(ConstructorCache.class);
    
    private static ConstructorCache instance;
    
    private Map<String, Constructor> cache;
    
    private ConstructorCache(){
        cache = new ConcurrentHashMap<String, Constructor>();
    }
    
    public static ConstructorCache getInstance(){
        if(instance == null){
            instance = new ConstructorCache();
        }
        return instance;
    }
    
    private Constructor getConstructor(Class<?> clazz){
        Constructor cons = null;
        String name = clazz.getName();
        if(cache.containsKey(name)){
            cons = cache.get(name);
        }else{
            Class[] types = null;
            try{
               cons = clazz.getConstructor(types); 
            }catch(Exception e){
                logger.error(e.getMessage());
            }
            cache.put(name, cons);
        }
        return cons;
    }
    
    public Object createObject(Class<?> clazz){
        Object obj = null;
        Constructor cons = getConstructor(clazz);
        Object[] args = null;
        try {
            obj = cons.newInstance(args);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return obj;
    }
    
}