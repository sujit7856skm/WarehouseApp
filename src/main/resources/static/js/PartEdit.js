$(document).ready(function(){
	//Hide Error Section
	$("#partDimensionError").hide();
	$("#baseCostError").hide();
	$("#baseCurrencyError ").hide();
	$("#uomError").hide();
	$("#descriptionError").hide();
    
    
    //Error Flag Decleration
	var partWidthError = false;
	var partLengthError = false;
	var partHeightError = false;
	var baseCostError = false;
	var baseCurrencyError  = false;
	var uomError  = false;
	var descriptionError  = false;
	
	
    //linking the inputs with events
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
	function validate_partWidth(){
		var partWidth = $("#partWidth").val();
		if(partWidth == ""){
			$("#partWidth").addClass("errorInput");
			$("#partDimensionError").addClass("errorMsg");
			$("#partDimensionError").html("Enter the Part Dimension");
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
		if(partLength == ""){
			$("#partLength").addClass("errorInput");
			$("#partDimensionError").addClass("errorMsg");
			$("#partDimensionError").html("Enter the Part Dimension");
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
		if(partHeight == ""){
			$("#partHeight").addClass("errorInput");
			$("#partDimensionError").addClass("errorMsg");
			$("#partDimensionError").html("Enter the Part Dimension");
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
		if(baseCost == ""){
			$("#baseCost").addClass("errorInput");
			$("#baseCostError").addClass("errorMsg");
			$("#baseCostError").html("Enter the base cost");
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
			$("#baseCurrencyError").html("Select te Base Currency");
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
 });