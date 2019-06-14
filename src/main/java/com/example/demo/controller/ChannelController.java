package com.example.demo.controller;

import com.example.demo.entity.Channel;
import com.example.demo.service.ChannelService;
import com.example.demo.util.CommonResponseData;
import com.example.demo.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.demo.util.Tools.strIsEmpty;

@Controller
public class ChannelController {

    @Autowired
    ChannelService channelService;

    @Secured("ROLE_管理栏目")
    @PostMapping(Constant.URL_CHANNEL)
    @ResponseBody
    public CommonResponseData addChannel(@RequestBody Map<String,String> jsonMap){
        CommonResponseData ret = new CommonResponseData();
        String title = jsonMap.get("title");
        String desc = jsonMap.get("desc");
        if ( strIsEmpty( title ) || strIsEmpty( desc ) ){
            ret.setData(null);
            ret.setMsg("参数不能空 title desc");
            ret.setStatusCode(500);
            return ret;
        }
        Channel ch = channelService.addChannelService(title,desc);
        ret.setStatusCode(200);
        ret.setMsg("栏目添加成功");
        ret.setData(ch);
        return ret;
    }

    @Secured("ROLE_管理栏目")
    @DeleteMapping(Constant.URL_CHANNEL)
    @ResponseBody
    public CommonResponseData delChannel(@RequestBody Map<String,Object> jsonMap){
        CommonResponseData ret = new CommonResponseData();
        Integer id = (Integer)jsonMap.get("channelid");
        try {
            if (id != null) {
                channelService.removeChannel(id);
            } else {
                throw new Exception("参数为空");
            }
        }catch (Exception e){
            ret.setMsg(e.getMessage());
            ret.setStatusCode(500);
            return ret;
        }
        ret.setStatusCode(200);
        ret.setMsg("删除成功");
        ret.setData(null);
        return ret;
    }

    @Secured("ROLE_管理栏目")
    @GetMapping(Constant.URL_CHANNEL)
    @ResponseBody
    public CommonResponseData getChannels(){
        CommonResponseData ret = new CommonResponseData();
        ret.setMsg("success");
        ret.setStatusCode(200);
        ret.setData(channelService.getAllChannels());
        return ret;
    }

}
