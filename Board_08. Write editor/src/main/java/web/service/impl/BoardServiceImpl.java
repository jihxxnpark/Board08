package web.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import common.JDBCTemplate;
import util.Paging;
import web.dao.face.BoardDao;
import web.dao.face.MemberDao;
import web.dao.impl.BoardDaoImpl;
import web.dao.impl.MemberDaoImpl;
import web.dto.Board;
import web.dto.BoardFile;
import web.service.face.BoardService;

public class BoardServiceImpl implements BoardService {

	private BoardDao boardDao = new BoardDaoImpl();
	private MemberDao memberDao = new MemberDaoImpl();
	
	@Override
	public List<Board> getList() {
		System.out.println("BoardService - getList() : 호출");
		
		return boardDao.selectAll( JDBCTemplate.getConnection() );
	}
	
	@Override
	public Paging getPaging(HttpServletRequest req) {
		
		//전달파라미터 curPage 추출
		String param = req.getParameter("curPage");
		int curPage = 0;
		if( param != null && !"".equals(param) ) {
			curPage = Integer.parseInt(param);
		} else {
			System.out.println("[WARN] BoardService - getPaging() : curPage값이 null이거나 비어있음");
		}
		
		//총 게시글 수 조회하기
		int totalCount = boardDao.selectCntAll( JDBCTemplate.getConnection() );
		
		//페이징 객체
//		Paging paging = new Paging(totalCount, curPage, 30, 5); //listCount:30, pageCount:5
		Paging paging = new Paging(totalCount, curPage);
		
		return paging;
	}
	
	@Override
	public List<Board> getList(Paging paging) {
		return boardDao.selectAll(JDBCTemplate.getConnection(), paging);
	}

	@Override
	public Board getBoardno(HttpServletRequest req) {
		
		Board board = new Board();
		board.setBoardno( Integer.parseInt( req.getParameter("boardno") ) );
		
		return board;
	}

	@Override
	public Board view(Board boardno) {
		System.out.println("BoardService view() 호출");
		
		//DB 연결 객체
		Connection conn = JDBCTemplate.getConnection();
		
		//hit+1 작업 추가
		int res = boardDao.updateHit(conn, boardno);
		if( res > 0 ) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		
		//글번호로 게시글 조회
		Board board = boardDao.selectBoardByBoardno(conn, boardno);
		
		//조회된 게시글 정보 반환
		return board;
		
	}
	
	@Override
	public void write(Board board) {

		Connection conn = JDBCTemplate.getConnection();

		if( boardDao.insert(conn, board) > 0 ) {
			JDBCTemplate.commit(conn);
			
		} else {
			JDBCTemplate.rollback(conn);
			
		}
		
	}
	
	@Override
	public String getNick(Board viewBoard) {
		return memberDao.selectNickByUserid(JDBCTemplate.getConnection(), viewBoard);
	}
	
