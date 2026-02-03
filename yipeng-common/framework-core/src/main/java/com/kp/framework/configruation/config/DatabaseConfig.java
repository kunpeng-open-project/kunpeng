package com.kp.framework.configruation.config;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Iterator;
import java.util.Map;

@Configuration
@Qualifier("myDataSourceConfig")
@Primary
public class DatabaseConfig extends DynamicDataSourceProperties {
    @Override
    public void setDatasource(Map<String, DataSourceProperty> datasource) {
        Iterator iterator = datasource.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            DataSourceProperty dataSourceProperty = datasource.get(key);
            dataSourceProperty.setPassword(dataSourceProperty.getPassword());
        }
        super.setDatasource(datasource);
    }

    //    @Autowired
//    private DweEncryptionUtil dweEncryptionUtil;
//
//    @Autowired
//    private FrameworkEncryptionProperties frameworkProperties;

//    @Override
//    public void setDatasource(Map<String, DataSourceProperty> datasource) {
////        if (frameworkProperties.getDatabase()){
//            Iterator iterator = datasource.keySet().iterator();
//            while (iterator.hasNext()){
//                String key = (String) iterator.next();
//                DataSourceProperty dataSourceProperty = datasource.get(key);
//                dataSourceProperty.setPassword(dataSourceProperty.getPassword());
//            }
//            super.setDatasource(datasource);
////        }else{
////            super.setDatasource(datasource);
////        }
//    }
}
