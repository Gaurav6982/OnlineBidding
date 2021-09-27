
<jsp:include page="header.jsp" /> 
<%
	if(request.getSession().getAttribute("user")==null)
    response.sendRedirect("login.jsp");
%>
    <main class="bg-secondary">
        <div class="container py-4">
            <div class="card2 my-2 p-2">
                <h4 class="p-2">Dashboard</h4>
                <%
                	String msg=request.getAttribute("success-msg")==null?"":request.getAttribute("success-msg").toString();
                %>

			         <p class="text-center text-success"><%=msg %><p>
                <div class="row py-4">
                  <div class="col-md-12">
                    <div class="d-flex justify-content-center">
                      <div class="big-btns">
                        <a href="AuctionControllerServlet?page=create_auction" class="btn btn-primary h-100 w-100"><i class="fas fa-plus dash-icon"></i></a>
                        <span class="text-center px-3">Create Auction</span>
                      </div>
                      <div class="big-btns">
                        <a href="AuctionControllerServlet?page=auction_history" class="btn btn-warning h-100 w-100"><i class="fas fa-history dash-icon"></i></a>
                        <span class="text-center px-3">Auction History</span>
                      </div>
                      <div class="big-btns">
                        <a href="AuctionControllerServlet?page=my_bids" class="btn btn-secondary h-100 w-100"><i class="fas fa-money-check-alt dash-icon"></i></a>
                        <span class="text-center px-3">Auction History</span>
                      </div>
                    </div>
                  </div>
                </div>
            </div>
        </div>
    </main>
    
<jsp:include page="footer.jsp" /> 