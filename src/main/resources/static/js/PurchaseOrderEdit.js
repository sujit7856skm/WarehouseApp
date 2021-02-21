 $(document).ready(function(){

            // AutoFill
            $("#status").val("OPEN");


            //Hide the Error Section
            $("#orderCodeError").hide();
            $("#shipmentCodeError").hide();
            $("#vendorError").hide();
            $("#refNumError").hide();
            $("#qltyCheckError").hide();
            $("#descriptionError").hide();

            //Declare the Eroor Flag
            var orderCodeError = false;
            var shipmentCodeError = false;
            var vendorError = false;
            var refNumError = false;
            var qltyCheckError = false;
            var descriptionError = false;

            //Link the inputs with the Event
            $("#orderCode").keyup(function(){
                $("#orderCode").val($("#orderCode").val().toUpperCase());
                validate_orderCode();
            });
            $("#shipmentCode").change(function(){
                validate_shipmentCode();
            });
            $("#vendor").change(function(){
            	validate_vendor();
            });
            $("#refNum").keyup(function(){
            	$("#refNum").val($("#refNum").val().toUpperCase());
                validate_refNum();
            });
            $('input[type="radio"][name="qualityCheck"]').change(function(){
                validate_qltyCheck();
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
            function validate_vendor(){
            	var vendor = $("#vendor").val();
            	if(vendor == ''){
            		$("#vendor").addClass("errorInput");
            		$("#vendorError").addClass("errorMsg");
            		$("#vendorError").html("Select Vendor");
            		$("#vendorError").show();
            		vendorError = false;
            	}else{
            		$("#vendor").removeClass("errorInput");
            		$("#vendorError").hide();
            		vendorError = true;
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
            function validate_qltyCheck(){
                var qltyCheck = $('input[type="radio"][name="qualityCheck"]:checked').length;
                if(qltyCheck < 1){
                    $("#qltyCheckError").addClass("errorMsg");
                    $("#qltyCheckError").html("Choose any one");
                    $("#qltyCheckError").show();
                    qltyCheckError = false;
                }else{
                    $("#qltyCheckError").hide();
                    qltyCheckError = true;
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
            
            $("#purchaseOrderForm").submit(function(){
                validate_orderCode();
                validate_shipmentCode();
                validate_vendor();
                validate_refNum();
                validate_qltyCheck();
                validate_description()
                if(orderCodeError && shipmentCodeError && refNumError &&  qltyCheckError && descriptionError && vendorError){
                    return true
                }else{
                    return false;
                }
            });
            
        });