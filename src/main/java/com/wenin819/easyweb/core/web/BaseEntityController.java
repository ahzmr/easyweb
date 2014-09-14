package com.wenin819.easyweb.core.web;

import com.wenin819.easyweb.core.db.BaseEntity;
import com.wenin819.easyweb.core.db.BaseService;
import com.wenin819.easyweb.core.db.CriteriaQuery;
import com.wenin819.easyweb.core.db.Page;
import com.wenin819.easyweb.core.util.StringUtils;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wenin819@gmail.com on 2014-09-02.
 */
public abstract class BaseEntityController <E extends BaseEntity> {


    /**
     * 得到页面路径
     * @return
     */
    protected abstract String getBasePagePath();

    /**
     * 查询页面路径
     */
    protected final String pagePathList = getBasePagePath() + "List";
    /**
     * 查看修改页面路径
     */
    protected final String pagePathForm = getBasePagePath() + "Form";

    /**
     * 得到对应service实现
     * @return
     */
    protected abstract BaseService<E> getService();

    /**
     * 生成分布查询条件
     * @param entity
     * @param request
     * @return
     */
    protected CriteriaQuery genCriteriaes(E entity, HttpServletRequest request) {
        return new CriteriaQuery();
    }

    /**
     * 更新操作实体
     * @param entity    实体
     * @param type  操作类型
     * @param request
     * @return
     */
    protected E updateEntity(E entity, ActionType type, HttpServletRequest request) {
        return entity;
    }

    @ModelAttribute
    public E getEntryById(@RequestParam(required = false) String id) {
        E entry = null;
        if(!StringUtils.isBlank(id)) {
            entry = getService().queryById(id);
        }
        if(null == entry) {
            entry = getService().createEntity();
        }
        return entry;
    }

    /**
     * 跳转分页查询页面
     * @param page  分页信息
     * @param entity
     * @param model
     * @param request
     * @return
     */
    @RequestMapping({"/list.html", ""})
    public String toList(Page<E> page, E entity, Model model, HttpServletRequest request) {
        entity = updateEntity(entity, ActionType.SELECT, request);
        CriteriaQuery example = genCriteriaes(entity, request);
        final List<E> list = getService().queryByCriteria(example);
        model.addAttribute("list", list);
        return pagePathList;
    }

    /**
     * 跳转查看修改页面
     * @param entry
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/form.html")
    public String toForm(E entry, Model model, HttpServletRequest request) {
        model.addAttribute("entry", entry);
        return pagePathForm;
    }

    /**
     * 修改处理
     * @param entity
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public String save(E entity, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        entity = updateEntity(entity, ActionType.SAVE, request);
        final int success = getService().createOrUpdate(entity);
        if(success > 0) {
            redirectAttributes.addAttribute("message", "保存成功");
            return "redirect:./list.html";
        } else {
            model.addAttribute("message", "保存失败，请重试");
            model.addAttribute("entry", entity);
            return pagePathForm;
        }
    }
}
