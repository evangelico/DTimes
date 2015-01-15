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
<link rel="stylesheet" type="text/css" href="<%=WWWstatic%>/css/fattura.css"/>
<link rel="stylesheet" type="text/css" media="print" href="<%=WWWstatic%>/css/fattura_stampa_A5.css" />
</head>
<body>