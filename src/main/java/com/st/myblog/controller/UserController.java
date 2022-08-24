package com.st.myblog.controller;


import com.st.myblog.entity.User;
import com.st.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-08-23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/id")
    public Object test(@RequestParam(name = "id" ,defaultValue = "1") Long id){
        System.out.println("hello");
        return userService.getById(id);
    }

    //user/1   @PathVariable请求路径上值

    @GetMapping("/{id}")
    public Object test1(@PathVariable(value = "id") Long id){
        System.out.println("hello");
        return userService.getById(id);
    }


    @RequestMapping("/index")
    public void test2(){
        System.out.println("hello");
    }

    /**
     * 测试实体校验
     * @param user
     * @return
     */
    @PostMapping("/save")
    public Object testSave(@Validated @RequestBody User user){
        System.out.println("#################");
        return user.toString();
    }


}
