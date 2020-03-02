<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, BoardClass.*" %>
<%
String s = request.getParameter("id");
int id = Integer.parseInt(s);
MemberDAO.Delete(id);
response.sendRedirect("PostMain.jsp");
%>
