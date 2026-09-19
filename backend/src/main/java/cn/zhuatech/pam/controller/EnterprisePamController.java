/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pam.controller;
import cn.zhuatech.pam.common.ApiResponse; import cn.zhuatech.pam.service.EnterprisePamService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/pam") public class EnterprisePamController {
 private final EnterprisePamService service; /**
                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                              */
public EnterprisePamController(EnterprisePamService service){this.service=service;}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @PostMapping("/authorize-access") ApiResponse<?> execute(@Valid @RequestBody EnterprisePamService.AccessRequest request){return ApiResponse.ok(service.authorize(request));}
}

