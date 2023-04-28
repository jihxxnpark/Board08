package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.dto.Board;
import web.dto.BoardFile;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/view")
public class BoardViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//BoardService를 이용한 요청 파라미터 처리
		Board boardno = boardService.getBoardno(req);
		System.out.println("BoardViewController doGet() - " + boardno);
		

		//게시글 조회
		Board viewBoard = boardService.view(boardno);
		System.out.println("BoardViewController doGet() - 조회결과 : " + viewBoard);

		//모델값 전달
		req.setAttribute("viewBoard", viewBoard);
		
		
		//게시글 작성자의 닉네임 전달
		req.setAttribute("writerNick", boardService.getNick(viewBoard));
		
		
		//첨부파일 정보 조회
		BoardFile boardFile = boardService.viewFile(viewBoard);
		
		//첨부파일 정보 모델값 전달
		req.setAttribute("boardFile", boardFile);
		
		
		req.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(req, resp);
		
	}
	
}
















