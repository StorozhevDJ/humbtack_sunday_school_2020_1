package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.mappers.*;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

// REVU все проще
// @MapperScan даст Вам все мапперы из пакета
// посмотрите его исходники, а также
// https://www.baeldung.com/spring-mybatis
// и нужно будет лишь 
// @Autowired
// public AnyDaoImpl(AnyMapper anyMapper) {
//    this.anyMapper = anyMapper;
// }


public class DaoImplBase {

    public SqlSession getSession() {
        try {
            return getSqlSessionFactory().openSession();
        } catch (Exception e) {
            return null;
        }
    }

    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder.build();
    }

    private SqlSessionFactory getSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        SqlSessionFactory sessionFactory = factoryBean.getObject();
        sessionFactory.getConfiguration().addMapper(UserMapper.class);
        return sessionFactory;
    }

    @Bean
    protected AdminMapper getAdminMapper(SqlSession sqlSession) throws ServerException {
        try {
            return new SqlSessionTemplate(getSqlSessionFactory()).getMapper(AdminMapper.class);
        } catch (Exception e) {
            return null;
        }
        //return sqlSession.getMapper(AdminMapper.class);
    }

    @Bean
    protected DoctorMapper getDoctorMapper(SqlSession sqlSession) {
        //return sqlSession.getMapper(DoctorMapper.class);
        try {
            return new SqlSessionTemplate(getSqlSessionFactory()).getMapper(DoctorMapper.class);
        } catch (Exception e) {
            return null;
        }
    }

    //@Bean
    protected PatientMapper getPatientMapper(SqlSession sqlSession) {
        //return sqlSession.getMapper(PatientMapper.class);
        try {
            return new SqlSessionTemplate(getSqlSessionFactory()).getMapper(PatientMapper.class);
        } catch (Exception e) {
            return null;
        }
    }

    //@Bean
    protected DayScheduleMapper getScheduleMapper(SqlSession sqlSession) {
        //return sqlSession.getMapper(DayScheduleMapper.class);
        try {
            return new SqlSessionTemplate(getSqlSessionFactory()).getMapper(DayScheduleMapper.class);
        } catch (Exception e) {
            return null;
        }
    }

    //@Bean
    protected TicketScheduleMapper getDayScheduleMapper(SqlSession sqlSession) {
        //return sqlSession.getMapper(TicketScheduleMapper.class);
        try {
            return new SqlSessionTemplate(getSqlSessionFactory()).getMapper(TicketScheduleMapper.class);
        } catch (Exception e) {
            return null;
        }
    }

    //@Bean
    protected CommissionMapper getCommissionMapper(SqlSession sqlSession) {
        //return sqlSession.getMapper(CommissionMapper.class);
        try {
            return new SqlSessionTemplate(getSqlSessionFactory()).getMapper(CommissionMapper.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
    protected UserMapper getUserMapper(SqlSession sqlSession) {
        //return sqlSession.getMapper(UserMapper.class);
        try {
            return new SqlSessionTemplate(getSqlSessionFactory()).getMapper(UserMapper.class);
        } catch (Exception e) {
            return null;
        }
    }
}