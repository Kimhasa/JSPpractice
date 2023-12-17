<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title> c:sql </title>
</head>
<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<sql:setDataSource driver="com.mysql.cj.jdbc.Driver"
                   url="jdbc:mysql://localhost:3306/db_a202312345"
                   user="ua202312345"
                   password="cometrue" />

<sql:query var="members" startRow="0" >
    select * from t_mba202312345;
    <%--
    <sql:param value="mysql%" />
    --%>
</sql:query>

<c:forEach var="row" items="${members.rows}">
    ${row.mid} :: ${row.fullname} :: ${row.email} :: ${row.pw} :: ${row.zipcode} <br>
</c:forEach>
</body>
</html>

