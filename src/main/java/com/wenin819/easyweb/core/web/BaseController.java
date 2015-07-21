package com.wenin819.easyweb.core.web;

import com.wenin819.easyweb.core.utils.DateUtils;
import com.wenin819.easyweb.core.utils.StringUtils;
import com.wenin819.easyweb.core.utils.WebUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wenin819@gmail.com on 2014-09-02.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 添加Model消息
     * @param messages 消息集合
     */
    protected void addMessages(Model model, String... messages) {
        String msg = (String) model.asMap().get(WebUtils.MSG);
        StringBuilder sb = null == msg ? new StringBuilder() : new StringBuilder(msg).append("<br/>");
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        model.addAttribute(WebUtils.MSG, sb.toString());
    }

    /**
     * 添加Flash消息
     * @param messages 消息集合
     */
    protected void addMessages(RedirectAttributes redirectAttributes, String... messages) {
        String msg = (String) redirectAttributes.getFlashAttributes().get(WebUtils.MSG);
        StringBuilder sb = null == msg ? new StringBuilder() : new StringBuilder(msg).append("<br/>");
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        redirectAttributes.addFlashAttribute(WebUtils.MSG, sb.toString());
    }

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
