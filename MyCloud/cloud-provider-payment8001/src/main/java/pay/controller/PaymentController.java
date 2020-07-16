package pay.controller;


import entity.CommonResult;
import entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import pay.service.PaymentService;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;


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
    @GetMapping(path = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for(String item:services){
            log.info("****:"+item);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance:instances){
            log.info("===="+instance.getInstanceId()+","+instance.getHost()+","+instance.getPort()+","+instance.getUri());
        }
        return this.discoveryClient;
    }
    
}
