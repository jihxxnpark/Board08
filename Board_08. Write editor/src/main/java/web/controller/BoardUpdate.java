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


@WebServlet("/board/update")
public class BoardUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//게시글을 작성한 사용자가 아니면 중단한다
		if(!boardService.checkUerid(req)){
			
			resp.sendRedirect("/board/list");
			return;
		}
		
		//게시글 조회
		Board viewBoard = boardService.view(boardService.getBoardno(req));
		System.out.println("BoardViewController doGet() - 조회결과 : " + viewBoard);
		
		//모델값 전달
		req.setAttribute("viewBoard", viewBoard);
		
		
		//게시글 작성자의 닉네임 전달
		req.setAttribute("writerNick", boardService.getNick(viewBoard));
		
		
		//첨부파일 정보 조회
		BoardFile boardFile = boardService.viewFile(viewBoard);
		
		//첨부파일 정보 모델값 전달
		req.setAttribute("boardFile", boardFile);
		
		
		req.getRequestDispatcher("/WEB-INF/views/board/update.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boardService.update(req);
		
		resp.sendRedirect("./list");
	}
	
	
	
	
	
	
	
	
	
	
	

}
