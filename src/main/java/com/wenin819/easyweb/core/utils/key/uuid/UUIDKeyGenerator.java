package com.wenin819.easyweb.core.utils.key.uuid;


import com.wenin819.easyweb.core.utils.key.KeyGenerator;

import java.util.UUID;

/**
 * UUID型的主键生成策略
 * @author wenin819@gmail.com
 */
public class UUIDKeyGenerator implements KeyGenerator {
    @Override
    public String genNextKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
