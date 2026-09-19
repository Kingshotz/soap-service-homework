package config;

import data.Constants;
import db.mapper.EmployeeMapper;
import db.mapper.UsersMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class DataBaseConfig {
    public static EmployeeMapper dbMapper() {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(Constants.DRIVER);
        dataSource.setUrl(Constants.CONNECTION_STRING);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("development", transactionFactory, dataSource);

        Configuration configuration = new Configuration(environment);

        configuration.addMapper(EmployeeMapper.class);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        return sqlSession.getMapper(EmployeeMapper.class);
    }
    public static UsersMapper usersMapper() {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(Constants.DRIVER);
        dataSource.setUrl(Constants.CONNECTION_STRING);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("development", transactionFactory, dataSource);

        Configuration configuration = new Configuration(environment);

        configuration.addMapper(UsersMapper.class);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        return sqlSession.getMapper(UsersMapper.class);
    }

}