<%@include file='header.jsp'%>
<div class="container">
<h2>Shipping And Billing Addresses</h2>
<br><br>
<form action="${pageContext.servletContext.contextPath}/payment" method="post">
<p class="validation">${message}</p>
	
	<div class="buttonRow">
		<input class="btn btn-primary" type="submit" value="Select Ship Address"  onclick="window.location='${pageContext.servletContext.contextPath}/selectshipaddress'">
		<input class="btn btn-primary" type="submit" value="Select Bill Address" onclick="window.location='${pageContext.servletContext.contextPath}/selectbilladdress'">
		<input class="btn btn-secondary" type="submit" value="Back To Cart" onclick="goBack()">
	</div>
<br><br>
		<table id="shippingbilling">
			<tr>
			    <th>Address</th>
			    <th>Shipping Address</th> 
			    <th>Billing Address</th>
			  </tr>
			<tr>
				<td>Street</td>
				<td><input id="shipStreet" class="form-control " type="text" value="${shippingAddress.street }" name="shipStreet"/></td>
				<td><input id="billStreet"  class="form-control " type="text" value="${billingAddress.street }" name="billStreet"/></td>
			</tr>
			<tr>
				<td>City</td>
				<td><input id="shipCity" class="form-control " type="text" value="${shippingAddress.city }" name="shipCity"/></td>
				<td><input id="billCity" class="form-control " type="text" value="${billingAddress.city }" name="billCity"/></td>
			</tr>
			<tr>
				<td>State</td>
				<td><input id="shipState" class="form-control " type="text" value="${shippingAddress.state }" name="shipState"/></td>
				<td><input id="billState" class="form-control " type="text" value="${billingAddress.state }" name="billState"/></td>
			</tr>
			<tr>
				<td>Zip</td>
				<td><input id="shipZip" class="form-control " type="text" value="${shippingAddress.zip }" name="shipZip"/></td>
				<td><input id="billZip" class="form-control " type="text" value="${billingAddress.zip }" name="billZip"/></td>
			</tr>
		</table>
		<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-3">
		<div style="padding:12px;">
			<input id="sameShipBill" type="checkbox" name="sameShipBill" value="sameShipBill"> Billing Same As Shipping?<br>
		  	<input type="checkbox" name="saveShippingAdd" value="saveShippingAdd">Save Shipping Address<br>
		  	<input type="checkbox" name="saveBillingAdd" value="saveBillingAdd">Save Billing Address<br>
		</div>
		</div>
		<div class="col-md-4">	<div style="padding:25px;">	<input class="btn btn-success" type="submit" value="Checkout" /></div></div>
		<div class="col-md-2"></div>
	
	

		</div>
			
		</form>
	
</div>
	<script>
	
	$('#shippingbilling').DataTable({"bPaginate": false, "bLengthChange": false,  "scrollY": 400});
		function goBack() {
			window.history.back();
		}		
		$("#sameShipBill").click(function(){
		    if($(this).prop("checked") == true){
		    	$("#billStreet").val($("#shipStreet").val());
		        $("#billCity").val($("#shipCity").val());
		        $("#billState").val($("#shipState").val());
		        $("#billZip").val($("#shipZip").val());
		        
		    } else {
		    	//console.log("checkbox unchecked");

		    }
		});
	</script>

<%@include file='footer.jsp'%>