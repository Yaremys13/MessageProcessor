<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://code.jquery.com/jquery-latest.js"></script>

<title>Market Trade Processor</title>

<link rel = "stylesheet" href = "graficStyle.css" type = "text/css"/>

<script type="text/javascript">

//Functions to show and hide 1by1 form
function show(){
document.getElementById('l1by1').style.display = 'block';}

function hide(){
	document.getElementById('l1by1').style.display = 'none';}
</script>
<script type="text/javascript">
	//Behavior of 1by1 button
	$(document).ready(function() {
		$('#option2').click(function(event) {
			var fileName = $('#file').val();
			var userId = $('#userId').val();
			var currencyFrom = $('#currencyFrom').val();
			var currencyTo = $('#currencyTo').val();
			var amountSell = $('#amountSell').val();
			var amountBuy = $('#amountBuy').val();
			var rate = $('#rate').val();
			var timePlaced = $('#timePlaced').val();
			var originatingCountry = $('#originatingCountry').val();
			$.post('ActionServlet', {
				fileName : fileName,
				option : "nonmasive",
				userId : userId,
				currencyFrom : currencyFrom,
				currencyTo : currencyTo,
				amountSell : amountSell,
				amountBuy : amountBuy,
				rate : rate,
				timePlaced : timePlaced,
				originatingCountry : originatingCountry
			}, function(responseText) {
				$('#processed').html(responseText);
			});
		});
	});
	</script>
	<script type="text/javascript">
	//Behavior of Load & Process button
    function performAjaxSubmit() {

       
        var sampleFile = document.getElementById("sampleFile").files[0];

        var formdata = new FormData();        

        formdata.append("sampleFile", sampleFile);

        var xhr = new XMLHttpRequest();       

        xhr.open("POST","ActionServletUpload", true);

        xhr.send(formdata);

        xhr.onload = function(e) {

            if (this.status == 200) {

            	 document.getElementById("processed").innerHTML = this.responseText;

            }

        };                    

    }   

</script>
</head>
<body>
<%! List<String> currencies = Arrays.asList("EUR", "GBP", "USD", "BSF");
	List<String> countries = Arrays.asList("SP", "FR", "GB", "US", "AU", "VE", "CH", "JP");
%>
<h3>Market Trade Processor</h3>

<br>
<br>
<div id = "load">
	Load messages:
	<br>
	<!-- Massive Load form -->
	<form id = "f1" name = "f1" method = "post">
		   
    <label for="sampleFile">Please select a file</label>

    <input id="sampleFile" name="sampleFile" type="file" /> <br/>

    <input id="uploadBtn" type="button" value="Load & Process" onClick="performAjaxSubmit();"></input>

	</form>
	<form>
	<!-- 1By1 form -->
	<input type = "button" name = "load1by1" id = "option" value = "You can also load message by message" onClick = "show();" />
	<div id = "l1by1" style = "display:none">
	
		<hr>
		User id: <input type = "text" id = "userId"  name = "userId" size = "10" /><br>
		Currency From: 	<select name = "currencyFrom" id = "currencyFrom">
						<%	for(String cur : currencies) 
							{ %>
								<option value = "<%=cur%>"><%=cur%></option>
							<%
							}
						%>
						</select> <br>
		Currency To: 	<select name = "currencyTo" id = "currencyTo">
						<%	for(String cur : currencies) 
							{ %>
								<option value = "<%=cur%>"><%=cur%></option>
							<%
							}
						%>
						</select> <br>
		Amount Sell: <input type = "text" id = "amountSell" name = "amountSell" size = "15"/><br>
		Amount By: <input type = "text" id = "amountBuy" name = "amountBuy" size = "15"/><br>
		Rate: <input type = "text" id = "rate" name = "rate" size = "10"/><br> 
		Time placed: <input type = "text" id = "timePlaced" name = "timePlaced" size = "15"/><br>
		Originating Country: <select name = "originatingCountry" id = "originatingCountry">
						<%	for(String country : countries) 
							{ %>
								<option value = "<%=country%>"><%=country%></option>
							<%
							}
						%>
						</select> <br>
		<input type = "button" id = "option2" name = "load1by1" value = "Load 1 by 1"/><br><br>
		<div align = "right">
			<input type = "button" name = "close" value = "Hide" onClick = "hide();"/>
		</div>
		
	</div>
	</form>
</div>
<hr>
<!-- Results's div -->
<div id = "processed">

</div>


</body>
</html>