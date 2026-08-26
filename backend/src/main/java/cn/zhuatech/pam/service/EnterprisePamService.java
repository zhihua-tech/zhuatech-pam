/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pam.service;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.stereotype.Service; import java.util.*;
@Service public class EnterprisePamService {
 private static final Set<String> FORBIDDEN=Set.of("rm -rf /","DROP DATABASE","shutdown -h","format c:");
 public AccessResult authorize(@Valid AccessRequest r){
  List<String> blockers=new ArrayList<>(); String risk=r.riskLevel().toUpperCase(Locale.ROOT);
  if(!r.mfaVerified()) blockers.add("MFA 校验未通过"); if(r.ticketNo().isBlank()) blockers.add("缺少变更或工单编号"); if(r.durationMinutes()>480) blockers.add("授权时长超过8小时上限"); if("HIGH".equals(risk)&&r.approverCount()<2) blockers.add("高风险访问需要双人审批"); if(!List.of("LOW","MEDIUM","HIGH").contains(risk)) blockers.add("风险等级无效");
  boolean commandBlocked=r.requestedCommands().stream().map(String::trim).anyMatch(c->FORBIDDEN.stream().anyMatch(f->c.equalsIgnoreCase(f))); if(commandBlocked) blockers.add("申请包含禁止执行的高危命令");
  int sessionLimit="HIGH".equals(risk)?60:Math.min(r.durationMinutes(),480);
  return new AccessResult(r.requestNo(),r.resourceNo(),sessionLimit,true,blockers,blockers.isEmpty()?"APPROVED_JIT":"DENIED");
 }
 public record AccessRequest(@NotBlank String requestNo,@NotBlank String resourceNo,@NotBlank String riskLevel,@Min(1) int durationMinutes,boolean mfaVerified,@NotNull String ticketNo,@Min(0) int approverCount,@NotNull Set<String> requestedCommands){}
 public record AccessResult(String requestNo,String resourceNo,int sessionLimitMinutes,boolean credentialMasked,List<String> blockers,String decision){}
}

