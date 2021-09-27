
<jsp:include page="header.jsp" /> 
<%@  taglib   uri="http://java.sun.com/jsp/jstl/core"  prefix="c"  %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.files.models.Auction" %>
<%
	//if(request.getSession().getAttribute("user")==null)
    //response.sendRedirect("login.jsp");
%>
    <main class="bg-secondary p-0 m-0">
    	
    	<p class="text-warning text-center">${warning==null?"":warning}</p>
    	
        <div class="container p-10">
        	<c:if test="${available_auctions.size()==0 && upcoming_auctions.size()==0 }">
        	<div class="card2 my-2 p-2">
        		<h3>No Auctions Found!</h3>
        	</div>
        	</c:if>
        	<c:if test="${available_auctions.size()>0 }">
            <div class="card2 my-2 p-2">
                <h4 class="p-2">Available Auctions</h4>
                <div class="row p-2">
                	<c:forEach var="auction" items="${available_auctions}">
                    <div class="col-md-4 my-2 h-100">
                        <div class="card2">
                            <!-- <div class="card-img">
                                <img src="back1.jpg" alt="">
                            </div> -->
                            <div class="card-body">
                            	<p class="fw-bold"><c:out value="${auction.auction_name }"/> : <br/> <i>for ${auction.item_name }</i></p>
                            	<hr>
                            	<c:if test="${biddings_mapping.get(auction.getId()).size()>0 }">
                                <p class="text-center fw-bold">Top Biddings</p>
                                <ul class="biddings-list">
                                	<c:forEach items="${biddings_mapping.get(auction.getId())}" var="bid">
                                    <li>${users.get(bid.getUser_id()) } : Rs. ${bid.amount }</li>
                                    </c:forEach>
                                </ul>
                                </c:if>
                                <p class="text-center">Ends in: <span class="countdown" data-countdown="${auction.end_timestamp }"></span></p>
                            </div>
                            <div class="card-footer d-flex justify-content-center">
                                <a href="BidControllerServlet?command=show_bid&auction=${auction.id }" class="btn btn-sm btn-danger w-50">Bid</a>
                            </div>
                            
                        </div>
                    </div>
					</c:forEach>

                </div>
            </div>
            </c:if>
             <c:if test="${upcoming_auctions.size()>0 and availalble_auctions.size()>0 }">
            <hr>
            </c:if>
            <c:if test="${upcoming_auctions.size()>0 }">
            <div class="card2 my-2 p-2">
                <h4 class="p-2">Upcoming Auctions</h4>
                <div class="row p-2">
                	<c:forEach var="auction" items="${upcoming_auctions}">
                    <div class="col-md-4 my-2 h-100">
                        <div class="card2">
                            <!-- <div class="card-img">
                                <img src="back1.jpg" alt="">
                            </div> -->
                            <div class="card-body">
                            	<p class="fw-bold"><c:out value="${auction.auction_name }"/> : <br/> <i>for ${auction.item_name }</i></p>      
                            </div>
                            <div class="card-footer d-flex justify-content-center countdown" data-countdown="${auction.start_timestamp }">
                                
                            </div>
                        </div>
                    </div>
					</c:forEach>

                </div>
            </div>
            </c:if>
        </div>
    </main>
 
<jsp:include page="footer.jsp" /> 