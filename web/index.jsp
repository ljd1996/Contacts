<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/7/18
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
  <title>通讯录</title>
  <link rel="stylesheet" type="text/css" href="style.css">
  <script type="text/javascript" src="script.js"></script>
</head>

<body>

<table class="work">
  <tr>
    <td id="add_contact">添加</td>
    <td id="search_contact">查询</td>
  </tr>
</table>

<table class="head">
  <tr>
    <td title="name">姓名</td>
    <td title="number">学号</td>
    <td title="phone">手机号码</td>
  </tr>
</table>

<table id="table_show">
</table>

<table class="page_work">
  <tr>
    <td id="first_page">首页</td>
    <td id="pre_page">上一页</td>
    <td id="next_page">下一页</td>
    <td id="last_page">末页</td>
    <td>
      <select id="select_page" name="page_go">
        <option value="1">1</option>
        <option value="2">2</option>
      </select>
    </td>
  </tr>
</table>

<form name="addInf" class="add-inf-form">
  <div class="close" id="close"></div>
  <input type="text" name="name" placeholder="姓名" id="name">
  <input type="text" name="number" placeholder="学号" id="number">
  <input type="text" name="phone" placeholder="手机" id="phone">
  <input type="submit" value="确定" id="submitInfo">
</form>

<form name="searchInf" class="add-inf-form">
  <div class="close" id="close_search"></div>
  <select id="searchBy">
    <option value="by_name">按姓名</option>
    <option value="by_num">按学号</option>
  </select>
  <input type="text" name="input" placeholder="姓名或学号">
  <input type="submit" value="确定" id="submitSearch">
</form>

<table id="table_show_result">
</table>

<div id="empty"></div>
</body>
</html>
