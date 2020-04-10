package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.mappers.*;
import org.apache.ibatis.session.SqlSession;

import net.thumbtack.school.hospital.database.utils.MyBatisUtils;

public class DaoImplBase {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected AdminMapper getAdminMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(AdminMapper.class);
    }

    protected DoctorMapper getDoctorMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DoctorMapper.class);
    }

    protected PatientMapper getPatientMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(PatientMapper.class);
    }

    protected ScheduleMapper getScheduleMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ScheduleMapper.class);
    }

    protected DayScheduleMapper getDayScheduleMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DayScheduleMapper.class);
    }

    protected CommissionMapper getCommissionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(CommissionMapper.class);
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }
}