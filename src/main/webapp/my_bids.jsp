
<jsp:include page="header.jsp" /> 
<%@  taglib   uri="http://java.sun.com/jsp/jstl/core"  prefix="c"  %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Map.*" %>
<%@ page import="com.files.models.Auction" %>
<%@ page import="com.files.models.Bidding" %>
<%
if(request.getSession().getAttribute("user")==null)
    response.sendRedirect("login.jsp");
%>
    <main class="bg-secondary">
        <div class="container p-4">
        	<c:if test="${biddings.size()==0 }">
        		<h2 class="text-center text-white">You have not Placed a bid yet.</h2>
        		<p class="text-center text-white">Make Your first bid by clicking <a href="BidControllerServlet?command=available_auctions" class="btn btn-primary btn-sm">here</a></p>
        	</c:if>
        	<c:if test="${biddings.size()>0 }">
        	<div class="table table-responsive">
        		<table class="w-100 bg-light custom-table">
        			<tr>
        				<th>Auction Name</th>
        				<th>My Bid Amount</th>
        				<th>Top Bidding Value</th>
        				<th>Action</th>
        			</tr>
        			<c:forEach items="${biddings}" var="bid">
        				<tr>
        					<td>${auctions.get(bid.getAuction_id()).getAuction_name()}</td>
        					<td><c:out value="${bid.amount}"/></td>
        					<td>${top_bidders.get(bid.getAuction_id()).getUser().getName() } : Rs. ${top_bidders.get(bid.getAuction_id()).getAmount() }</td>
        					<td class="d-flex justify-content-center"><a href="BidControllerServlet?command=show_auction&auction=${bid.auction_id }" class="btn btn-sm btn-warning">View</a></td>
        				</tr>
        			</c:forEach>
        		</table>
        	</div>
        	</c:if>
        </div>
    </main>
   
<jsp:include page="footer.jsp" /> 