package idusw.javaweb.openapia.controller;

import idusw.javaweb.openapia.model.ProjectDTO;
import idusw.javaweb.openapia.util.ConnectionManager;
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

@WebServlet(name="projectController", urlPatterns = { "/project/update",
        "/project/list" ,"/project/add",
        "/project/add-form", "/project/login" ,
        "/project/delete", "/project/detail",
        "/project/register-form", "/project/register"})
public class ProjectController extends HttpServlet {
    ConnectionManager connectionManager = ConnectionManager.getInstance();
    ProjectDTO projectDTO = null;
    List<ProjectDTO> projectDTOList = null;
    int cnt = 0;

    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int index = request.getRequestURI().lastIndexOf("/"); // URI에서 마지막 / 위치의 인덱스 값을 반환
        String command = request.getRequestURI().substring(index + 1);
        // HttpSession 유형의 객체가 존재하는 경우 가져오고, 존재하지 않는 경수 생성함, session : 일정 기간 동안 상태 유지
        HttpSession session = request.getSession();

        String sql = null;
        String tableName = null;  //테이블 t_prja201812015

        if(command.equals("add")) { // == vs. equals()
            projectDTO = new ProjectDTO();
            projectDTO.setProjectName(request.getParameter("project-name"));
            projectDTO.setStatus(request.getParameter("status"));
            projectDTO.setProjectDescription(request.getParameter("project-description"));
            projectDTO.setProjectLeader(request.getParameter("project-leader"));

            sql = "insert into " + tableName + "(project_name, project_description ,status, project_leader) " + "values (?, ?, ?, ?)";

            try (Connection conn = connectionManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);){
                pstmt.setString(1, projectDTO.getProjectName());
                pstmt.setString(2, projectDTO.getProjectDescription());
                pstmt.setString(3, projectDTO.getStatus());
                pstmt.setString(4,projectDTO.getProjectLeader());
                cnt = pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if(cnt >= 1) { // cnt가 0이면 질의 실패
                    request.getRequestDispatcher("/project/list").forward(request, response);;
                }
                else{
                    request.setAttribute("message", "프로젝트 등록 실패");
                    request.getRequestDispatcher("../errors/fail.jsp").forward(request, response);
                }
            }

        }
        else if(command.equals("add-form")) {
            request.getRequestDispatcher("../project/add.jsp").forward(request, response);
        }
        else if(command.equals("list")) {
            sql = "select * from " + tableName;
            try (Connection conn = connectionManager.getConnection();
                 Statement stmt = conn.createStatement(); // 문장(질의처리) 객체 변환
                 ResultSet rs = stmt.executeQuery(sql);){
                // select 구문의 경우 row (record) 또는 rows를 resultSet 변환
                projectDTOList = new ArrayList<>(); //projectDto init
                while(rs.next()) {
                    ProjectDTO project = setRsToDTO(rs);
                    projectDTOList.add(project);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                if(projectDTOList != null){
                    request.setAttribute("dtoList", projectDTOList); // view에게 결과 전달을 위한 속성
                    request.getRequestDispatcher("../project/list.jsp").forward(request, response);
                } else{
                    request.setAttribute("message", "프로젝트 등록 실패");
                    request.getRequestDispatcher("../errors/fail.jsp").forward(request,response);
                }
            }
        }
        else if(command.equals("detail")) {
            projectDTO = new ProjectDTO();
            projectDTO.setPid(Long.valueOf(request.getParameter("pid")));

            sql = "select  * from " + tableName + "where pid=" + projectDTO.getPid();

            try (Connection conn = connectionManager.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql);){
                if(rs.next()) {
                    projectDTO = setRsToDTO(rs);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if(projectDTO != null){
                    request.setAttribute("pDto", projectDTO);
                    request.getRequestDispatcher("../project/detail.jsp").forward(request, response);
                }
                else{
                    request.setAttribute("message", "프로젝트 상세보기 실패");
                    request.getRequestDispatcher("../errors/fail.jsp").forward(request, response);
                }
            }

            // Database로 부터 정보를 가져와서 속성으로 저장 후 전달

            request.setAttribute("pDto", projectDTO);

            request.getRequestDispatcher("../project/detail.jsp").forward(request, response);
        }
        else if(command.equals("update-form")){
            projectDTO = new ProjectDTO();
            projectDTO.setPid(Long.valueOf(request.getParameter("pid")));
            sql = "select *from "+tableName+"where pid=" + projectDTO.getPid();

            try (Connection conn = connectionManager.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql);){
                if(rs.next()) {
                    projectDTO = setRsToDTO(rs);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            finally {
                if(projectDTO!= null) {
                    request.setAttribute("pDto", projectDTO);
                    request.getRequestDispatcher("../project/edit.jsp");
                }
                else{
                    request.setAttribute("message", "프로젝트 업데이트 폼 로드 실패");
                    request.getRequestDispatcher("../errors/fail.jsp").forward(request, response);
                }
            }
        }
        else if(command.equals("update")) {
            projectDTO = new ProjectDTO();
            projectDTO.setPid(Long.valueOf(request.getParameter("pid")));
            projectDTO.setProjectName(request.getParameter("project-name"));
            projectDTO.setStatus(request.getParameter("status"));
            projectDTO.setProjectDescription(request.getParameter("project-description"));
            projectDTO.setProjectLeader(request.getParameter("project-leader"));
            sql = "update "+ tableName + "set project_name = ?, project_description = ?, status = ?, project_leader = ? where pid = ?";
            try (Connection conn = connectionManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);){

                pstmt.setString(1,projectDTO.getProjectName());
                pstmt.setString(2,projectDTO.getProjectDescription());
                pstmt.setString(3,projectDTO.getStatus());
                pstmt.setString(4, projectDTO.getProjectLeader());
                pstmt.setLong(5, Long.valueOf(request.getParameter("pid")));
                cnt = pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if(cnt>=1){
                    response.sendRedirect("../project/list");
                }
                else{
                    request.setAttribute("message", "프로젝트 업데이트 실패");
                    request.getRequestDispatcher("../errors/fail.jsp").forward(request, response);
                }
            }
        }
        else if (command.equals("delete")) {
            projectDTO = new ProjectDTO();
            projectDTO.setPid(Long.valueOf(request.getParameter("pid")));

            sql = "delete from " + tableName + " where pid = ?";
            try (Connection conn = connectionManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);){
                pstmt.setLong(1, projectDTO.getPid());
                cnt = pstmt.executeUpdate();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }finally {
                if(cnt>=1){
                    response.sendRedirect("../projects/list");
                }
                else{
                    request.setAttribute("message", "프로젝트 삭제 실패");
                    request.getRequestDispatcher("../errors.fail.jsp").forward(request, response);
                }
            }
        }

    }
    private ProjectDTO setRsToDTO(ResultSet rs) throws SQLException{
        projectDTO = new ProjectDTO();
        projectDTO.setPid(rs.getLong("pid"));
        projectDTO.setProjectName(rs.getString("project_name"));
        projectDTO.setProjectDescription(rs.getString("project_description"));
        projectDTO.setStatus(rs.getString("status"));
        projectDTO.setProjectLeader(rs.getString("project_leader"));
        return projectDTO;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
}