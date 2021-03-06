<!--
MIT License
Copyright (c) 2020 dfighter1985
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="stylesheet" href="<c:url value="/css/bootstrap-4.0.0.min.css" />">
		
		<title><spring:message code="site.title" /></title>
	</head>
	<body>
                <div class="container">
                    <nav class="navbar navbar-expand-lg navbar-light bg-light">
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
                                    <li class="nav-item"><a class="nav-link" href="<c:url value="/" />"><spring:message code="nav.home" /></a></li>
                                    <li class="nav-item"><a class="nav-link" href="<c:url value="/resolve" />"><spring:message code="nav.resolve" /></a></li>
                                    <li class="nav-item"><a class="nav-link" href="<c:url value="/reverse-resolve" />"><spring:message code="nav.reverse-resolve" /></a></li>
                                    <li class="nav-item"><a class="nav-link" href="<c:url value="/register" />"><spring:message code="nav.register" /></a></li>
                                    <li class="nav-item"><a class="nav-link" href="<c:url value="/delete" />"><spring:message code="nav.delete" /></a></li>
                                    <li class="nav-item"><a class="nav-link" href="<c:url value="/remap" />"><spring:message code="nav.remap" /></a></li>
				</ul>
                                
                                <ul class="navbar-nav ml-auto">
                                    <li class="nav-item"><a class="nav-link" href="?lang=hu"><spring:message code="nav.hungarian" /></a></li>
                                    <li class="nav-item"><a class="nav-link" href="?lang=en"><spring:message code="nav.english" /></a></li>
                                </ul>                                
			</div>
                    </nav>
            
                
                    <spring:message code="remap.remove" />

                    <form action="<c:url value="/remap" />" method="GET">
                        URN<br/>
                        <input type="text" name="urn" value="${urn}"/><br/>
                        Old URL<br/>
                        <input type="text" name="oldurl" value="${oldurl}"/><br/>
                        New URL<br/>
                        <input type="text" name="newurl" value="${newurl}"/><br/>
                        TID<br/>
                        <input type="text" name="tid" value="${tid}"/><br/>
                        <input type="submit" value="<spring:message code="remap.confirm" />"/>
                    </form>
                    
                    <H1 class="text-danger">${Error}</H1>
                    <H1 class="text-success">${Message}</H1>
                </div>
                
                <script src="<c:url value="/js/jquery-3.2.1.slim.min.js" />"></script>
                <script src="<c:url value="/js/popper-1.12.9.min.js" />"></script>
                <script src="<c:url value="/js/bootstrap-4.0.0.min.js" />"></script>
	</body>
</html>
