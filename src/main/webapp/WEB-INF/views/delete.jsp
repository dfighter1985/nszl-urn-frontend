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
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="stylesheet" href="<c:url value="/css/bootstrap-4.0.0.min.css" />">
		
		<title>URN resolver service</title>
	</head>
	<body>
                <div class="container">
                    <nav class="navbar navbar-expand-lg navbar-light bg-light">
                            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                            </button>
                            <div class="collapse navbar-collapse" id="navbarNav">
                                    <ul class="navbar-nav mr-auto">
                                            <li class="nav-item"><a class="nav-link" href="/">Home</a></li>
                                            <li class="nav-item"><a class="nav-link" href="/resolve">Resolve</a></li>
                                            <li class="nav-item"><a class="nav-link" href="/register">Register</a></li>
                                            <li class="nav-item"><a class="nav-link" href="/delete">Delete</a></li>
                                    </ul>
                            </div>
                    </nav>
            
                
                    Enter the URN - URL pair you'd like to delete

                    <form action="/delete" method="GET">
                        URN<br/>
                        <input type="text" name="urn" value="${urn}"/><br/>
                        URL<br/>
                        <input type="text" name="url" value="${url}"/><br/>
                        <input type="submit" value="Delete!"/>
                    </form>
                    
                    <H1 class="text-danger">${Error}</H1>
                    <H1 class="text-success">${Message}</H1>
                </div>
                
                <script src="<c:url value="/js/jquery-3.2.1.slim.min.js" />"></script>
                <script src="<c:url value="/js/popper-1.12.9.min.js" />"></script>
                <script src="<c:url value="/js/bootstrap-4.0.0.min.js" />"></script>
	</body>
</html>
