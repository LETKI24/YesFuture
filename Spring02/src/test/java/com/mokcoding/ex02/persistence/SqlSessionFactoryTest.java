package com.mokcoding.ex02.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mokcoding.ex02.config.RootConfig;

import lombok.extern.log4j.Log4j;
import oracle.jdbc.OracleDriver;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit test 연결
@ContextConfiguration(classes= {RootConfig.class}) // 설정 파일 연결 - Test하고 싶은 '걔'를 데려온다
@Log4j
public class SqlSessionFactoryTest {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	// try() {} catch() {} : 자동 리소스 해제 try-catch
	@Test
	public void testSessionFactory() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();
				Connection conn = sqlSession.getConnection()){ // 리소스를 finally로 안닫아도 알아서 닫아주는 애들
			log.info("SqlSession 객체 : " + sqlSession);
			log.info("Connection 객체 : " + conn);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
}
