package cn.crawler.split.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * Created by liang.liu04@hand-china.com
 * on 2018/6/30
 */
@Configuration
@EnableConfigurationProperties({ DataSourceProperties.class, MybatisProperties.class})
public class DatabaseConfiguration {
    private final DataSourceProperties dataSourceProperties;
    private final MybatisProperties properties;
    private final ResourceLoader resourceLoader;
    @Autowired(required = false)
    private Interceptor[] interceptors;
    public DatabaseConfiguration(DataSourceProperties dataSourceProperties, MybatisProperties properties, ResourceLoader resourceLoader) {
        this.dataSourceProperties = dataSourceProperties;
        this.properties = properties;
        this.resourceLoader = resourceLoader;
    }

    /**
     * 注册数据源，使用阿里巴巴的一个实现类
     * @return
     */
    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());

        return dataSource;
    }


    /**
     * 注册sqlSession
     * @return
     */
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(){
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource());
        mybatisSqlSessionFactoryBean.setVfs(SpringBootVFS.class);

        //sqlsession实体类
        if(StringUtils.hasLength(properties.getTypeAliasesPackage())){
            mybatisSqlSessionFactoryBean.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }

        //typeHandle包
        if(StringUtils.hasLength(properties.getTypeHandlersPackage())){
            mybatisSqlSessionFactoryBean.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }


        if(StringUtils.hasText(properties.getConfigLocation())){
            mybatisSqlSessionFactoryBean.setConfigLocation(this.resourceLoader.getResource(properties.getConfigLocation()));
        }

        //扫描mapper.xml包
        Resource[] resources = this.properties.resolveMapperLocations();
        if(!ObjectUtils.isEmpty(resources)){
            mybatisSqlSessionFactoryBean.setMapperLocations(resources);
        }


        //扫描自定义的拦截器
        if(ObjectUtils.isEmpty(this.interceptors)){
            mybatisSqlSessionFactoryBean.setPlugins(interceptors);
        }
        return mybatisSqlSessionFactoryBean;
    }


}
