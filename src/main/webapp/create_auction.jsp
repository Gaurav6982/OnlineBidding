
<jsp:include page="header.jsp" /> 
<%
	if(request.getSession().getAttribute("user")==null)
    response.sendRedirect("login.jsp");
%>
<%@  taglib   uri="http://java.sun.com/jsp/jstl/core"  prefix="c"  %>
    <main class="bg-secondary">
        <div class="container p-4">
            <div class="row">
                <div class="col-md-8 offset-md-2">
                    <div class="card2 mx-2 p-4">
                        <h4 class="py-2">Create Auction</h4>
                        <form action="AuctionControllerServlet" method="POST">
                            <input type="hidden" name="command" value="create_auction" >	
                            <div class="form-group">
                                <label for="auction_name">Auction Name :</label>
                                <input type="text" name="auction_name" id="" class="form-control" value="sample" required>
                            </div>

                            <div class="form-group">
                                <label for="item_name">Item Name :</label>
                                <input type="text" name="item_name" id="" class="form-control" value="sample" required>
                            </div>

                            <div class="form-group">
                                <label for="start_time">Start Time :</label>
                                <input type="datetime-local" name="start_time" id="start_time" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="end_time">End Time :</label>
                                <input type="datetime-local" name="end_time" id="end_time" class="form-control" required>
                            </div>

                            <!-- <div class="form-group">
                                <label for="image">Item Images :</label>
                                <input type="file" name="image" multiple id="" class="form-control" >
                            </div> -->

                            <div class="form-group">
                                <label for="min_bid">Minimum Bid Amount :</label>
                                <input type="number" name="min_bid" id="" value="100" min="0" class="form-control" required>
                            </div>

                            <button type="submit" class="btn btn-primary w-100 my-4">Host a Auction</button>
                        </form>
                    </div>
                </div>
        </div>
    </main>
   
<jsp:include page="footer.jsp" /> 