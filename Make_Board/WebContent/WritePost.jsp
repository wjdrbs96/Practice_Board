<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html lang="ko"> 
<head> 
<script src="http://code.jquery.com/jquery-latest.js"></script> 
<!-- jQuery받아오기 위해서 --> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<!-- 합쳐지고 최소화된 최신 CSS --> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<title>Insert title here</title> 
</head> 
<body> 

<div class="container"> 
<!-- container는 표를 가운데 정렬하기 위해서 -->
<table class="table table-hover table table-striped"> 
<!-- table-hover클래스는 마우스를 올리면 회색이 생기게
table-striped클래스는 홀수번째 테이블에 회색이 생겨있게-->
<!-- table table-bordered 이 클래스는 테이블의 선 그어주기--> 

<tr> 
<th>번호</th> 
<th>제목</th> 
<th>작성자</th> 
<th>날짜</th> 
<th>조회수</th> 
</tr> 

<tr> 
<td>1</td> 
<td>bbbbbbb</td> 
<td>ccccccc</td> 
<td>2017.07.04</td> 
<td>3</td> 
</tr> 


<tr> 
<td>2</td> 
<td>bbbbbbb</td> 
<td>ccccccc</td> 
<td>2017.07.05</td> 
<td>67</td> 
</tr> 


<tr> 
<td>3</td> 
<td>bbbbbbb</td> 
<td>ccccccc</td> 
<td>2017.07.06</td> 
<td>5</td> 
</tr> 


<tr> 
<td>4</td> 
<td>bbbbbbb</td> 
<td>ccccccc</td> 
<td>2017.07.08</td> 
<td>34</td> 
</tr> 
</table> 

<a class="btn btn-default pull-right">글쓰기</a> 

<div class="text-center"> 
<ul class="pagination">
<!-- pagination클래스는 번호를 이쁘게 꾸며준다 -->
<li><a href="#">1</a></li> 
<li><a href="#">2</a></li> 
<li><a href="#">3</a></li> 
<li><a href="#">4</a></li> 
<li><a href="#">5</a></li> 
</ul> 
</div> 
</div> 
</body> 
</html>

