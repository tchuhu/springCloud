package pay.controller;


import entity.CommonResult;
import entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import pay.service.PaymentService;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;


    @PostMapping(path = "/payment/insert")
    public CommonResult create(@RequestBody Payment payment){

        int i = paymentService.insert(payment);

        log.info("******插入结果是啥呢,port:"+serverPort+""+i);
        if(i >0)   {
            return new CommonResult(200,"插入数据库成功!");
        }else{
            return new CommonResult(444,"插入数据库失败!");
        }
    }

    @GetMapping(path = "/payment/get/{id}")
    public CommonResult find(@PathVariable("id") Long id){
        Payment payment = paymentService.selectByPrimaryKey(id);
        log.info("******查询结果:"+payment);
        if(payment!= null){
            return new CommonResult(200,"查询成功!,port:"+serverPort,payment);
        }else{
            return new CommonResult(444,"没有对应记录,查询ID!"+id,payment);
        }
    }

}
