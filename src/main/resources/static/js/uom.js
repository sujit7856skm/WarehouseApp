$(document).ready(function(){
	
            // 1. Hiding the Error Sections
            $("#uomTypeError").hide();
            $("#uomModelError").hide();
            $("#descriptionError").hide();

            // 2. Declearing the error flage variable
            var uomTypeError = false;
            var uomModelError = false;
            var descriptionError = false;

            // 3. Linking the input with the event
            $("#uomType").change(function(){
                validate_uomType();
            });
            $("#uomModel").keyup(function(){
                $("#uomModel").val($("#uomModel").val().toUpperCase());
                validate_uomModel();
            });
            $("#description").keyup(function(){
                validate_description();
            });

            // 4. Creating the Validate funtion
            function validate_uomType(){
                var uomType = $("#uomType").val();
                if(uomType == ''){
                    $("#uomType").addClass("errorInput");
                    $("#uomTypeError").html("Please Select Uom Type");
                    $("#uomTypeError").addClass("errorMsg");
                    $("#uomTypeError").show();
                    uomTypeError = false;
                }else{
                    $("#uomType").removeClass("errorInput");
                    $("#uomTypeError").hide();
                    uomTypeError = true;

                }
            }
            function validate_uomModel(){
                var uomModel = $("#uomModel").val();
                var uomModelExp = /^[A-Z0-9]{4,16}$/;
                if(uomModel == ''){
                    $("#uomModel").addClass("errorInput");
                    $("#uomModelError").html("Enter the Uom Model");
                    $("#uomModelError").addClass("errorMsg");
                    $("#uomModelError").show();
                    uomModelError = false;
                }else if(!uomModelExp.test(uomModel)){
                    $("#uomModel").addClass("errorInput");
                    $("#uomModelError").html("Uom Model should be 4 to 16 chars");
                    $("#uomModelError").addClass("errorMsg");
                    $("#uomModelError").show();
                    uomModelError = false;

                }else{
                    $("#uomModel").removeClass("errorInput");
                    $("#uomModelError").hide();
                    uomModelError = true;

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

            // On Form Submit
            $("#uomReg").submit(function(){
                validate_uomType();
                validate_uomModel();
                validate_description()

                if(uomTypeError && uomModelError && descriptionError){
                    return true;
                }else{
                    return false;
                }
            });

        });