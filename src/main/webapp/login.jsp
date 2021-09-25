
<jsp:include page="header.jsp" /> 
    <main>
        <div class="container-fluid">
            <div class="row my-3">
                <div class="col-md-6 offset-md-3 my-3">
                    <div class="card my-4">
                    	
                        <div class="card-header">Login</div>
                        <%
                    		String warning=(String)request.getSession().getAttribute("warning");
                    		if(warning!=null){
                    			out.println("<p class='text-danger text-center'>"+warning+"</p>");
                    		}
                    	%>
                        <div class="card-body p-3">
                            <form action="LoginControllerServlet" method="POST">
                            	<input type="hidden" name="command" value="login"/>
                                <div class="row my-2">
                            
                                <!-- <div class="form-group my-2"> -->
                                    <div class="col-md-2 py-2">
                                    <label for="email">Email :</label></div>
                                    <div class="col-md-10"><input type="text" name="email" value="gaurav@gmail.com" id="" placeholder="Enter Email" class="form-control"></div>
                                <!-- </div> -->
                                </div>
                                <div class="row my-2">
                                    <div class="col-md-2 py-2">
                                    <label for="password">Password :</label></div>
                                    <div class="col-md-10">
                                    <input type="password" name="password" value="1234" id="" placeholder="Enter Password" class="form-control">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 offset-md-3">
                                        <button type="submit" class="btn btn-md btn-danger w-100 mx-4">Login</button>
                                        <p class="text-center">Don't have a Account? <a href="register.jsp">Create One</a></p>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
  
<jsp:include page="footer.jsp" /> 