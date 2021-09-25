<%@  taglib   uri="http://java.sun.com/jsp/jstl/core"  prefix="c"  %>
<%@ page import="com.files.models.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Bidding</title>
    <link rel="stylesheet" href="assets/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.1/css/bootstrap.min.css" integrity="sha512-6KY5s6UI5J7SVYuZB4S/CZMyPylqyyNZco376NM2Z8Sb8OxEdp02e1jkKk/wZxIEmjQ6DRCEBhni+gpr9c4tvA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-1ycn6IcaQQ40/MKBW2W4Rhis/DbILU74C1vSrLJxCq57o941Ym01SwNsOMqvEBFlcgUa6xLiPY/NS5R+E6ztJQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.css" integrity="sha512-UTNP5BXLIptsaj5WdKFrkFov94lDx+eBvbKyoe1YAfjeRPC+gT5kyZ10kOHCfNZqEui1sxmqvodNUx3KbuYI/A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css" integrity="sha512-sMXtMNL1zRzolHYKEujM2AqCLUR9F2C4/05cdbxjjLSRvMQIciEPCQZo++nk7go3BtSuK9kfa/s+a4f4i5pLkw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
    <header>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
              <a class="navbar-brand" href="index.jsp"> <img src="assets/OBidding.png" class="logo" alt=""> </a>
              <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                  <li class="nav-item">
                    <a class="nav-link <%=request.getParameter("command")==null?"active":"" %>" aria-current="page" href="index.jsp"><i class="fas fa-home"></i> Home</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="#"><i class="far fa-comment-dots"></i> Notifications</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link <%=request.getParameter("command")!=null?(request.getParameter("command").equals("available_auctions")?"active":""):"" %>" href="BidControllerServlet?command=available_auctions"><i class="far fa-comment-dots"></i> Auctions</a>
                  </li>
                </ul>
                <span class="navbar-text">
                 <% 
			    	User user=(User)request.getSession().getAttribute("user");
			    	//if(user!=null){
			    	%>
			    	<c:choose>
					  	<c:when test="${user!=null}"> 
						  	<div class="btn-group dropstart">
							  <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
							    <c:out value="${user.getName() }"/>
							  </button>
							  <ul class="dropdown-menu">
							   <li><a class="dropdown-item" href="HomeControllerServlet?page=dashboard">DashBoard</a></li>
							    <li>
							    	<form action="LoginControllerServlet" method="POST">
							    		<input type="hidden" name="command" value="logout">
							    		<button type="submit" class="dropdown-item">Logout</button>
							    	</form>
							    </li>
							  </ul>
							</div>
						</c:when>
					  <c:otherwise>
					    <p><a class='btn btn-secondary' href='login.jsp'>Login</a></p>
					  </c:otherwise>
					</c:choose>
                </span>
              </div>
            </div>
          </nav>
    </header>