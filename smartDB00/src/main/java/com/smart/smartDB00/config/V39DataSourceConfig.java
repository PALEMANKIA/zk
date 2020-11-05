package com.smart.smartDB00.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.smart.smartDB00.dao.v39", sqlSessionFactoryRef = "v39SqlSessionFactory")
public class V39DataSourceConfig {
    // 将这个对象放入Spring容器中
    @Bean(name = "v39DataSource")
    // 表示这个数据源是默认数据源
    @ConfigurationProperties(prefix = "spring.datasource.v39")
    public DataSource getV39DateSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "v39SqlSessionFactory")
    public SqlSessionFactory v39SqlSessionFactory(@Qualifier("v39DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/v39/*.xml"));
        return bean.getObject();
    }

    @Bean("v39SqlSessionTemplate")
    public SqlSessionTemplate v39SqlSessionTemplate (
            @Qualifier("v39SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
