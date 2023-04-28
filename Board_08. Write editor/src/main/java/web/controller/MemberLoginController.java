package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.dto.Member;
import web.service.face.MemberService;
import web.service.impl.MemberServiceImpl;

@WebServlet("/member/login")
public class MemberLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//서비스 객체
	private MemberService memberService = new MemberServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//로그인 전달 파라미터 추출
		Member member = memberService.getLoginMember(req);
		
		
		//로그인 인증 - 아이디/비번 확인
		boolean isLogin = memberService.login(member);
		
		//로그인 인증 성공
		if( isLogin ) {
			
			//사용자 정보 조회
			member = memberService.info(member);
			System.out.println("MemberLoginController result : " + member);
			
			//세션
			HttpSession session = req.getSession();
			
			session.setAttribute("login", isLogin);
			
			session.setAttribute("userid", member.getUserid());
			session.setAttribute("usernick", member.getUsernick());
			
		}
		
		//메인페이지로 리다이렉트
		resp.sendRedirect("/");
		
	}
	
}
















