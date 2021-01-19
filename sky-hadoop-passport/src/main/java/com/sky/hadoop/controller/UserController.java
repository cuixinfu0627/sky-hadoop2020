package com.sky.hadoop.controller;

import com.sky.hadoop.entity.OrderEntity;
import com.sky.hadoop.service.UserService;
import com.sky.hadoop.view.PageUtils;
import com.sky.hadoop.view.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 用户 使用RestTemplate调用
 * @author live
 * @email 870459550@qq.com
 * @date 2020-12-19 15:36:10
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Value("${service-url.nacos-order-service}")
    private String invoke_url; //http://order-service

    @Autowired
    private UserService userService;

    /**
     * @title: 获取用户列表 <tb>
     * @author: cuixinfu@51play.com <tb>
     * @date: 2021/1/12 11:40<tb>
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * @title: 雪花算法生成id <tb>
     * @author: cuixinfu@51play.com <tb>
     * @date: 2021/1/12 11:40<tb>
     */
    @RequestMapping("/snowflake")
    public R snowflake(){
        String snowflakeId  = userService.getIDBySnowFlake();
        return R.ok().put("id",snowflakeId);
    }

    /** ===================远程调用模块=================== **/
    @Resource
    private RestTemplate restTemplate;

    /**
     * http://127.0.0.1:8002/order/list
     */
    @GetMapping("/order/list")
    public R orderList(){

        R result = restTemplate.getForObject(invoke_url + "/order/list", R.class);

        return result;
    }

    /**
     * http://127.0.0.1:8002/order/info/1001
     */
    @GetMapping("/order/info/{id}")
    public R info(@PathVariable("id") Long id){

        R result = restTemplate.getForObject(invoke_url + "/order/info/"+id, R.class);

        return result;
    }

    /**http://127.0.0.1:8002/order/create
     * {
     *     "payment": 790000,
     *     "orderNum": "1235649864",
     *     "itemId": "1001",
     *     "itemTitle": "40g海婷海带丝（25包一箱）卖9.9",
     *     "userId": 1,
     *     "username": "孙行者",
     *     "mobile": "15010089114",
     *     "address": "花果山水帘洞齐天大圣美猴王孙悟空",
     *     "message": "充数下班侧",
     *     "status": 1
     * }
     * @param order
     * @return
     */
    @PostMapping("/order/create")
    public R create(@RequestBody OrderEntity order){
        R result = restTemplate.postForObject(invoke_url + "/order/create", order,R.class);
        return result;
    }

    // zipkin+sleuth
    @GetMapping("/order/zipkin")
    public String paymentzipkin(){
        String result=restTemplate.getForObject(invoke_url + "/order/zipkin/", String.class);
        return result;
    }


}
