package com.example.demo.controller;

import com.example.demo.entity.Access;
import com.example.demo.entity.AuthenticationUser;
import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import com.example.demo.util.CommonResponseData;
import com.example.demo.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.entity.Role.*;

@RestController
@Controller
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping(Constant.URL_REGIST)
    @ResponseBody
    public CommonResponseData personRegister(@RequestBody Map<String,Object> jsonMap){
        CommonResponseData ret = new CommonResponseData();
        String username;
        String password;
        try{
            username = (String)jsonMap.get("username");
            password = (String)jsonMap.get("password");
        }catch (Exception e){
            ret.setMsg("参数不全:username,password");
            ret.setStatusCode(500);
            ret.setData(null);
            return ret;
        }

        Person newP = new Person();
        newP.setPersonName(username);
        newP.setPersonPwd(password);
        try {
            personService.registerPerson(newP);
        }catch (Exception e){
            ret.setMsg(e.getMessage());
            ret.setStatusCode(500);
            ret.setData(null);
            return ret;
        }

        ret.setMsg("注册成功");
        ret.setStatusCode(200);
        newP.setPersonPwd("");
        ret.setData(newP);
        return ret;
    }

    @PatchMapping(Constant.URL_PERSON)
    @ResponseBody
    public CommonResponseData updatePerson(@RequestBody Map<String,Object> jsonMap, HttpSession session) throws Exception{
        CommonResponseData ret = new CommonResponseData();
        int loginUserID = ((AuthenticationUser)
                (((SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication())
                .getPrincipal()).getId();
        Integer id;
        String password;

        try{
            id = (Integer) jsonMap.get("userid");
            password = (String) jsonMap.get("userpassword");
        }catch (Exception e){
            id = -1;
            ret.setMsg("参数不全 userid userpassword");
            ret.setStatusCode(500);
            ret.setData(null);
            return ret;
        }

        if ( loginUserID != id ){
            ret.setMsg("用户id错误");
            ret.setStatusCode(401);
            ret.setData(null);
            return ret;
        }

        personService.changePassword(id, password);
        ret.setMsg("更改密码成功");
        ret.setStatusCode(200);
        ret.setData(null);

        return ret;
    }

    @Secured("ROLE_人员管理")
    @PostMapping(Constant.URL_PERSON)
    @ResponseBody
    public CommonResponseData updatePerson(@RequestBody Map<String,Object> jsonMap) throws Exception{
        List<Integer> accessList = (List<Integer>)jsonMap.get("accessList");
        String rolename = (String)jsonMap.get("rolename");
        Integer userid = (Integer)jsonMap.get("userid");
        if ( userid == null )
            throw new Exception("userid不能为空");

        Person pp = personService.updatePerson(userid,rolename,accessList);
        CommonResponseData ret = new CommonResponseData();
        ret.setMsg("修改成功");
        ret.setStatusCode(200);
        ret.setData(pp);

        return ret;
    }

    @Secured("ROLE_人员管理")
    @GetMapping(Constant.URL_ACCESS)
    @ResponseBody
    public CommonResponseData getAccesses(){
        CommonResponseData ret = new CommonResponseData();
        List<Access> rr = new ArrayList<>();
        rr.add(accessControl);
        rr.add(addArticleControl);
        rr.add(editorArticleControl);
        rr.add(deleteArticleControl);
        rr.add(readControl);
        rr.add(channelControl);
        ret.setData(rr);
        ret.setMsg("ok");
        ret.setStatusCode(200);
        return ret;
    }

    @Secured("ROLE_人员管理")
    @GetMapping(Constant.URL_PERSON)
    @ResponseBody
    public CommonResponseData selectAllPersons(){
        CommonResponseData ret = new CommonResponseData();
        ret.setData( personService.getAllPersons() );
        ret.setStatusCode(200);
        ret.setMsg("获取成功");
        return ret;
    }
}
