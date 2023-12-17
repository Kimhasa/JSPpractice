package idusw.javaweb.openapia.controller;

import idusw.javaweb.openapia.model.MemberDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Controller : 요청의 흐름을 제어하는 역할을 수행
// 데이터 처리 기본 연산 : C.R.U.D (create, read, update, delete)
// HTTP Method (Rest API 관련이 높음) : post, get, (update, delete) - jsp에서는 지원에 문제가 있음
@WebServlet(name="memberController", urlPatterns = {
        "/members/post", "/members/get-one", "/members/get-list",
        "/members/update", "/members/delete", "/members/post-form", "/members/login-form",
        "/members/login", "/members/logout"})
public class MemberController extends HttpServlet {
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    List<MemberDTO> memberDTOList = null;

    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // http://localhost:8088/a202312345/members/post
        int index = request.getRequestURI().lastIndexOf("/"); // URI에서 마지막 / 위치의 인덱스 값을 반환
        String command = request.getRequestURI().substring(index + 1);
        // HttpSession 유형의 객체가 존재하는 경우 가져오고, 존재하지 않는 경수 생성함, session : 일정 기간 동안 상태 유지
        HttpSession session = request.getSession();

        getConnection();
        if(command.equals("post")) { // == vs. equals()
            MemberDTO member = new MemberDTO();
            member.setFullName(request.getParameter("full-name"));
            member.setEmail(request.getParameter("email"));
            String pw1 = request.getParameter("pw1");
            String pw2 = request.getParameter("pw2");
            if (pw1.equals(pw2))
                member.setPw(pw1);
            else
                System.out.println("암호 불일치로 작업 중단");
            try {
                stmt = conn.createStatement(); // 문장(질의 처리) 객체 반환
                // cnt 가 0이면 질의 실패
                int cnt = stmt.executeUpdate("insert into t_mba202312345(fullname, email, pw) " +
                        "values ('" +  member.getFullName() + "', '" +
                        member.getEmail() + "', '" +
                        member.getPw() + "')");
                if(cnt >= 1) {
                    request.setAttribute("dto", member);
                    // 회원 가입 성공 메시지와 이동 버튼을 가진 view 호출
                    request.getRequestDispatcher("/members/success.jsp").forward(request, response);
                }
                else {
                    request.setAttribute("message", "회원 가입 실패");
                    request.getRequestDispatcher("/errors/fail.jsp").forward(request, response);
                    ; // 회원 가입 실패 view 호출
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else if(command.equals("get-list")) {
            memberDTOList = new ArrayList<>(); // memberDTOList 초기화
            // Database로 부터 정보를 가져와서 속성으로 저장 후 전달
            try {
                stmt = conn.createStatement(); // 문장(질의 처리) 객체 반환
                // select 구문의 경우 row (record) 또는 rows를 resultSet 반환
                rs = stmt.executeQuery("select * from t_mba202312345"); // 자신에게 권한이 있는 대상 테이블
                while(rs.next()) {
                    MemberDTO m = new MemberDTO();
                    //m.setMid(rs.getLong(1));
                    m.setFullName(rs.getString("fullname")); // fullname : table의 필드 이름
                    m.setEmail(rs.getString("email"));
                    m.setPw(rs.getString("pw"));
                    memberDTOList.add(m);
                }
                /* memberDTOList : JCF ArrayList를 enhanced for statement로 출력
                for(MemberDTO m : memberDTOList)
                    System.out.println(m.getEmail());

                 */
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("dtoList", memberDTOList);
            request.getRequestDispatcher("../members/list.jsp").forward(request, response);
        }
        else if(command.equals("post-form")) {
            request.getRequestDispatcher("../members/register.jsp").forward(request, response);
        }
        else if(command.equals("login-form")) {
            request.getRequestDispatcher("../members/login.jsp").forward(request, response);
        }
        else if(command.equals("login")) {
            MemberDTO m = null;
            try {
                stmt = conn.createStatement(); // 문장(질의 처리) 객체 반환
                // select 구문의 경우 row (record) 또는 rows를 resultSet 반환
                String query = "select * from t_mba202312345 " +
                        "where email = '" + request.getParameter("email") +
                        "' and pw = '" + request.getParameter("pw1")+  "'";
                System.out.println(query);
                rs = stmt.executeQuery(query); // 자신에게 권한이 있는 대상 테이블
                if(rs.next()) {
                    m = new MemberDTO();
                    m.setMid(rs.getLong("mid")); // mid : primary key
                    m.setFullName(rs.getString("fullname"));
                    m.setEmail(rs.getString("email"));
                    m.setPw(rs.getString("pw"));
                }
                if( m != null) {
                    //request.setAttribute("dto", m); // Scope 객체는 page -> request -> session -> application 순서로 검색
                    session.setAttribute("dto", m);
                    request.getRequestDispatcher("../main/index.jsp").forward(request, response);
                }
                else
                    request.getRequestDispatcher("/errors/fail.jsp").forward(request, response);; // 회원 로그인 실패 view 호출
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else if(command.equals("get-one")) {
            MemberDTO member = null;
            try {
                /*
                stmt = conn.createStatement(); // 문장(질의 처리) 객체 반환
                // select 구문의 경우 row (record) 또는 rows를 resultSet 반환
                String query = "select * from t_mba202312345 " +
                        "where mid = " + Long.valueOf(request.getParameter("mid")); // valueOf() : String -> Long
                System.out.println(query);
                 */
                pstmt = conn.prepareStatement("select * from t_mba202312345 where mid = ?"); // ? : placeholder
                pstmt.setLong(1, Long.valueOf(request.getParameter("mid"))); // Long -> long
                rs = pstmt.executeQuery(); // 자신에게 권한이 있는 대상 테이블
                if(rs.next()) {
                    member = new MemberDTO();
                    member.setMid(Long.valueOf(request.getParameter("mid")));
                    member.setFullName(rs.getString("fullname"));
                    member.setEmail(rs.getString("email"));
                    member.setPw(rs.getString("pw"));
                    member.setZipcode(rs.getString("zipcode"));
                }
                if( member != null) {
                    //request.setAttribute("dto", m); // Scope 객체는 page -> request -> session -> application 순서로 검색
                    session.setAttribute("dto", member);
                    request.getRequestDispatcher("../members/detail.jsp").forward(request, response);
                }
                else
                    request.getRequestDispatcher("/errors/fail.jsp").forward(request, response);;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else if(command.equals("update")) {
            MemberDTO member = new MemberDTO();
            member.setMid(Long.valueOf(request.getParameter("mid"))); // String -> Long 값으로 변환하여 가져오기
            member.setFullName(request.getParameter("full-name"));
            member.setEmail(request.getParameter("email"));
            member.setPw(request.getParameter("pw"));
            member.setZipcode(request.getParameter("zipcode"));
            int cnt = 0;
            try {
                pstmt = conn.prepareStatement("update t_mba202312345 set fullname = ?, pw = ?, zipcode = ? where mid = ?");
                pstmt.setString(1, member.getFullName()); // placeholder에 매칭하여 대체됨
                pstmt.setString(2, member.getPw());
                pstmt.setString(3, member.getZipcode());
                pstmt.setLong(4, member.getMid()); // Auto Boxing / Unboxing : long -> Long, Long -> long
                cnt = pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if(cnt > 0) {
                    request.getRequestDispatcher("../members/get-one?mid=" + member.getMid()).forward(request, response);
                } else {
                    request.getRequestDispatcher("../errors/fail.jsp").forward(request, response);
                }
            }
        }
        else if(command.equals("logout")) {
            session.invalidate();
            response.sendRedirect("../main/index.jsp"); // request 객체 전달이 필요없는 경우
            // request.getRequestDispatcher("../main/index.jsp").forward(request, response);
            // request 객체 전달이 필요한 경우
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
    private void getConnection() { // dbms 연결 및 필요 객체 생성 관련 메소드
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_a202312345?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
        String dbUser = "ua202312345"; // idusw 계정도 가능, m_a202312345 계정은 DB_User 그룹에 속하므로 권한이 매우 많음
        String dbPass = "cometrue";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver를 메모리에 적재
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass); // dbms 연결 후 연결 객체 반환
            /*
            rs = stmt.executeQuery("select * from member");
            System.out.println("Connection Success - " + jdbcUrl);
            while (rs.next()) {
                System.out.print(rs.getString(1) + "\t");
                System.out.println(rs.getString(2));
            }
             */
        } catch(SQLException e) {
            System.out.println("Connection Fail - ");
        } finally {
            /*
            if (rs != null) try { rs.close(); } catch (Exception e) {}
            if (pstmt != null) try {pstmt.close(); } catch (Exception e) {}
            if (stmt != null) try {stmt.close(); } catch (Exception e) {}
            if (conn != null) try {conn.close(); } catch (Exception e) {}
             */
        }
    }
}
