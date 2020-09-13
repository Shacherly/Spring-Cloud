package com.hhhh;

import org.springframework.web.bind.annotation.*;

/**
 * UserAPI代表外部接口，通常是写死的，如果要扩展需要找原厂商提供方来扩展，
 * 但也不是没有办法，我自己的provider不是有个一继承该接口的接口嘛，我可以在自己的接口中进行扩展
 */
// @RequestMapping("/user") // 去掉mapping否则起不来
public interface UserAPI {

    /**
     * 提供么么哒服务
     * @return (づ ￣ 3 ￣)づ
     */
    @GetMapping("/alive")
    String alive();

    /**
     * @RequestParam("id") 还是必须得加的
     * @param id
     * @return
     */
    @GetMapping("/getById")
    String getById(@RequestParam("id") Integer id);

    /**
     * @RequestBody 好像可以不加
     * @param p
     * @return
     */
    @PostMapping("/postPerson")
    Person postPerson(/*@RequestBody*/ Person p);
}
