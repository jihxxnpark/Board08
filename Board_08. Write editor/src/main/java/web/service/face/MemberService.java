package web.service.face;

import javax.servlet.http.HttpServletRequest;

import web.dto.Member;

public interface MemberService {

	/**
	 * 로그인 정보(파라미터) 추출하기
	 * 
	 * @param req - 요청 정보 객체
	 * @return 로그인 정보
	 */
	public Member getLoginMember(HttpServletRequest req);

	/**
	 * 로그인 인증 처리
	 * 
	 * @param member - 로그인 정보
	 * @return 인증 결과 ( true: 인증 성공, false: 인증 실패 )
	 */
	public boolean login(Member member);

	/**
	 * 사용자 정보 가져오기
	 * 
	 * @param member - 조회할 회원 아이디를 가진 객체
	 * @return 조회된 회원 정보
	 */
	public Member info(Member member);

	/**
	 * 회원가입 정보(파라미터) 추출하기
	 * 
	 * @param req - 요청 정보 객체
	 * @return 회원가입 정보
	 */
	public Member getJoinMember(HttpServletRequest req);

	/**
	 * 회원가입 처리
	 * 
	 * @param joinMember - 회원가입 정보
	 */
	public void join(Member joinMember);
	
}















