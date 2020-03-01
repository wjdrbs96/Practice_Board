<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%> 
<%@ page import="java.util.*, java.text.*, BoardClass.*" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<%
int currentPage = 1;
int pageSize = 10;

String srchText = request.getParameter("srchText");

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
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<title>Insert title here</title> 
</head> 
<body> 



<div class="container"> 
<form class="form-inline">
  <div class="form-group">
    <label>검색</label>
    <input type="text" class="form-control" name="srchText"  placeholder="제목을 입력하세요" />
  </div>
  <button type="submit" class="btn btn-primary">조회</button>
</form>



<!-- container는 표를 가운데 정렬하기 위해서 -->
<table class="table table-hover table table-striped"> 
<!-- table-hover클래스는 마우스를 올리면 회색이 생기게
table-striped클래스는 홀수번째 테이블에 회색이 생겨있게-->
<!-- table table-bordered 이 클래스는 테이블의 선 그어주기--> 

<tr> 
<th>번호</th>
<th>작성자</th> 
<th>제목</th> 
<th>조회수</th>
<th>날짜</th>  
</tr> 

<% for (int i = 0; i < list.size(); ++i) { %>
	<tr>
		<a><th><%= list.get(i).getPostId() %></th></a>
		<th><%= list.get(i).getMemberId() %></th>
		<th><%= list.get(i).getTitle() %></th>
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
