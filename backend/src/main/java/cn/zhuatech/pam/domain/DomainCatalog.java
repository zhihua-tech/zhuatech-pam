/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pam.domain;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DomainCatalog {
    private final Map<String, WorkflowAction> actions = new LinkedHashMap<>();

    public DomainCatalog() {
        actions.put("APPROVE", new WorkflowAction("APPROVE", "批准授权", List.of("已申请"), "已批准", "ADMIN"));
        actions.put("ACTIVATE", new WorkflowAction("ACTIVATE", "启动会话", List.of("已批准"), "会话中", "OPERATOR"));
        actions.put("SUSPEND", new WorkflowAction("SUSPEND", "暂停会话", List.of("会话中"), "已暂停", "ADMIN"));
        actions.put("RESUME", new WorkflowAction("RESUME", "恢复会话", List.of("已暂停"), "会话中", "ADMIN"));
        actions.put("REVOKE", new WorkflowAction("REVOKE", "回收权限", List.of("已批准","会话中","已暂停"), "已回收", "ADMIN"));
    }

    public String systemName() { return "知华科技特权访问管理 PAM"; }
    public String scene() { return "特权账号保管、授权申请、审批、JIT 会话、命令控制、轮换与审计"; }
    public String initialStatus() { return "已申请"; }
    public String partyLabel() { return "账号/资源"; }
    public String amountLabel() { return "风险敞口"; }
    public String quantityLabel() { return "授权时长"; }
    public String dueLabel() { return "授权截止时间"; }

    public List<ModuleDefinition> modules() {
        return List.of(
            new ModuleDefinition("VAULT", "凭据保险库", "加密保管密码、密钥、令牌并实施密钥分层"),
            new ModuleDefinition("ACCOUNT", "特权账号", "发现、纳管、分级和认证特权账号"),
            new ModuleDefinition("POLICY", "访问策略", "配置最小权限、时段、MFA、工单和双人审批"),
            new ModuleDefinition("REQUEST", "访问申请", "按资源、目的和时间提交临时权限申请"),
            new ModuleDefinition("APPROVAL", "授权审批", "执行风险分级、职责分离与多级审批"),
            new ModuleDefinition("SESSION", "会话代理", "通过代理建立 JIT 会话并隐藏真实凭据"),
            new ModuleDefinition("RECORDING", "会话录制", "录屏、键盘审计、检索与防篡改留存"),
            new ModuleDefinition("ROTATION", "凭据轮换", "定期或会话后自动轮换并校验可用性"),
            new ModuleDefinition("COMMAND_CONTROL", "命令控制", "拦截高危命令并支持审批放行"),
            new ModuleDefinition("AUDIT", "合规审计", "形成账号、授权、会话、命令和导出审计链")
        );
    }

    public Map<String, WorkflowAction> actions() { return Collections.unmodifiableMap(actions); }

    public record ModuleDefinition(String code, String name, String description) {}
    public record WorkflowAction(String code, String label, List<String> from, String to, String requiredRole) {}
}
