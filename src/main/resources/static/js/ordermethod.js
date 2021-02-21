  $(document).ready(function(){
			// hiding the error section
			$("#orderModeError").hide();
			$("#orderCodeError").hide();
			$("#orderTypeError").hide();
			$("#orderAcceptError").hide();
			$("#descriptionError").hide();

			// Decleare the error flag
			var orderModeError = false;
			var orderCodeError = false;
			var orderTypeError = false;
			var orderAcceptError = false;
			var descriptionError = false;

			// linking the input with event
			$('input[type="radio"][name="orderMode"]').change(function(){
				validate_orderMode();
			});
			$("#orderCode").keyup(function(){
				$("#orderCode").val($("#orderCode").val().toUpperCase());
				validate_orderCode();
			});
			$("#orderType").change(function(){
				validate_orderType();
			});
			$('input[type="checkbox"][name="orderAccept"]').change(function(){
				validate_orderAccept();
			});
			$("#description").keyup(function(){
				validate_description();
			});
			//creating the validate function
			function validate_orderMode(){
				var orderMode = $('input[type="radio"][name="orderMode"]:checked').length;
				if(orderMode == 0){
					$("#orderModeError").html("Please select the Order Mode");
					$("#orderModeError").addClass("errorMsg");
					$("#orderModeError").show();
					orderModeError = false;
				}else{
					$("#orderModeError").hide();
					orderModeError = true;
				}
			}
			function validate_orderCode(){
				var orderCode = $("#orderCode").val();
				var pattern = /^[A-Z0-9]{4,16}$/;
				if(orderCode == ''){
					$("#orderCode").addClass("errorInput");
					$("#orderCodeError").html("Please Enter the Order Code");
					$("#orderCodeError").addClass("errorMsg");
					$("#orderCodeError").show();
					orderCodeError = false;
				}else if(!pattern.test(orderCode)){
					$("#orderCode").addClass("errorInput");
					$("#orderCodeError").html("Order Code should 4 to 16 chars");
					$("#orderCodeError").addClass("errorMsg");
					$("#orderCodeError").show();
					orderCodeError = false;
				}else{
					
					$.ajax({
						url : 'validatecode',
						data : {"orderCode" : orderCode},
						success : function(message){
							if(message != ""){
								$("#orderCode").addClass("errorInput");
								$("#orderCodeError").html(message);
								$("#orderCodeError").addClass("errorMsg");
								$("#orderCodeError").show();
								orderCodeError = false;
							}else{
								$("#orderCode").removeClass("errorInput");
								$("#orderCodeError").hide();
								orderCodeError = true;
							}
						}
					});
					
					
				}
			}
			function validate_orderType(){
				var orderType = $("#orderType").val();
				if(orderType == ''){
					$("#orderType").addClass("errorInput");
					$("#orderTypeError").html("Please select the Order Type");
					$("#orderTypeError").addClass("errorMsg");
					$("#orderTypeError").show();
					orderTypeError = false;
				}else{
					$("#orderType").removeClass("errorInput");
					$("#orderTypeError").hide();
					orderTypeError = true;
				}
			}
			function validate_orderAccept(){
				var orderAccept = $('input[type="checkbox"][name="orderAccept"]:checked').length;
				if(orderAccept == 0){
					$("#orderAccept").addClass("errorInput");
					$("#orderAcceptError").html("Please select Order Accept");
					$("#orderAcceptError").addClass("errorMsg");
					$("#orderAcceptError").show();
					orderAcceptError = false;
				}else{
					$("#orderAccept").removeClass("errorInput");
					$("#orderAcceptError").hide();
					orderAcceptError = true;
				}
			}
			function validate_description(){
				var description = $("#description").val();
				if(description == ''){
					$("#description").addClass("errorInput");
					$("#descriptionError").html("Please Enter the Order Code");
					$("#descriptionError").addClass("errorMsg");
					$("#descriptionError").show();
					descriptionError = false;
				}else if(description.length < 10 || description.length > 150){
					$("#description").addClass("errorInput");
					$("#descriptionError").html("Description shoulod be 10 to 150 chars");
					$("#descriptionError").addClass("errorMsg");
					$("#descriptionError").show();
					descriptionError = false;
				}else{
					$("#description").removeClass("errorInput");
					$("#descriptionError").hide();
					descriptionError = true;
				}
			}

			// on form submit 
			$("#orderMethodForm").submit(function(){
				validate_orderMode();
				validate_orderCode();
				validate_orderType();
				validate_orderAccept();
				validate_description();
				if(orderModeError && orderCodeError && orderTypeError && orderAcceptError && descriptionError){
					return true;
				}else{
					return false;
				}
			});
        });
  