	@Override
	public void write(HttpServletRequest req) {
		
		//1. 파일업로드 형식의 인코딩이 맞는지 검사한다
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if( !isMultipart ) {
			//multipart 데이터가 아님
			System.out.println("BoardService isMultipart : " + isMultipart);
			
			//fileupload()메소드 중단시키기
			return;
		}
		
		//2. 업로드된 데이터를 처리하는 방법을 설정한다
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//3. 업로드된 FileItem의 용량(크기)이 설정값보다 작으면 메모리에서 처리한다
		int maxMem = 1 * 1024 * 1024;	//1MB == 1048576B
		factory.setSizeThreshold(maxMem);

		//4. 메모리 처리 사이즈보다 크면 임시파일을 만들어서 HDD로 처리한다
		ServletContext context = req.getServletContext();
		String path = context.getRealPath("tmp");
		
		//임시 폴더 설정
		File tmpRespository = new File(path);
		tmpRespository.mkdir();
		factory.setRepository(tmpRespository);
		
		//5. 파일 업로드를 수행하는 객체 설정하기
		ServletFileUpload upload = new ServletFileUpload(factory);

		//최대 업로드 허용 사이즈 설정
		int maxFile = 10 * 1024 * 1024; //10MB
		upload.setFileSizeMax(maxFile);
		
		//6. 파일 업로드 처리
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		//7. 파싱된 전달 파라미터 데이터 처리하기
		
		//게시글 정보
		Board board = new Board();
		
		//첨부 파일 정보
		BoardFile boardFile = null;
		
		
		//파일아이템 리스트의 반복자
		Iterator<FileItem> iter = items.iterator();
		
		while( iter.hasNext() ) {
			
			//전달 파라미터를 저장한 FileItem객체를 하나씩 꺼내서 적용하기
			FileItem item = iter.next();
		
			
			//--- 1. 빈 파일에 대한 처리 ---
			if( item.getSize() <= 0 ) { //전달 데이터의 크기가 0이하
				
				//빈 파일을 무시하고 다음 FileItem 처리로 넘어간다 
				continue;
			}
			
			
			//--- 2. 폼 필드에 대한 처리 ---
			if( item.isFormField() ) {
				
				//키(key) 추출하기
				String key = item.getFieldName();
				
				//값(value) 추출하기
				String value = null;
				try {
					value = item.getString("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				//전달파라미터의 name(key)에 맞게 DTO의 setter를 호출한다
				if( "title".equals(key) ) {
					board.setTitle(value);
					
				} else if( "content".equals(key) ) {
					board.setContent(value);
					
				}
				
				//세션 정보를 이용하여 userid 추가하기
				board.setUserid( (String) req.getSession().getAttribute("userid") );
				
			} //if( item.isFormField() ) end
			
			
			//--- 3. 파일에 대한 처리 ---
			if( !item.isFormField() ) {
				
				boardFile = new BoardFile();
				
				String rename = UUID.randomUUID().toString().split("-")[4];

				System.out.println("BoardService write() - 원본파일명 : " + item.getName());
				System.out.println("BoardService write() - 저장파일명 : " + rename);
				
				//임시 보관하고 있는 파일을 실제 업로드 저장소로 옮기기
				
				//실제 파일 저장소
				File uploadFolder = new File( context.getRealPath("upload") );
				uploadFolder.mkdir();
				
				//실제 저장할 파일 객체 (옮길 파일)
				File up = new File(uploadFolder, rename); //파일이름 변경되어 저장됨
				
				try {
					//임시파일을 실제 업로드 파일로 출력한다
					item.write(up);
					
					//임시파일 제거
					item.delete();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//업로드된 파일의 정보를 DTO객체에 저장하기
				boardFile.setOriginname( item.getName() );
				boardFile.setStoredname( rename );
				boardFile.setFilesize( (int) item.getSize() );
				
			} //if( !item.isFormField() ) end
			
		} //while( iter.hasNext() ) end
		
		System.out.println("BoardService write() - board : " + board);
		System.out.println("BoardService write() - boardFile : " + boardFile);
		
		
		//8. DB에 최종 데이터 삽입하기
		Connection conn = JDBCTemplate.getConnection(); 
		
		
		//boardno로 사용할 시퀀스의 nextval을 조회한다
		int nextBoardNo = boardDao.selectBoardno(conn);
		
		int res = 0;
		
		//게시글 삽입
		board.setBoardno(nextBoardNo);
		res += boardDao.insert(conn, board);
		
		//첨부파일 삽입
		if( boardFile != null ) {
			boardFile.setBoardno(nextBoardNo);
			res += boardDao.insertFile(conn, boardFile);
		}

		
		//트랜잭션 관리
		if( boardFile != null && res < 2 ) {
			JDBCTemplate.rollback(conn);

		} else if ( boardFile == null && res < 1 ) {
			JDBCTemplate.rollback(conn);
		
		} else {
			JDBCTemplate.commit(conn);
			
		}

	}
	
	@Override
	public BoardFile viewFile(Board viewBoard) {
		return boardDao.selectFile(JDBCTemplate.getConnection(), viewBoard);
	}

	@Override
	public boolean checkUerid(HttpServletRequest req) {
		
		//로그인한 사용자의 userid
		String loginid = (String) req.getSession().getAttribute("userid");
		
		//게시글 번호 얻어오기
		Board boardno = getBoardno(req);
		
		//게시글 정보 얻어오기
		Board board = boardDao.selectBoardByBoardno(JDBCTemplate.getConnection(), boardno);
		
		
		//작성자와 로그인정보 비교
		if(loginid.equals(board.getUserid())) {
			return true;
		}
		return false;
	}

	@Override
	public void update(HttpServletRequest req) {
		
		//1. 파일업로드 형식의 인코딩이 맞는지 검사한다
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if( !isMultipart ) {
			//multipart 데이터가 아님
			System.out.println("BoardService isMultipart : " + isMultipart);
			
			//fileupload()메소드 중단시키기
			return;
		}
		
		//2. 업로드된 데이터를 처리하는 방법을 설정한다
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//3. 업로드된 FileItem의 용량(크기)이 설정값보다 작으면 메모리에서 처리한다
		int maxMem = 1 * 1024 * 1024;	//1MB == 1048576B
		factory.setSizeThreshold(maxMem);

		//4. 메모리 처리 사이즈보다 크면 임시파일을 만들어서 HDD로 처리한다
		ServletContext context = req.getServletContext();
		String path = context.getRealPath("tmp");
		
		//임시 폴더 설정
		File tmpRespository = new File(path);
		tmpRespository.mkdir();
		factory.setRepository(tmpRespository);
		
		//5. 파일 업로드를 수행하는 객체 설정하기
		ServletFileUpload upload = new ServletFileUpload(factory);

		//최대 업로드 허용 사이즈 설정
		int maxFile = 10 * 1024 * 1024; //10MB
		upload.setFileSizeMax(maxFile);
		
		//6. 파일 업로드 처리
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		//7. 파싱된 전달 파라미터 데이터 처리하기
		
		boolean isDel = false;
		
		//게시글 정보
		Board board = new Board();
		
		//첨부 파일 정보
		BoardFile boardFile = null;
		
		
		//파일아이템 리스트의 반복자
		Iterator<FileItem> iter = items.iterator();
		
		while( iter.hasNext() ) {
			
			//전달 파라미터를 저장한 FileItem객체를 하나씩 꺼내서 적용하기
			FileItem item = iter.next();
		
			
			//--- 1. 빈 파일에 대한 처리 ---
			if( item.getSize() <= 0 ) { //전달 데이터의 크기가 0이하
				
				//빈 파일을 무시하고 다음 FileItem 처리로 넘어간다 
				continue;
			}
			
			
			//--- 2. 폼 필드에 대한 처리 ---
			if( item.isFormField() ) {
				
				//키(key) 추출하기
				String key = item.getFieldName();
				
				//값(value) 추출하기
				String value = null;
				try {
					value = item.getString("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				//전달파라미터의 name(key)에 맞게 DTO의 setter를 호출한다
				
				if("boardno".equals(key)) {
					board.setBoardno(Integer.parseInt(value));
					
				}else if("title".equals(key)) {
					board.setTitle(value);
					
		        }else if("content".equals(key)) {
		        	board.setContent(value);
		        }
				if( "del".equals(key) ) {
					if("y".equals(value)){
						isDel = true;
					}
				}
				
			} //if( item.isFormField() ) end
			
			
			//--- 3. 파일에 대한 처리 ---
			if( !item.isFormField() ) {
				
				boardFile = new BoardFile();
				
				String rename = UUID.randomUUID().toString().split("-")[4];

				System.out.println("BoardService write() - 원본파일명 : " + item.getName());
				System.out.println("BoardService write() - 저장파일명 : " + rename);
				
				//임시 보관하고 있는 파일을 실제 업로드 저장소로 옮기기
				
				//실제 파일 저장소
				File uploadFolder = new File( context.getRealPath("upload") );
				uploadFolder.mkdir();
				
				//실제 저장할 파일 객체 (옮길 파일)
				File up = new File(uploadFolder, rename); //파일이름 변경되어 저장됨
				
				try {
					//임시파일을 실제 업로드 파일로 출력한다
					item.write(up);
					
					//임시파일 제거
					item.delete();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//업로드된 파일의 정보를 DTO객체에 저장하기
				boardFile.setOriginname( item.getName() );
				boardFile.setStoredname( rename );
				boardFile.setFilesize( (int) item.getSize() );
				
			} //if( !item.isFormField() ) end
			
		} //while( iter.hasNext() ) end
		
		System.out.println("BoardService write() - board : " + board);
		System.out.println("BoardService write() - boardFile : " + boardFile);
		
		
		//8. DB에 최종 데이터 삽입하기
		Connection conn = JDBCTemplate.getConnection(); 
		
		int res = 0;
		
		//게시글 수정
		res += boardDao.update(conn, board);
		
		//첨부파일 삭제
		if( isDel) {
			List<BoardFile> fileList = boardDao.selectFileAllByBoardno(conn, board);
			for(BoardFile bf : fileList) {
				File file = new File(context.getRealPath("upload"), bf.getStoredname());
				file.delete();	
			}
			boardDao.deleteFile(conn, board);
		}
		
		//첨부파일 삽입
		if( boardFile != null ) {
			boardFile.setBoardno(board.getBoardno());

			List<BoardFile> fileList = boardDao.selectFileAllByBoardno(conn, board);
			
			for(BoardFile bf : fileList) {
				File file = new File(context.getRealPath("upload"), bf.getStoredname());
				file.delete();	
				
			}
			
			
			
			boardDao.deleteFile(conn, boardFile);
			
			res += boardDao.insertFile(conn, boardFile);
		}

		
		//트랜잭션 관리
		if( boardFile != null && res < 2 ) {
			JDBCTemplate.rollback(conn);

		} else if ( boardFile == null && res < 1 ) {
			JDBCTemplate.rollback(conn);
		
		} else {
			JDBCTemplate.commit(conn);
			
		}
		
	}

	@Override
	public void delete(Board board) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		boardDao.deleteFile(conn, board);
		boardDao.delete(conn, board);
		
		JDBCTemplate.commit(conn);
		
		
	}
	
}


















