$(document).ready(function(){
            //Hide Error Section
            $("#partCodeError").hide();
            $("#partDimensionError").hide();
            $("#baseCostError").hide();
            $("#baseCurrencyError").hide();
            $("#uomError").hide();
            $("#descriptionError").hide();

            //Error Flag Decleration
            var partCodeError = false;
            var partWidthError = false;
            var partLengthError = false;
            var partHeightError = false;
            var baseCostError = false;
            var baseCurrencyError = false;
            var uomError = false;
            var descriptionError = false;

            //linking the inputs with events
            $("#partCode").keyup(function(){
                $("#partCode").val($("#partCode").val().toUpperCase());
                validate_partCode();
            });
            $("#partWidth").keyup(function(){
                validate_partWidth();
            });
            $("#partLength").keyup(function(){
                validate_partLength();
            });
            $("#partHeight").keyup(function(){
                validate_partHeight();
            });
            $("#baseCost").keyup(function(){
                validate_baseCost();
            });
            $("#baseCurrency").change(function(){
                validate_baseCurrency();
            });
            $("#uom").change(function(){
            	validate_uom();
            });
            $("#description").keyup(function(){
                validate_description();
            });

            //vaidation method
            function validate_partCode(){
                var partCode = $("#partCode").val();
				var pattern = /^[A-Z]+[A-Z0-9]{3,7}$/;
                if(partCode == ""){
                    $("#partCode").addClass("errorInput");
                    $("#partCodeError").addClass("errorMsg");
                    $("#partCodeError").html("Enter the Part Code");
                    $("#partCodeError").show();
                    partCodeError = false;
                }else if(!pattern.test(partCode)){
                    $("#partCode").addClass("errorInput");
                    $("#partCodeError").addClass("errorMsg");
                    $("#partCodeError").html("Code Should be 4-8 chars and start with Alphbet");
                    $("#partCodeError").show();
                    partCodeError = false;

                }else{
                	$.ajax({
                		url : 'validatecode',
                		data : {"partCode" : partCode},
                		success : function(message){
                			if(message != ""){
                				$("#partCode").addClass("errorInput");
                				$("#partCodeError").addClass("errorMsg");
                				$("#partCodeError").html(message);
                				$("#partCodeError").show();
                				partCodeError = false;                			
                			}else{
                				$("#partCode").removeClass("errorInput");
                				$("#partCodeError").hide();                			
                				partCodeError = true;                			
                			}
                		}
                	});

                }
			}
			function validate_partWidth(){
				var partWidth = $("#partWidth").val();
				var pattern = /^[0-9]{1,5}$/;
				if(partWidth == ""){
					$("#partWidth").addClass("errorInput");
					$("#partDimensionError").addClass("errorMsg");
					$("#partDimensionError").html("Enter the Part Dimension");
					$("#partDimensionError").show();
					partWidthError = false;
				}else if(! pattern.test(partWidth)){
					$("#partWidth").addClass("errorInput");
					$("#partDimensionError").addClass("errorMsg");
					$("#partDimensionError").html("Enter valid Width");
					$("#partDimensionError").show();
					partWidthError = false;
				}else{
					$("#partWidth").removeClass("errorInput")
					$("#partDimensionError").hide();
					partWidthError = true;

				}
			}
			function validate_partLength(){
				var partLength = $("#partLength").val();
				var pattern = /^[0-9]{1,5}$/;
				if(partLength == ""){
					$("#partLength").addClass("errorInput");
					$("#partDimensionError").addClass("errorMsg");
					$("#partDimensionError").html("Enter the Part Dimension");
					$("#partDimensionError").show();
					partLengthError = false;
				}else if(! pattern.test(partLength)){
					$("#partLength").addClass("errorInput");
					$("#partDimensionError").addClass("errorMsg");
					$("#partDimensionError").html("Enter valid Length");
					$("#partDimensionError").show();
					partLengthError = false;
				}else{
					$("#partLength").removeClass("errorInput")
					$("#partDimensionError").hide();
					partLengthError = true;

				}
			}
			function validate_partHeight(){
				var partHeight = $("#partHeight").val();
				var pattern = /^[0-9]{1,5}$/;
				if(partHeight == ""){
					$("#partHeight").addClass("errorInput");
					$("#partDimensionError").addClass("errorMsg");
					$("#partDimensionError").html("Enter the Part Dimension");
					$("#partDimensionError").show();
					partHeightError = false;
				}else if(! pattern.test(partHeight)){
					$("#partHeight").addClass("errorInput");
					$("#partDimensionError").addClass("errorMsg");
					$("#partDimensionError").html("Enter valid Height");
					$("#partDimensionError").show();
					partHeightError = false;
				}else{
					$("#partHeight").removeClass("errorInput")
					$("#partDimensionError").hide();
					partHeightError = true;

				}
			}

			function validate_baseCost(){
				var baseCost = $("#baseCost").val();
				var pattern = /^[0-9]{1,10}$/;
				if(baseCost == ""){
					$("#baseCost").addClass("errorInput");
					$("#baseCostError").addClass("errorMsg");
					$("#baseCostError").html("Enter the base cost");
					$("#baseCostError").show();
					baseCostError = false;
				}else if(!pattern.test(baseCost)){
					$("#baseCost").addClass("errorInput");
					$("#baseCostError").addClass("errorMsg");
					$("#baseCostError").html("Enter the Valid Cost");
					$("#baseCostError").show();
					baseCostError = false;
				}else{
					$("#baseCost").removeClass("errorInput");
					$("#baseCostError").hide();
					baseCostError = true;
				}
			}
			function validate_baseCurrency(){
				var baseCurrency = $("#baseCurrency").val();
				if(baseCurrency == ""){
					$("#baseCurrency").addClass("errorInput");
					$("#baseCurrencyError").addClass("errorMsg");
					$("#baseCurrencyError").html("Select the Base Currency");
					$("#baseCurrencyError").show();
					baseCurrencyError = false;
				}else{
					$("#baseCurrency").removeClass("errorInput");
					$("#baseCurrencyError").hide();
					baseCurrencyError = true;
				}
			}
			function validate_uom(){
				var uom = $("#uom").val();
				if(uom == ""){
					$("#uom").addClass("errorInput");
					$("#uomError").addClass("errorMsg");
					$("#uomError").html("Select UOM");
					$("#uomError").show();
					uomError = false;
				}else{
					$("#uom").removeClass("errorInput");
					$("#uomError").hide();
					uomError = true;
				}
			}
			function validate_description(){
                var description = $("#description").val();
                if(description == ''){
                    $("#description").addClass("errorInput");
                    $("#descriptionError").html("Enter the Uom Descriptions");
                    $("#descriptionError").addClass("errorMsg");
                    $("#descriptionError").show();
                    descriptionError = false;
                }else if(description.length < 10 || description.length > 150){
                    $("#description").addClass("errorInput");
                    $("#descriptionError").html("Description Should be 10 150 chars");
                    $("#descriptionError").addClass("errorMsg");
                    $("#descriptionError").show();
                    descriptionError = false;

                }else{
                    $("#description").removeClass("errorInput");
                    $("#descriptionError").hide();
                    descriptionError = true;

                }
            }

			$("#partForm").submit(function(){
				validate_partCode();
				validate_partWidth();
				validate_partLength();
				validate_partHeight();
				validate_baseCost();
				validate_baseCurrency();
				validate_uom();
				validate_description();

				if(partCodeError && partWidthError && partLengthError && partHeightError && baseCostError && baseCurrencyError && uomError && descriptionError){
					return true;
				}else{
					return false;
				}
			})

        });