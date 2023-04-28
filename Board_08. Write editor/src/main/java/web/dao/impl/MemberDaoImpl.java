package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.JDBCTemplate;
import oracle.net.aso.n;
import web.dao.face.MemberDao;
import web.dto.Board;
import web.dto.Member;

public class MemberDaoImpl implements MemberDao {

	private PreparedStatement ps;	//SQL수행 객체
	private ResultSet rs;	//조회 결과 객체

	@Override
	public int selectCntMemberByUseridUserpw(Connection conn, Member member) {
		
		String sql = "";
		sql += "SELECT count(*) FROM member";
		sql += " WHERE 1=1";
		sql += "	AND userid = ?";
		sql += "	AND userpw = ?";
		
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, member.getUserid());
			ps.setString(2, member.getUserpw());
			
			rs = ps.executeQuery();

			while( rs.next() ) {
//				cnt = rs.getInt("count(*)");
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
				
		return cnt;
	}
	
	@Override
	public Member selectMemberByUserid(Connection conn, Member member) {
		
		String sql = "";
		sql += "SELECT * FROM member";
		sql += " WHERE 1=1";
		sql += "	AND userid = ?";
		
		Member result = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, member.getUserid());
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				result = new Member();
				
				result.setUserid( rs.getString("userid") );
				result.setUsernick( rs.getString("usernick") );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
				
		return result;
	}
	
	@Override
	public int insert(Connection conn, Member joinMember) {
		
		String sql = "";
		
		sql += "INSERT INTO member( userid, userpw, usernick )";
		sql += " VALUES ( ?, ?, ? )";
	
		int res = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, joinMember.getUserid());
			ps.setString(2, joinMember.getUserpw());
			ps.setString(3, joinMember.getUsernick());
			
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
				
		return res;
	}
	
	@Override
	public String selectNickByUserid(Connection conn, Board viewBoard) {
		
		String sql = "";
		sql += "SELECT usernick FROM member";
		sql += " WHERE userid = ?";
		
		String nick = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, viewBoard.getUserid());
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				nick = rs.getString("usernick");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
				
		return nick;
	}
}
















