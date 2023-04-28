package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import util.Paging;
import web.dao.face.BoardDao;
import web.dto.Board;
import web.dto.BoardFile;

public class BoardDaoImpl implements BoardDao {

	private PreparedStatement ps;	//SQL수행 객체
	private ResultSet rs;	//조회 결과 객체
	
	@Override
	public List<Board> selectAll(Connection conn) {
		System.out.println("BoardDao - selectAll() : 호출");
		
		//SQL 작성
		String sql = "";
		sql += "SELECT";
		sql += "	boardno, title, userid, content";
		sql += "	, hit, write_date";
		sql += " FROM board";
		sql += " ORDER BY boardno DESC";
		
		//결과 처리 변수
		List<Board> list = new ArrayList<>();
		
		//SQL 수행
		try {
			ps = conn.prepareStatement(sql); //SQL 수행 객체
			rs = ps.executeQuery(); //SQL 수행 및 결과 저장
			
			//조회 결과 처리
			while( rs.next() ) {
				Board b = new Board(); //각 행 결과값 저장 객체
				
				//결과값을 한 행씩 저장하기
				b.setBoardno( rs.getInt("boardno") );
				b.setTitle( rs.getString("title") );
				b.setUserid( rs.getString("userid") );
				b.setContent( rs.getString("content") );
				b.setHit( rs.getInt("hit") );
				b.setWriteDate( rs.getTimestamp("write_date") );
				
				//리스트 객체에 조회된 행 객체 저장
				list.add(b);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//자원 반환
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		//최종 결과 리턴
		return list;
	}
	
	@Override
	public int selectCntAll(Connection conn) {
		
		String sql = "";
		sql += "SELECT count(*) FROM board";
		
		//총 게시글 수
		int count = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while( rs.next() ) {
//				count = rs.getInt("count(*)");
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//자원 해제
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return count;
	}

	@Override
	public List<Board> selectAll(Connection conn, Paging paging) {
		
		String sql = "";
		sql += "SELECT * FROM (";
		sql += "	SELECT rownum rnum, B.* FROM (";
		sql += "		SELECT";
		sql += "			boardno, title, userid, hit, write_date";
		sql += "		FROM board";
		sql += "		ORDER BY boardno DESC";
		sql += "	) B";
		sql += " ) BOARD";
		sql += " WHERE rnum BETWEEN ? AND ?";
		
		//결과 처리 변수
		List<Board> list = new ArrayList<>();
		
		//SQL 수행
		try {
			ps = conn.prepareStatement(sql); //SQL 수행 객체
			
			ps.setInt(1, paging.getStartNo());
			ps.setInt(2, paging.getEndNo());
			
			rs = ps.executeQuery(); //SQL 수행 및 결과 저장
			
			//조회 결과 처리
			while( rs.next() ) {
				Board b = new Board(); //각 행 결과값 저장 객체
				
				//결과값을 한 행씩 저장하기
				b.setBoardno( rs.getInt("boardno") );
				b.setTitle( rs.getString("title") );
				b.setUserid( rs.getString("userid") );
//				b.setContent( rs.getString("content") );
				b.setHit( rs.getInt("hit") );
				b.setWriteDate( rs.getTimestamp("write_date") );
				
				//리스트 객체에 조회된 행 객체 저장
				list.add(b);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//자원 반환
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		//최종 결과 리턴
		return list;
	}
	
	@Override
	public int updateHit(Connection conn, Board boardno) {
		System.out.println("BoardDao updateHit() 호출");
		
		String sql = "";
		sql += "UPDATE board";
		sql += " SET hit = hit + 1";
		sql += " WHERE boardno = ?";
		
		//UPDATE 수행 결과
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, boardno.getBoardno());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	
	@Override
	public Board selectBoardByBoardno(Connection conn, Board boardno) {
		System.out.println("BoardDao selectBoardByBoardno() 호출");
		
		String sql = "";
		sql += "SELECT";
		sql += "	boardno";
		sql += "	, title";
		sql += "	, userid";
		sql += "	, content";
		sql += "	, hit";
		sql += "	, write_date";
		sql += " FROM board";
		sql += " WHERE boardno = ?";
		
		//조회 결과를 저장할 DTO객체
		Board board = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, boardno.getBoardno());
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				board = new Board();
			
				board.setBoardno( rs.getInt("boardno") );
				board.setTitle( rs.getString("title") );
				board.setUserid( rs.getString("userid") );
				board.setContent( rs.getString("content") );
				board.setHit( rs.getInt("hit") );
				board.setWriteDate( rs.getTimestamp("write_date") );
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return board;
	}
	
	@Override
	public int insert(Connection conn, Board board) {
		
		String sql = "";
		sql += "INSERT INTO board ( boardno, title, userid, content, hit )";
		sql += " VALUES ( ?, ?, ?, ?, 0 )";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());
			ps.setString(2, board.getTitle());
			ps.setString(3, board.getUserid());
			ps.setString(4, board.getContent());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	
	@Override
	public int selectBoardno(Connection conn) {
		
		String sql = "";
		sql += "SELECT BOARD_SEQ.nextval FROM dual";
		
		int nextval = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				nextval = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return nextval;
	}
	
	@Override
	public int insertFile(Connection conn, BoardFile boardFile) {
		
		String sql = "";
		sql += "INSERT INTO boardFile ( fileno, boardno, originname, storedname, filesize )";
		sql += " VALUES ( BOARDFILE_SEQ.nextval, ?, ?, ?, ? )";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, boardFile.getBoardno());
			ps.setString(2, boardFile.getOriginname());
			ps.setString(3, boardFile.getStoredname());
			ps.setInt(4, boardFile.getFilesize());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	
	@Override
	public BoardFile selectFile(Connection conn, Board viewBoard) {
		
		String sql = "";
		sql += "SELECT";
		sql += "	fileno, boardno, originname, storedname";
		sql += "	, filesize, write_date";
		sql += " FROM boardfile";
		sql += " WHERE boardno = ?";
		sql += " ORDER BY fileno";
		
		BoardFile boardFile = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, viewBoard.getBoardno());
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				boardFile = new BoardFile();
				
				boardFile.setFileno( rs.getInt("fileno") );
				boardFile.setBoardno( rs.getInt("boardno") );
				boardFile.setOriginname( rs.getString("originname") );
				boardFile.setStoredname( rs.getString("storedname") );
				boardFile.setFilesize( rs.getInt("filesize") );
				boardFile.setWriteDate( rs.getTimestamp("write_date") );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return boardFile;
	}

	@Override
	public int update(Connection conn, Board board) {
		
		String sql = "";
		sql += "UPDATE board";
		sql += " SET";
		sql += "	title = ?";
		sql += "	, content = ?";
		sql += " WHERE boardno = ?";
		
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setInt(3, board.getBoardno());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}

	@Override
	public void deleteFile(Connection conn, BoardFile boardFile) {
		
		String sql = "";
		sql += "DELETE boardfile WHERE boardno = ?";
		
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, boardFile.getBoardno());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		
		
	}

	@Override
	public List<BoardFile> selectFileAllByBoardno(Connection conn, Board board) {
		
		String sql = "";
		sql += "SELECT storedname FROM boardfile";
		sql += " WHERE boardno = ?";
		
		List<BoardFile> list = new ArrayList<>();
		
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new BoardFile(0, 0, "", rs.getString("storedname"), 0, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return list;
	}

	@Override
	public void deleteFile(Connection conn, Board board) {
		
		String sql = "";
		sql += "DELETE boardfile WHERE boardno = ?";
		
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}		
	}

	@Override
	public int delete(Connection conn, Board board) {
		
		String sql = "";
		sql += "DELETE board WHERE boardno = ?";
		
		int res = 0;
		
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, board.getBoardno());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
		
		return res;
	}
	
}

















