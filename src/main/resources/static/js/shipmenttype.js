 $(document).ready(function(){
            // 1. Hidding the Error secction
            $("#shipmentModeError").hide();
            $("#shipmentCodeError").hide();
            $("#enableShipmentError").hide();
            $("#shipmentGradeError").hide();
            $("#descriptionError").hide();

            

            // 2. Declearing error flag
            var shipmentModeError = false;
            var shipmentCodeError = false;
            var enableShipmentError = false;
            var shipmentGradeError = false;
            var descriptionError = false;

            // 3. Linking the input with event
            $("#shipmentMode").change(function(){
                validate_shipmentMode();
            });
            $("#shipmentCode").keyup(function(){
            	$("#shipmentCode").val($("#shipmentCode").val().toUpperCase());
                validate_shipmentCode();
            });
            $("#enableShipment").change(function(){
                validate_enableShipment();
            });
            $('input[type="radio"][name="shipmentGrade"]').change(function(){
                validate_shipmentGrade();
            });
            $("#description").keyup(function(){
                validate_description();
            });

            // 4. Creating Validate Function 

            function validate_shipmentMode(){
                var shipmentMode = $("#shipmentMode").val();
                if(shipmentMode == ''){
                    $("#shipmentMode").addClass("errorInput");
                    $("#shipmentModeError").html("Please Select Shipment Mode");
                    $("#shipmentModeError").addClass("errorMsg");
                    $("#shipmentModeError").show();
                    shipmentModeError = false;
                }else{
                    $("#shipmentMode").removeClass("errorInput");
                    $("#shipmentModeError").hide();
                    shipmentModeError = true;
                }
                return shipmentModeError;
            }

            function validate_shipmentCode(){
                var shipmentCode = $("#shipmentCode").val();
                var pattern = /^[A-Z0-9]{4,16}$/;
                if(shipmentCode == ''){
                    $("#shipmentCode").addClass("errorInput");
                    $("#shipmentCodeError").html("Please Enter the Shipment Code");
                    $("#shipmentCodeError").addClass("errorMsg");
                    $("#shipmentCodeError").show();
                    shipmentCodeError = false;
                }else if(!pattern.test(shipmentCode)){
                    $("#shipmentCode").addClass("errorInput");
                    $("#shipmentCodeError").html("Code should be 4-16 chars");
                    $("#shipmentCodeError").addClass("errorMsg");
                    $("#shipmentCodeError").show();
                    shipmentCodeError = false;
                }else{
                	//AJAX Call
                	$.ajax({
                		url : 'validatecode',
                		data:{"shipmentCode":shipmentCode},
                		success:function(response){
                			if(response != ""){
                				 $("#shipmentCode").addClass("errorInput");
                                 $("#shipmentCodeError").html(response);
                                 $("#shipmentCodeError").addClass("errorMsg");
                                 $("#shipmentCodeError").show();
                                 shipmentCodeError = false;
                			}else{
                				 $("#shipmentCode").removeClass("errorInput");
                                 $("#shipmentCodeError").hide();
                                 shipmentCodeError = true;
                			}
                		}
                		
                	});
                	//End AJAX Call
                   
                }
                return shipmentCodeError;
            }
            
            function validate_enableShipment(){
                var enableShipment = $("#enableShipment").val();
                if(enableShipment == ''){
                    $("#enableShipment").addClass("errorInput");
                    $("#enableShipmentError").html("Please Select Enable Shipment");
                    $("#enableShipmentError").addClass("errorMsg");
                    $("#enableShipmentError").show();
                    enableShipmentError = false;
                }else{
                    $("#enableShipment").removeClass("errorInput");
                    $("#enableShipmentError").hide();
                    enableShipmentError = true;
                }
                return enableShipmentError;
            }
            
            function validate_shipmentGrade(){
                var shipmentGrade = $('input[type="radio"][name="shipmentGrade"]:checked').length;
                if(shipmentGrade == 0){
                    $("#shipmentGradeError").html("Please Choose the Shipment Grade");
                    $("#shipmentGradeError").addClass("errorMsg");
                    $("#shipmentGradeError").show();
                    shipmentGradeError = false;
                }else{
                    $("#shipmentGradeError").hide();
                    shipmentGradeError = true;
                }
                return shipmentGradeError;
            }
            
            function validate_description(){
                var description = $("#description").val();
                if(description == ''){
                    $("#description").addClass("errorInput");
                    $("#descriptionError").html("Please Enter Description About Shipment");
                    $("#descriptionError").addClass("errorMsg");
                    $("#descriptionError").show();
                    descriptionError = false;
                }else if(description.length < 10 || description.length >150){
                	$("#description").addClass("errorInput");
                    $("#descriptionError").html("Description Should be 10 to 150 chars");
                    $("#descriptionError").addClass("errorMsg");
                    $("#descriptionError").show();
                    descriptionError = false;
                }else{
                    $("#description").removeClass("errorInput");
                    $("#descriptionError").hide();
                    descriptionError = true;
                }
                return descriptionError;
            }

            // On submit
            $("#shipmentTypeForm").submit(function(){
                validate_shipmentMode();
                validate_shipmentCode();
                validate_enableShipment();
                validate_shipmentGrade();
                validate_description()

                if(shipmentModeError && shipmentCodeError && enableShipmentError && shipmentGradeError && descriptionError){
                    return true;
                }else{
                    return false;
                }
            });

        });