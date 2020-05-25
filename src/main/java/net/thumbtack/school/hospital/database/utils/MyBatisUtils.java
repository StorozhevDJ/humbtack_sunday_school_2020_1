package net.thumbtack.school.hospital.database.utils;

import net.thumbtack.school.hospital.database.mappers.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.Reader;


public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    public static boolean initSqlSessionFactory() {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

// 		instead of setting here, it is possible to set in configuration XML file
//      sqlSessionFactory.getConfiguration().setAggressiveLazyLoading(false);

            return true;
        } catch (Exception e) {
            LOGGER.error("Error loading mybatis-config.xml", e);
            return false;
        }
    }

    /*public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }*/

    @Bean
    public static SqlSessionFactory getSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        SqlSessionFactory sessionFactory = factoryBean.getObject();
        sessionFactory.getConfiguration().addMapper(UserMapper.class);
        return sessionFactory;
    }

    public static DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder.build();
    }



}