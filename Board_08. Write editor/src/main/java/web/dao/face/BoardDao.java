package web.dao.face;

import java.sql.Connection;
import java.util.List;

import util.Paging;
import web.dto.Board;
import web.dto.BoardFile;

public interface BoardDao {

	/**
	 * 테이블 전체 조회
	 * 
	 * @param conn - DB 연결 객체
	 * @return 테이블 전체 조회 결과 목록
	 */
	public List<Board> selectAll(Connection conn);

	/**
	 * 총 게시글 수 조회하기
	 * 
	 * @param conn - DB 연결 객체
	 * @return 테이블의 전체 행 수
	 */
	public int selectCntAll(Connection conn);

	/**
	 * 테이블의 페이징 리스트 조회하기
	 * 
	 * @param conn - DB 연결 객체
	 * @param paging - 페이징 정보 객체
	 * @return 조회된 페이징 리스트
	 */
	public List<Board> selectAll(Connection conn, Paging paging);

	/**
	 * 글번호에 해당하는 조회수를 1 증가 시킨다 
	 * 
	 * @param conn - DB 연결 객체
	 * @param boardno - 조회된 게시글 번호 객체
	 * @return UPDATE SQL의 수행 결과 ( 영향받은 행 수 )
	 * 		1 - 조회수 증가 성공
	 * 		0 - 조회수 증가 실패
	 */
	public int updateHit(Connection conn, Board boardno);
	
	/**
	 * 글번호로 게시글 조회한다
	 * 
	 * @param conn - DB 연결 객체
	 * @param boardno - 조회할 게시글 번호 DTO 객체
	 * @return 조회된 게시글 정보 DTO 객체
	 */
	public Board selectBoardByBoardno(Connection conn, Board boardno);

	/**
	 * 게시글 입력
	 * 
	 * @param conn - DB 연결 객체
	 * @param board - 삽입될 게시글 내용
	 * @return INSERT 수행 결과 (1:성공, 0:실패)
	 */
	public int insert(Connection conn, Board board);

	/**
	 * 시퀀스를 이용하여 다음 게시글 번호를 조회한다
	 * 
	 * @param conn - DB 연결 객체
	 * @return 다음 게시글 번호
	 */
	public int selectBoardno(Connection conn);

	/**
	 * 첨부 파일 정보 삽입
	 * 
	 * @param conn - DB 연결 객체
	 * @param boardFile - 업로드된 첨부파일 정보
	 * @return INSERT 쿼리 수행 결과 (1:성공, 0:실패)
	 */
	public int insertFile(Connection conn, BoardFile boardFile);

	/**
	 * 첨부 파일 정보 조회
	 * 
	 * @param conn - DB 연결 객체
	 * @param viewBoard - 조회할 게시글 번호를 가진 객체
	 * @return 첨부파일 정보
	 */
	public BoardFile selectFile(Connection conn, Board viewBoard);

	
	/**
	 * 게시글 수정
	 * 
	 * @param conn - 디비 연결 객체
	 * @param board - 게시글 수정할 내용을 담은 객체
	 * @return update 수행 결과(1:성공, 0:실패)
	 */
	public int update(Connection conn, Board board);

	
	/**
	 * 기존 첨부파일 삭제
	 * @param conn
	 * @param boardFile
	 */
	public void deleteFile(Connection conn, BoardFile boardFile);

	/**
	 * 게시글에 첨부된 모든 파일 정보 조회
	 * 
	 * @param conn 
	 * @param board
	 * @return 첨부파일 목록
	 */
	public List<BoardFile> selectFileAllByBoardno(Connection conn, Board board);
	
	/**
	 * 게스길에 첨부된 파일 삭제
	 * 
	 * @param conn
	 * @param board
	 */
	public void deleteFile(Connection conn, Board board);
	
	/**
	 * 게시글 삭제
	 * 
	 * @param conn 디비 연결 객체
	 * @param board 삭제할 게시글 번호를 담은 객체
	 * @return delete 수행 결과 리턴
	 */
	public int delete(Connection conn, Board board);
	
}





















