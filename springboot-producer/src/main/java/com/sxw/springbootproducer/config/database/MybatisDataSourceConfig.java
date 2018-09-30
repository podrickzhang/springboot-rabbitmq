package com.sxw.springbootproducer.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@AutoConfigureAfter(DruidDataSourceConfig.class)
public class MybatisDataSourceConfig {

    @Autowired
    private DataSource dataSource;

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
         //   bean.setMapperLocations(resolver.getResources("classpath:com/sxw/springbootproducer/mapping/*.xml"));
          //  bean.setMapperLocations(new Resource[]{resolver.getResource("classpath:com/sxw/springbootproducer/mapping/BrokerMessageLogMapper.xml")},{"classpath:com/sxw/springbootproducer/mapping/OrderMapper.xml"});
            bean.setMapperLocations(new Resource[]{resolver.getResource("classpath:mapping/BrokerMessageLogMapper.xml"),resolver.getResource("classpath:mapping/OrderMapper.xml")});
            SqlSessionFactory sqlSessionFactory = bean.getObject();
            sqlSessionFactory.getConfiguration().setCacheEnabled(Boolean.TRUE);

            return sqlSessionFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

