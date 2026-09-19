/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pam.config;

import cn.zhuatech.pam.domain.DomainCatalog;
import cn.zhuatech.pam.model.*;
import cn.zhuatech.pam.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration
public class DataInitializer {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Bean
    CommandLineRunner seed(BusinessRecordRepository records, SystemSettingRepository settings, DomainCatalog catalog) {
        return args -> {
            if (records.count() > 0) return;
        settings.save(new SystemSetting("defaultSessionMinutes", "60"));
        settings.save(new SystemSetting("maxSessionMinutes", "480"));
        settings.save(new SystemSetting("mfaRequired", "true"));
        settings.save(new SystemSetting("credentialRotation", "会话后"));
            int sequence = 1;
            for (var module : catalog.modules()) {
                String no = "PAM-DEMO-" + String.format("%03d", sequence);
                records.save(new BusinessRecord(
                    no, module.code(), module.name() + "标准业务事项", "上海总部",
                    sequence % 3 == 0 ? "内控经理" : "业务专员", catalog.initialStatus(),
                    BigDecimal.valueOf(sequence * 12500L), sequence * 2,
                    LocalDate.now().plusDays(sequence * 3L), sequence % 4 == 0 ? "关注" : "正常",
                    module.description() + "；用于演示完整台账、状态流、权限和审计能力"));
                sequence++;
            }
        };
    }
}

