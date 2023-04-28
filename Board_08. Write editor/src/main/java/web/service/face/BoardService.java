package web.service.face;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import util.Paging;
import web.dto.Board;
import web.dto.BoardFile;

public interface BoardService {

	/**
	 * 게시글 전체 조회
	 * 
	 * @return 게시글 전체 조회한 결과 목록
	 */
	public List<Board> getList();

	/**
	 * 페이징 객체 생성
	 * 
	 * @param req - 요청 정보 객체
	 * @return 페이징 계산이 완료된 Paging객체
	 */
	public Paging getPaging(HttpServletRequest req);

	/**
	 * 게시글 페이징 목록 조회
	 * 
	 * @param paging - 페이징 정보 객체
	 * @return 페이징이 반영된 게시글 조회 결과 목록
	 */
	public List<Board> getList(Paging paging);
	
	/**
	 * 전달파라미터를 Board DTO로 저장하여 반환한다
	 * 
	 * @param req - 요청 정보 객체
	 * @return 전달파라미터 boardno를 저장한 DTO 객체
	 */
	public Board getBoardno(HttpServletRequest req);
	
	/**
	 * 글번호로 게시글 조회
	 * hit+1 작업 추가
	 * 	 
	 * @param boardno - 글번호를 가진 DTO객체
	 * @return 조회된 게시글 정보 DTO객체
	 */
	public Board view(Board boardno);

	/**
	 * 게시글 작성
	 * 입력된 게시글 내용을 DB에 저장한다
	 * 
	 * @param board - 신규 게시글 정보
	 */
	public void write(Board board);

	/**
	 * 전달될 Board객체의 userid를 이용한 닉네임 조회
	 * 
	 * @param viewBoard - 조회할 게시글 정보
	 * @return 게시글 작성자의 닉네임
	 */
	public String getNick(Board viewBoard);

	/**
	 * 게시글 작성
	 * 첨부파일 처리가 추가된다
	 * 
	 * @param req - 요청 정보 객체
	 */
	public void write(HttpServletRequest req);

	/**
	 * 첨부파일 정보 조회
	 * 
	 * @param viewBoard - 첨부파일과 연결된 게시글 번호(boardno)
	 * @return 첨부파일 정보 객체
	 */
	public BoardFile viewFile(Board viewBoard);

	/**
	 * 글 작성자인지 판단하기
	 * 로그인정보(세션)와 작성자정보(전달파라미터)를 비교한다
	 * 
	 * 작성자정보는 전달파라미터 boardno를 이용하여 DB조회하여 알아낸다
	 * 
	 * @param req - 요청 정보 객체
	 * @return 작성자 판단 결과(true 작성자, false 작성자아님)
	 */
	public boolean checkUerid(HttpServletRequest req);

	
	/**
	 * 게시글 수정
	 * 
	 * 
	 * @param req
	 */
	public void update(HttpServletRequest req);
	
	/**
	 * 게시글 삭제
	 * 
	 * 
	 * @param board 삭제할 게시글 번호를 가진 객체
	 */
	public void delete(Board board);
	
}















