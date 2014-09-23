package com.wenin819.easyweb.modules.webclient.controller;

import com.wenin819.easyweb.core.web.BaseController;
import com.wenin819.easyweb.modules.webclient.service.WebClientService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by wenin819@gmail.com on 2014-08-26
 */
@Controller
@RequestMapping("/webclient/")
public class WebClientController extends BaseController {

    @Resource
    private WebClientService webClientService;

    @RequestMapping({"", "index.html"})
    public String index(Model model) {
        model.addAttribute("idList", webClientService.genNewHttpContextIdList());
        model.addAttribute("userId", webClientService.userId);
        return "modules/webclient/index";
    }

    @RequestMapping("getImage.html")
    public void getImage(String id, HttpServletResponse response) throws IOException {
        final byte[] image = webClientService.getImage(id);

        response.setHeader("Content-Type", "Image/BMP");
        response.setContentLength(image.length);
        final ServletOutputStream out = response.getOutputStream();
        out.write(image);
        out.flush();
    }

    @RequestMapping("commit.json")
    @ResponseBody
    public Map<String, Object> commit(String id, String code) {
        Map<String, Object> rs = new HashMap<>();
        String msg = null;
        int status = 0;
        try {
            msg = webClientService.commit(id, code);
            status = 1;
        } catch (Exception e) {
            msg = e.getMessage();
            e.printStackTrace();
        }
        rs.put("status", status);
        rs.put("msg", msg);
        rs.put("id", id);
        return rs;
    }
}
