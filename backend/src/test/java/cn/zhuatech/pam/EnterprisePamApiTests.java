/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pam;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic; import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@SpringBootTest @AutoConfigureMockMvc class EnterprisePamApiTests { @Autowired MockMvc mvc;

 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void compliantJitAccessIsApproved() throws Exception {mvc.perform(post("/api/enterprise/pam/authorize-access").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content("""
 {"requestNo":"PAM-001","resourceNo":"DB-PROD-01","riskLevel":"HIGH","durationMinutes":120,"mfaVerified":true,"ticketNo":"CHG-001","approverCount":2,"requestedCommands":["select 1"]}
 """)).andExpect(status().isOk()).andExpect(jsonPath("$.data.sessionLimitMinutes").value(60)).andExpect(jsonPath("$.data.credentialMasked").value(true)).andExpect(jsonPath("$.data.decision").value("APPROVED_JIT"));}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void highRiskCommandAndMissingControlsAreDenied() throws Exception {mvc.perform(post("/api/enterprise/pam/authorize-access").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content("""
 {"requestNo":"PAM-002","resourceNo":"OS-PROD-01","riskLevel":"HIGH","durationMinutes":600,"mfaVerified":false,"ticketNo":"","approverCount":1,"requestedCommands":["rm -rf /"]}
 """)).andExpect(status().isOk()).andExpect(jsonPath("$.data.decision").value("DENIED")).andExpect(jsonPath("$.data.blockers.length()").value(5));}
}

