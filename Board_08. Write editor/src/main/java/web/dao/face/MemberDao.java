package web.dao.face;

import java.sql.Connection;

import web.dto.Board;
import web.dto.Member;

public interface MemberDao {

	/**
	 * userid와 userpw가 일치하는 회원의 수를 조회한다
	 * 	로그인 인증에 사용한다
	 * 
	 * @param conn - DB 연결 객체
	 * @param member - 조회할 회원의 정보
	 * @return 0: 존재하지 않는 회원, 1: 존재하는 회원
	 */
	public int selectCntMemberByUseridUserpw(Connection conn, Member member);

	/**
	 * userid를 이용해서 회원정보 조회하기
	 * 
	 * @param conn - DB 연결 객체
	 * @param member - 조회할 userid를 가진 객체
	 * @return 조회된 회원 정보
	 */
	public Member selectMemberByUserid(Connection conn, Member member);

	/**
	 * 신규 회원정보 삽입
	 * 
	 * @param conn - DB연결 객체
	 * @param joinMember - 회원가입 정보 객체
	 * @return INSERT 수행 결과 (1: 성공, 0: 실패)
	 */
	public int insert(Connection conn, Member joinMember);

	/**
	 * userid를 이용하여 usernick을 조회한다
	 * 
	 * @param conn - DB 연결 객체
	 * @param viewBoard - 조회할 userid를 가진 객체
	 * @return 조회된 닉네임
	 */
	public String selectNickByUserid(Connection conn, Board viewBoard);

}














