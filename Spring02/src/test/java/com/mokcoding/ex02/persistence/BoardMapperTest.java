package com.mokcoding.ex02.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mokcoding.ex02.config.RootConfig;
import com.mokcoding.ex02.domain.BoardVO;
import com.mokcoding.ex02.util.Pagination;

import lombok.extern.log4j.Log4j;
import oracle.jdbc.OracleDriver;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit test 연결
@ContextConfiguration(classes= {RootConfig.class}) // 설정 파일 연결 - Test하고 싶은 '걔'를 데려온다
@Log4j
public class BoardMapperTest {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void test() {
//		testBoardInsert();
//		testBoardList();
//		testBoardSelect();
//		testBoardUpdate();
//		testBoardDelete();
		
		//페이징 처리
		testBoardPagination();
	}

	private void testBoardPagination() {
		Pagination pagination = new Pagination(1, 3); // = 페이지당 갯수 3개, 1페이지 불러오기
		List<BoardVO> list = boardMapper.selectListByPagination(pagination);
		for(BoardVO boardVO : list) {
			log.info(boardVO);
		}
	}

	private void testBoardSelect() {
		BoardVO boardVO = boardMapper.selectOne(3);
		log.info(boardVO); 
	}
	
	private void testBoardUpdate() {
		BoardVO vo = new BoardVO(3, "title Update", "content update", null, null);
		int result = boardMapper.update(vo);
		log.info(result + "행 업뎃");
	}

	private void testBoardDelete() {
		int result = boardMapper.delete(3);
		log.info(result + "행 삭제");
	}


	private void testBoardList() {
		for(BoardVO boardVO : boardMapper.selectList()) {
			log.info(boardVO);
		}
		
	}

	private void testBoardInsert() {
		BoardVO vo = new BoardVO(0, "test title", "test content", "guest", null);
		int result = boardMapper.insert(vo);
		log.info(result + "행 삽입");
		
	} 
	
}
