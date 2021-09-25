
<jsp:include page="header.jsp" /> 
    <main>
        <div class="container-fluid">
            <div class="row my-3">
                <div class="col-md-6 offset-md-3 my-3">
                    <div class="card my-4">
                    	
                        <div class="card-header">Register</div>
                        <div class="card-body p-3">
                        <p class='text-danger text-center'>
                        <%
                    		String warning=(String)request.getSession().getAttribute("warning");
                    		if(warning!=null){
                    			out.println(""+warning+"");
                    		}
                    	%>
                    	</p>
                            <form action="LoginControllerServlet" method="POST">
                            	<input type="hidden" name="command" value="register">
                                <div class="row my-2">
                                    <div class="col-md-2 py-2">
                                    <label for="name">Name :</label></div>
                                    <div class="col-md-10"><input type="text" name="name" id="" placeholder="Enter Name" class="form-control"></div>
                                </div>
                                <!-- <div class="row my-2">
                                    <div class="col-md-2 py-2">
                                    <label for="name">Location :</label></div>
                                    <div class="col-md-10"><input type="text" name="location" id="" placeholder="Enter Location" class="form-control"></div>
                                </div>-->
                                <div class="row my-2">
                                    <div class="col-md-2 py-2">
                                    <label for="email">Email :</label></div>
                                    <div class="col-md-10"><input type="text" name="email" id="" placeholder="Enter Email" class="form-control"></div>
                                </div>
                                <div class="row my-2">
                                    <div class="col-md-2 py-2">
                                    <label for="password">Password :</label></div>
                                    <div class="col-md-10">
                                    <input type="password" name="password" id="" placeholder="Enter Password" class="form-control">
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col-md-2">
                                    <label for="confirm">Confirm Password :</label></div>
                                    <div class="col-md-10"><input type="password" name="confirm" id="" placeholder="Enter Confirm Password" class="form-control"></div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 offset-md-3">
                                        <button type="submit" class="btn btn-md btn-primary w-100 mx-4">Register</button>
                                        <p class="text-center">Already have a Account? <a href="login.jsp">login</a></p>
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