package com.wenin819.easyweb.core.utils.key;

import com.wenin819.easyweb.core.utils.Configs;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.core.utils.key.sequence.SequenceKeyGenerator;
import com.wenin819.easyweb.core.utils.key.uuid.UUIDKeyGenerator;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 主键生成工厂
 * @author wenin819@gmail.com
 */
public class KeyFactory {

    private static final ConcurrentMap<String, KeyGenerator> CONCURRENT_MAP =
            new ConcurrentHashMap<String, KeyGenerator>();

    public static KeyGenerator getKeyGenerator(String tableName) {
        Assert.notNull(tableName, "tableName不能为空！");
        tableName = tableName.toUpperCase();
        KeyGenerator keyGenerator = CONCURRENT_MAP.get(tableName);
        if(null != keyGenerator) {
            return keyGenerator;
        }
        synchronized (CONCURRENT_MAP) {
            keyGenerator = CONCURRENT_MAP.get(tableName);
            if(null != keyGenerator) {
                return keyGenerator;
            }
            String value = ConfigUtils.get().getValue(Configs.KEY_GENERATOR_TYPE,
                    Configs.KEY_GENERATOR_TYPE_DEFVAL);
            if("sequence".equalsIgnoreCase(value)) {
                keyGenerator = new SequenceKeyGenerator(tableName);
            } else {    // uuid
                keyGenerator = new UUIDKeyGenerator();
            }
            CONCURRENT_MAP.put(tableName, keyGenerator);
        }
        return keyGenerator;
    }

}
