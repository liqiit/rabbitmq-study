package com.ifreegroup.reliability;

import com.ifreegroup.reliability.confirm.PublishAckConfiguration;
import com.ifreegroup.reliability.transaction.TransactionConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Title: GlobalConfiguration
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/9
 */
@Configuration
@Import(value = {
        PublishAckConfiguration.class,
        TransactionConfig.class
})
public class GlobalConfiguration {
}
