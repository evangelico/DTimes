<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible" />
<title>D.Time's - Dance & Fitness Accademy - Gestione</title>
<%
	String WWWroot = request.getContextPath();
	pageContext.setAttribute("WWWroot", WWWroot);

	String WWWstatic = WWWroot + "/static";
	pageContext.setAttribute("WWWstatic", WWWstatic);

	ActionContext actionCtx = ActionContext.getContext();
	if (actionCtx != null) {
		actionCtx.put("WWWroot", WWWroot);
		actionCtx.put("WWWstatic", WWWstatic);
	}
%>
<link rel="stylesheet" type="text/css" href="<%=WWWstatic%>/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=WWWstatic%>/css/style_menu_dropdown.css" />
<link rel="stylesheet" href="<%=WWWstatic%>/css/jquery-ui_1.11.1.css" />
<script src="<%=WWWstatic%>/js/jquery_1.8.2.min.js" type="text/javascript">
	/**/
</script>
<script type="text/javascript" src="<%=WWWstatic%>/js/jquery-ui_1.11.1.min.js">
	/* */
</script>
<script type="text/javascript" src="<%=WWWstatic%>/js/datepicker-it.js">
	/* */
</script>
<script type="text/javascript" src="<%=WWWstatic%>/js/util.js">
	/* */
</script>
<script type="text/javascript">
	function DropDown(el) {
		this.dd = el;
		this.placeholder = this.dd.children('span');
		this.opts = this.dd.find('ul.dropdown > li');
		this.val = '';
		this.index = -1;
		this.initEvents();
	}
	DropDown.prototype = {
		initEvents : function() {
			var obj = this;

			obj.dd.on('click', function(event) {
				$('.wrapper-dropdown.multi').removeClass('active');
				$(this).toggleClass('active');
				return false;
			});

			obj.opts.on('click', function() {
				window.location = $(this).find("a").prop('href');

			});
		},
	}

	$(function() {

		var dd = new DropDown($('.wrapper-dropdown.multi'));

		$(".datepick").datepicker({
			dateFormat : "dd/mm/yy",
			changeMonth : true,
			changeYear : true,
			yearRange : "1900:2200"
		});
		$(".datepick").datepicker($.datepicker.regional["it"]);

		$(document).click(function() {
			// all dropdowns
			$('.wrapper-dropdown.multi').removeClass('active');
		});

	});
</script>
</head>
<body>