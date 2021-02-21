 $(document).ready(function(){

            // AutoFill
            $("#status").val("SALE-OPEN");


            //Hide the Error Section
            $("#orderCodeError").hide();
            $("#shipmentCodeError").hide();
            $("#customerError").hide();
            $("#refNumError").hide();
            $("#stockModeError").hide();
            $("#stockSourceError").hide();
            $("#descriptionError").hide();

            //Declare the Eroor Flag
            var orderCodeError = false;
            var shipmentCodeError = false;
            var customerError = false;
            var refNumError = false;
            var stockModeError = false;
            var stockSourceError = false;
            var descriptionError = false;

            //Link the inputs with the Event
            $("#orderCode").keyup(function(){
                $("#orderCode").val($("#orderCode").val().toUpperCase());
                validate_orderCode();
            });
            $("#shipmentCode").change(function(){
                validate_shipmentCode();
            });
            $("#customer").change(function(){
            	validate_customer();
            });
            $("#refNum").keyup(function(){
            	$("#refNum").val($("#refNum").val().toUpperCase());
                validate_refNum();
            });
            $('input[type="radio"][name="stockMode"]').change(function(){
                validate_stockMode();
            });
            $("#stockSource").change(function(){
            	validate_stockSource();
            });
            $("#description").keyup(function(){
                validate_description();
            });
            
            //Validation method
            function validate_orderCode(){
                var orderCode = $("#orderCode").val();
                var pattern = /^[A-Z]+[A-Z0-9]{3,7}$/;
                if(orderCode == ''){
                    $("#orderCode").addClass("errorInput");
                    $("#orderCodeError").addClass("errorMsg");
                    $("#orderCodeError").html("Enter the OrderCode");
                    $("#orderCodeError").show();
                    orderCodeError = false;
                }else if(!pattern.test(orderCode)){
                    $("#orderCode").addClass("errorInput");
                    $("#orderCodeError").addClass("errorMsg");
                    $("#orderCodeError").html("Should be 4 to 8 chars, Must start with alphabet");
                    $("#orderCodeError").show();
                    orderCodeError = false;
                }else{
                    $("#orderCode").removeClass("errorInput");
                    $("#orderCodeError").hide();
                    orderCodeError = true;
                }
            }
            function validate_shipmentCode(){
                var shipmentCode = $("#shipmentCode").val();
                if(shipmentCode == ''){
                    $("#shipmentCode").addClass("errorInput");
                    $("#shipmentCodeError").addClass("errorMsg");
                    $("#shipmentCodeError").html("Select Shipment Type");
                    $("#shipmentCodeError").show();
                    shipmentCodeError = false;
                }else{
                    $("#shipmentCode").removeClass("errorInput");
                    $("#shipmentCodeError").hide();
                    shipmentCodeError = true;
                }
            }
            function validate_customer(){
            	var customer = $("#customer").val();
            	if(customer == ''){
            		$("#customer").addClass("errorInput");
            		$("#customerError").addClass("errorMsg");
            		$("#customerError").html("Select Customer");
            		$("#customerError").show();
            		customerError = false;
            	}else{
            		$("#customer").removeClass("errorInput");
            		$("#customerError").hide();
            		customerError = true;
            	}
            }
            function validate_refNum(){
                var refNum = $("#refNum").val();
                var pattern = /^[A-Z0-9]{6,12}$/;
                if(refNum == ''){
                    $("#refNum").addClass("errorInput");
                    $("#refNumError").addClass("errorMsg");
                    $("#refNumError").html("Enter the Reference Number");
                    $("#refNumError").show();
                    refNumError = false;
                }else if(!pattern.test(refNum)){
                    $("#refNum").addClass("errorInput");
                    $("#refNumError").addClass("errorMsg");
                    $("#refNumError").html("Should be 6 to 12 chars");
                    $("#refNumError").show();
                    refNumError = false;
                }else{
                    $("#refNum").removeClass("errorInput");
                    $("#refNumError").hide();
                    refNumError = true;
                }
            }
            function validate_stockMode(){
                var stockMode = $('input[type="radio"][name="stockMode"]:checked').length;
                if(stockMode < 1){
                    $("#stockModeError").addClass("errorMsg");
                    $("#stockModeError").html("Select Stock Mode");
                    $("#stockModeError").show();
                    stockModeError = false;
                }else{
                    $("#stockModeError").hide();
                    stockModeError = true;
                }
            }
            function validate_stockSource(){
            	var stockSource = $("#stockSource").val();
            	if(stockSource == ''){
            		$("#stockSource").addClass("errorInput");
            		$("#stockSourceError").addClass("errorMsg");
            		$("#stockSourceError").html("Select Stock Source");
            		$("#stockSourceError").show();
            		stockSourceError = false;
            	}else{
            		$("#stockSource").removeClass("errorInput");
            		$("#stockSourceError").hide();
            		stockSourceError = true;
            	}
            }
            function validate_description(){
                var description = $("#description").val();
                if(description == ''){
                    $("#description").addClass("errorInput");
                    $("#descriptionError").addClass("errorMsg");
                    $("#descriptionError").html("Enter the Description");
                    $("#descriptionError").show();
                    descriptionError = false;
                }else if(description.length < 10 || description > 150){
                    $("#description").addClass("errorInput");
                    $("#descriptionError").addClass("errorMsg");
                    $("#descriptionError").html("Description Should be 10 to 150 chars");
                    $("#descriptionError").show();
                    descriptionError = false;
                }else{
                    $("#description").removeClass("errorInput");
                    $("#descriptionError").hide();
                    descriptionError = true;
                }
            }
            
            $("#saleOrderForm").submit(function(){
                validate_orderCode();
                validate_shipmentCode();
                validate_customer();
                validate_refNum();
                validate_stockMode();
                validate_stockSource();
                validate_description()
                if(orderCodeError && shipmentCodeError && customerError && refNumError &&  stockModeError && stockSourceError && descriptionError){
                    return true
                }else{
                    return false;
                }
            });
            
        });