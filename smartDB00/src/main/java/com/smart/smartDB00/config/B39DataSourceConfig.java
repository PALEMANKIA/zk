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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.smart.smartDB00.dao.b39", sqlSessionFactoryRef = "b39SqlSessionFactory")
public class B39DataSourceConfig {

    // 表示这个数据源是默认数据源
    @Primary
    // 将这个对象放入Spring容器中
    @Bean(name = "b39DataSource")
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.b39")
    public DataSource getB39DateSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "b39SqlSessionFactory")
    // 表示这个数据源是默认数据源
    @Primary
    public SqlSessionFactory b39SqlSessionFactory(@Qualifier("b39DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/b39/*.xml"));
        return bean.getObject();
    }

    @Bean("b39SqlSessionTemplate")
    // 表示这个数据源是默认数据源
    @Primary
    public SqlSessionTemplate b39SqlSessionTemplate (
            @Qualifier("b39SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
