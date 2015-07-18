package com.wenin819.easyweb.core.web;

import com.wenin819.easyweb.core.utils.DateUtils;
import com.wenin819.easyweb.core.utils.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wenin819@gmail.com on 2014-09-02.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder, WebRequest webRequest) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(null, true));
        dataBinder.registerCustomEditor(Date.class,
                new PropertyEditorSupport() {
                    @Override
                    public void setAsText(String text) throws IllegalArgumentException {
                        if(StringUtils.isBlank(text)) {
                            setValue(null);
                        } else {
                            try {
                                setValue(DateUtils.parseDate(text, DateUtils.DATE_FORMATS));
                            } catch (ParseException e) {
                                logger.warn("转换日期参数出错！", e);
                                setValue(null);
                            }
                        }
                    }

                    @Override
                    public String getAsText() {
                        if(null == getValue()) {
                            return null;
                        } else {
                            return DateFormatUtils.format((Date) getValue(), DateUtils.DATE_FORMAT);
                        }
                    }
                });
    }

}
