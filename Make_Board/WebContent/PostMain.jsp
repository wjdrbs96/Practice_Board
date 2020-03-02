<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%> 
<%@ page import="java.util.*, java.text.*, BoardClass.*" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<%
int currentPage = 1;
int pageSize = 10;

String srchText = request.getParameter("srchText");

if (srchText == null) srchText = "";
	
List<Post> list = MemberDAO.findBytitle(srchText,currentPage, pageSize);

int recordCount = MemberDAO.count(srchText);

String pg = request.getParameter("pg");
if (pg != null) {
	currentPage = Integer.parseInt(pg);
}
// 필요한 코드 작성
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html lang="ko"> 
<head> 

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">


<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<title>Insert title here</title> 
</head> 
<body> 



<div class="container"> 

<form class="form-inline">
  <div class="form-group">
    <label>검색</label>
    <input type="text" size=20 class="form-control" name="srchText" value="<%= srchText %>" placeholder="제목을 입력하세요" >
  </div>
  <button type="submit" class="btn btn-primary">조회</button>
</form>



<table class="table table-hover table table-striped"> 


<tr> 
<th>번호</th>
<th>작성자</th> 
<th>제목</th> 
<th>조회수</th>
<th>날짜</th>  
</tr> 

<% for (int i = 0; i < list.size(); ++i) { %>
	<tr>
		<th><%= list.get(i).getPostId() %></th>
		<th><%= list.get(i).getMemberId() %></th>
		<th><a href="PostView.jsp?id=<%=list.get(i).getPostId()%>"><%= list.get(i).getTitle() %></a></th>
		<th><%= list.get(i).getCount() %></th>
		<th><%= list.get(i).getCreateDateTime() %></th>
	</tr>
<% } %>
</table> 

<a href="WritePost.jsp" class="btn btn-default pull-right">글쓰기</a> 

<my:pagination pageSize="<%= pageSize %>" recordCount="<%= recordCount %>" queryStringName="pg" />
</div> 
</body> 
</html>
