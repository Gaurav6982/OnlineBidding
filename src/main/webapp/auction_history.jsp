
<jsp:include page="header.jsp" /> 
<%@ page import="com.files.models.Auction" %>
<%@ page import="java.util.ArrayList" %>
<%@  taglib   uri="http://java.sun.com/jsp/jstl/core"  prefix="c"  %>
<%
	if(request.getSession().getAttribute("user")==null)
    response.sendRedirect("login.jsp");
	ArrayList<Auction> active_auctions=(ArrayList<Auction>)request.getAttribute("active_auctions");
	ArrayList<Auction> inactive_auctions=(ArrayList<Auction>)request.getAttribute("inactive_auctions");
	String successMsg=request.getAttribute("success-msg")!=null?request.getAttribute("success-msg").toString():"";
%>
	
    <main class="bg-secondary">
        <div class="container p-4">
            <div class="row">
                <div class="col-md-10 offset-md-1">
                    <div class="card2 mx-2 p-4 bg-dark text-light">
                        <h4 class="py-2 text-center">Auction History</h4>
                         <p class="text-center text-success"><%=successMsg%></p>
                        <hr class="bg-light">
                        <h5 class="py-1">Active </h5>
                        <div class="row">
                        	<c:if test="${active_auctions.size()==0}">
                        		<a href="create_auction.jsp" class="btn btn-sm btn-primary">Create Auction</a>
                        	</c:if>
                        	 <c:forEach items="${active_auctions}" var="auction">
						          <div class="col-md-12 my-2">
		                                <div class="card2 bg-dark">
		                                    <div class="d-flex justify-content-between">
		                                        <div class="auction_name">
		                                            ${auction.getAuction_name() }
		                                        </div>
		                                        <div class="auction_actions d-flex justify-content-end">
		                                            <a href="AuctionControllerServlet?page=update_auction&id=${auction.id}" class="btn btn-warning btn-sm">Update</a>
		                                            <form method="POST" action="AuctionControllerServlet">
			                                            <input type="hidden" name="command" value="delete_auction">
			                                            <input type="hidden" name="auction_id" value="${auction.id }">
			                                            <button type="submit" class="btn btn-danger btn-sm mx-3">Delete</button>
		                                            </form>
		                                        </div>
		                                    </div>
		                                </div>
		                            </div>
						      </c:forEach>
                           
					
                        </div>

                        <hr class="bg-light">

                        <h5 class="py-1 fw-bold">History</h5>
                        <div class="row">
	                        <c:forEach items = "${inactive_auctions}" var="auction">
	                            <div class="col-md-12 my-2">
	                                <div class="card2 bg-dark">
	                                    <div class="d-flex justify-content-between">
	                                        <div class="auction_name text-disabled">
	                                            ${auction.auction_name }
	                                        </div>
	                                        <div class="auction_actions d-flex justify-content-end">
	                                            <a href="BidControllerServlet?command=show_auction&auction=${auction.id }" class="btn btn-warning btn-sm">View Details</a>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
							</c:forEach>
                            

                            

                     
                        </div>
                    </div>
                </div>
        </div>
    </main>

    
<jsp:include page="footer.jsp" /> 