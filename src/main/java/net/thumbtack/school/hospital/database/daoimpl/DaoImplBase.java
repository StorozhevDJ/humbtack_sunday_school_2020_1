package net.thumbtack.school.hospital.database.daoimpl;

import org.apache.ibatis.session.SqlSession;

import net.thumbtack.school.hospital.database.mappers.AdminMapper;
import net.thumbtack.school.hospital.database.mappers.DoctorMapper;
import net.thumbtack.school.hospital.database.mappers.PatientMapper;
import net.thumbtack.school.hospital.database.mappers.UserMapper;
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

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }
}