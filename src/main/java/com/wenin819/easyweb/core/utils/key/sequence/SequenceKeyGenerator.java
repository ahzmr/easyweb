package com.wenin819.easyweb.core.utils.key.sequence;

import com.wenin819.easyweb.core.utils.ConfigName;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.core.utils.SpringContextUtils;
import com.wenin819.easyweb.core.utils.key.KeyGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 序列的主键生成策略
 * @author wenin819@gmail.com
 */
public class SequenceKeyGenerator implements KeyGenerator {

    private Lock lock = new ReentrantLock();
    private JdbcTemplate jdbcTemplate = null;
    /**
     * 当前值
     */
    private long curVal = -1;
    /**
     * 已缓存的最大值
     */
    private long maxVal = -1;
    /**
     * 序列对应的表名
     */
    protected String tableName;
    /**
     * 主键表名
     */
    protected static final String KEY_SEQUENCE_TABLENAME = ConfigUtils.get().getValue(ConfigName.SCHAME_CONFIGPLAT,
            ConfigName.SCHAME_CONFIGPLAT_DEFVAL) + ".SYS_KEY_SEQUENCE";
    /**
     * 查询Sql
     */
    protected String selectSql = "SELECT VALUE FROM " + KEY_SEQUENCE_TABLENAME + " WHERE CODE = ?";
    /**
     * 插入Sql
     */
    protected String insertSql = "INSERT INTO " + KEY_SEQUENCE_TABLENAME + "(CODE, VALUE) VALUES (?, ?)";
    /**
     * 更新Sql，运用乐观锁机制
     */
    protected String updateSql = "UPDATE " + KEY_SEQUENCE_TABLENAME + " set VALUE = ? where CODE = ? and VALUE = ?";

    public SequenceKeyGenerator(String tableName) {
        Assert.notNull(tableName, "tableName不能为空！");
        tableName = tableName.toUpperCase();
        this.tableName = tableName;
    }

    @Override
    public String genNextKey() {
        lock.lock();
        try {
            if(curVal >= maxVal) {
                getAndUpdateKeyFromDB();
            }
            return String.valueOf(curVal++);
        } finally {
            lock.unlock();
        }
    }

    private void getAndUpdateKeyFromDB() {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        int cacheNum = ConfigUtils.get().getValue(ConfigName.SEQUENCE_KEY_CACHE_NUM,
                ConfigName.SEQUENCE_KEY_CACHE_NUM_DEFVAL);
        // 乐观锁尝试100次
        for (int i = 0; i < 100; i++) {
            List<String> strings = jdbcTemplate.queryForList(selectSql, new Object[]{tableName}, String.class);
            if(null == strings || strings.isEmpty()) {
                int defVal = ConfigUtils.get().getValue(ConfigName.SEQUENCE_KEY_DEFAULT_VALUE,
                        ConfigName.SEQUENCE_KEY_DEFAULT_VALUE_DEFVAL);
                int update = jdbcTemplate.update(insertSql, tableName, defVal);
                if(0 == update) {
                    continue;
                }
                curVal = defVal;
            } else {
                curVal = Long.valueOf(strings.get(0));
            }
            maxVal = curVal + cacheNum;
            int update = jdbcTemplate.update(updateSql, maxVal, tableName, curVal);
            if(0 < update) {
                return;
            }
        }
        throw new RuntimeException("采用乐观乐机制获取主键序列失败，表名为" + tableName);
    }

    public JdbcTemplate getJdbcTemplate() {
        if(null == jdbcTemplate) {
            jdbcTemplate = SpringContextUtils.getBean(JdbcTemplate.class);
        }
        return jdbcTemplate;
    }
}
