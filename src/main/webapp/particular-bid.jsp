
<jsp:include page="header.jsp" /> 
<%@  taglib   uri="http://java.sun.com/jsp/jstl/core"  prefix="c"  %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.files.models.Auction" %>
<%@ page import="com.files.models.Bidding" %>
<%
if(request.getSession().getAttribute("user")==null)
    response.sendRedirect("login.jsp");
%>
    <main class="bg-secondary">
        <div class="container p-4">
            <div class="d-flex">
                <div class="card2 mx-2 p-4 w-25">
                	<c:if test="${biddings.size()>0}">
                    <h5 class="text-center"> Top Biddings</h5>
                    <ul class="text-left">
                    	<c:forEach items="${biddings}" var="bid">
                        	<li>${users.get(bid.getUser_id()) } : Rs. ${bid.amount }</li>
                        </c:forEach>
                    </ul>
                    </c:if>
                    <c:if test="${biddings.size()==0}">
                     <h5 class="text-center"> No Biddings Yet.</h5>
                    </c:if>
                    
                </div>
                <div class="card2 mx-2 p-4 w-100">
                    <p>
                        <span class="fw-bold">Auction For ${item_name}</span>
                    </p>
                    <!-- <p>
                        <span class="fw-bold">Created by : </span> Name
                    </p>
                    <p>
                        <span class="fw-bold">Description : </span>
                        <span>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Aspernatur quos quisquam molestiae adipisci, repellendus repellat hic quia est a sint ad. A, rem nemo aperiam asperiores voluptates sed accusantium aliquid.</span>
                    </p> -->
                    <c:if test="${biddings.size()>0}">
                    <p>
                        <span class="fw-bold">Top Bid By : </span>
                        <span>${top_bidder_name }</span>
                    </p>
                    
                    <p>
                        <span class="fw-bold">Top Bid Amount : </span>
                        <span>Rs. ${top_bidding_amount }</span>
                    </p>
                    </c:if>
                    <div class="form-bid">
                        <div class="row">
                            <div class="col-md-8 offset-md-2">
                                <form action="">
                                    <div class="row">
                                        <div class="col-md-3 py-1">Enter Amount:</div>
                                        <div class="col-md-5">
                                            <input type="number" name="amount" id="" class="form-control">
                                        </div>
                                        <div class="col-md-4">
                                            <button type="submit" class="btn btn-primary">Place a Bid</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <br>
                    <p>Your last Bid Value : ${last_bid_value}</p>
                </div>
                

            </div>

            
        </div>
    </main>
   
<jsp:include page="footer.jsp" /> 