package web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Paging;
import web.dto.Board;
import web.service.face.BoardService;
import web.service.impl.BoardServiceImpl;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService = new BoardServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//전달파라미터를 이용해서 현재 페이징 객체 알아내기
		Paging paging = boardService.getPaging(req);
		System.out.println("BoardListController - doGet() : " + paging);

		//모델값으로 페이징 객체 전달하기
		req.setAttribute("paging", paging);
	
		
		
		//게시글 전체 조회 - BoardService 이용
//		List<Board> list = boardService.getList();
		
		//게시글 페이징 조회 - BoardService 이용
		List<Board> list = boardService.getList(paging);
		
		//[TEST] 조회 결과 확인
		if( list == null )	System.out.println("list 반환값 null");
		else	for( Board b : list )	System.out.println(b);
		
		//조회결과를 MODEL값으로 전달
		req.setAttribute("list", list);
		
		
		
		//VIEW 지정 및 응답 - forward
		req.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(req, resp);
		
	}
	
}













