   
<jsp:include page="header.jsp" /> 
<%@ page import="com.files.models.Auction" %>
<%
	if(request.getSession().getAttribute("user")==null)
    response.sendRedirect("login.jsp");
	
	Auction auction=null;
	try{
	auction=((Auction)request.getAttribute("auction"));
	//out.println(auction.toString());
		if(auction==null) response.sendRedirect("dashboard.jsp");
	}
	catch(Exception e){
		out.println(e);
		e.printStackTrace();
	}
%>
    <main class="bg-secondary">
        <div class="container p-4">
            <div class="row">
                <div class="col-md-8 offset-md-2">
                    <div class="card2 mx-2 p-4">
                        <h4 class="py-2">Update Auction</h4>
                        <form action="AuctionControllerServlet" method="POST">
                        	<input type="hidden" name="command" value="update_auction"/>
                        	<input type="hidden" name="auction_id" value="${auction.id}"/>
                            <div class="form-group">
                                <label for="auction_name">Auction Name :</label>
                                <input type="text" name="auction_name" id="" class="form-control" value="${auction.auction_name}">
                            </div>

                            <div class="form-group">
                                <label for="item_name">Item Name :</label>
                                <input type="text" name="item_name" id="" class="form-control" value="${auction.item_name}">
                            </div>

                            <div class="form-group">
                                <label for="start_time">Start Time :</label>
                                <% String arr[]=auction.getStart_timestamp().split(" "); String stamp=arr[0]+"T"+arr[1]; stamp=stamp.substring(0,16);%>
                                <input type="datetime-local" name="start_time" id="" class="form-control" value="<%=stamp%>">
                            </div>

                            <div class="form-group">
                                <label for="end_time">End Time :</label>
                                <%  arr=auction.getEnd_timestamp().split(" ");  stamp=arr[0]+"T"+arr[1]; stamp=stamp.substring(0,16); %>
                                <input type="datetime-local" name="end_time" id="" class="form-control" value="<%=stamp%>">
                            </div>

                            <div class="form-group">
                                <label for="min_bid">Minimum Bid Amount :</label>
                                <input type="number" name="min_bid" id="" class="form-control" value="${auction.min_value}">
                            </div>
                            
                            <div class="form-group">
                                <label for="is_active">is Active :</label>
                                <select name="is_active" class="form-control">
                                	<option value="1" <%= auction.isIs_active()?"selected":"" %> >Active</option>
                                	
                                	<option value="0" <%= !auction.isIs_active()?"selected":"" %>  >InActive</option>
                                </select>
                            </div>

                            <button type="submit" class="btn btn-primary w-100 my-4">Update a Auction</button>
                        </form>
                    </div>
                </div>
        </div>
    </main>
       
<jsp:include page="footer.jsp" /> 