package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Board;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/write")
public class BoardWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//비로그인 시 처리 중단
//		if( req.getSession().getAttribute("login") == null ) {
//			resp.sendRedirect("/");
//			
//			return;
//		}
		
		req.getRequestDispatcher("/WEB-INF/views/board/write.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//** 파일 첨부없이 게시글 작성 처리
		
//		Board board = new Board();
//		
//		//전달 파라미터 정보 이용
//		board.setTitle( req.getParameter("title") );
//		board.setContent( req.getParameter("content") );
//		
//		//세션 컨텍스트 정보 이용
//		board.setUserid( (String) req.getSession().getAttribute("userid") );
//		
//		System.out.println("BoardWriteController 작성글 : " + board);
//		
//		boardService.write(board);
	
		//---------------------------------------------------------------------
		
		//** 파일 첨부 추가해서 게시글 작성 처리
		
		boardService.write(req);
		
		resp.sendRedirect("./list");
		
	}
	
}


















