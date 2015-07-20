package com.wenin819.easyweb.utils;

import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.utils.CacheUtils;
import com.wenin819.easyweb.core.utils.ConfigEnum;
import com.wenin819.easyweb.core.utils.SpringContextUtils;
import com.wenin819.easyweb.system.dao.SysDictDao;
import com.wenin819.easyweb.system.model.SysDict;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * @author wenin819@gmail.com
 */
public class SysDictUtils {
	
	private static SysDictDao sysDictDao = SpringContextUtils.getBean(SysDictDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";

    /**
     * 通过值获得对应的标签
     * @param value 值
     * @param type 字典类型
     * @param defaultValue 默认值
     * @return
     */
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (SysDict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

    /**
     * 通过标签获得对应的值
     * @param label 标签
     * @param type 字典类型
     * @param defaultLabel 默认值
     * @return
     */
	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (SysDict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}

	/**
	 * 得到字典缓存Map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static Map<String, List<SysDict>> getDictMapCache() {
		Map<String, List<SysDict>> dictMap = CacheUtils.get(CACHE_DICT_MAP, Map.class, null);
		if (dictMap != null) {
			return dictMap;
		}
		synchronized (CACHE_DICT_MAP) {
			dictMap = CacheUtils.get(CACHE_DICT_MAP, Map.class, null);
			if (dictMap != null) {
				return dictMap;
			}

			// 初始化总缓存
			dictMap = new HashMap<String, List<SysDict>>();
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		return dictMap;
	}

    /**
     * 得到此字典类型的字典列表
     * @param type 字典类型
     * @return
     */
	public static List<SysDict> getDictList(String type){
        Map<String, List<SysDict>> dictMap = getDictMapCache();

		List<SysDict> dictList = dictMap.get(type);
		if(null != dictList) {
			return dictList;
		}

		synchronized (CACHE_DICT_MAP) {
			dictList = dictMap.get(type);
			if(null != dictList) {
				return dictList;
			}

			CriteriaQuery query = new CriteriaQuery();
			query.createAndCriteria().equalTo(SysDict.TE.delFlag, ConfigEnum.DEL_FLAG_NORMAL);
			query.createAndCriteria().equalTo(SysDict.TE.type, type);
			query.addOrder(SysDict.TE.sort, true);
			dictList = sysDictDao.queryByCriteria(query);

			if (dictList == null){
				dictList = new ArrayList<SysDict>(0);
			}

			dictMap.put(type, dictList);
		}
		return dictList;
	}

    /**
     * 得到此字典类型的值与标签的映射关系
     * @param type 字典类型
     * @return
     */
    public static Map<String, String> getValue2LabelMap(String type) {
        List<SysDict> dictList = getDictList(type);
        Map<String, String> map = new HashMap<String, String>();
        if(null != dictList) {
            for (SysDict dict : dictList) {
                map.put(dict.getType(), dict.getLabel());
            }
        }
        return map;
    }

    /**
     * 得到此字典类型的标签与值的映射关系
     * @param type 字典类型
     * @return
     */
    public static Map<String, String> getLabel2ValueMap(String type) {
        List<SysDict> dictList = getDictList(type);
        Map<String, String> map = new HashMap<String, String>();
        if(null != dictList) {
            for (SysDict dict : dictList) {
                map.put(dict.getLabel(), dict.getValue());
            }
        }
        return map;
    }

	/**
	 * 根据字典Type 重新加载Type下的字典值
	 * @param type 字典类型
	 */
	public static void removeDictTypeCache(String type) {
		getDictMapCache().remove(type);
	}
}

