$(document).ready(function () {
			// hide the error section
			$("#userTypeError").hide();
			$("#userCodeError").hide();
			$("#userEmailError").hide();
			$("#userContactError").hide();
			$("#userIdTypeError").hide();
			$("#ifOtherError").hide();
			$("#idNumberError").hide();

			// Decleare Error flag
			var userTypeError = false;
			var userCodeError = false;
			var userEmailError = false;
			var userContactError = false;
			var userIdTypeError = false;
			var ifOtherError = true;
			var idNumberError = false;

			//link input with event
			$("#userCode").keyup(function () {
				$("#userCode").val($("#userCode").val().toUpperCase());
				validate_userCode();
			});
			$("#userEmail").keyup(function () {
				validate_userEmail();
			});
			$("#userContact").keyup(function () {
				validate_userContact();
			});
			$("#ifOther").keyup(function () {
				validate_ifOther();
			});
			$("#idNumber").keyup(function () {
				validate_idNumber();
			});

			//validation functions


			//User Type validation
			function validate_userType() {
				var userTypeLen = $('input[type="radio"][name="userType"]:checked').length;
				if (userTypeLen == 0) {
					$("#userTypeError").html("Please Select User Type");
					$("#userTypeError").addClass("errorMsg");
					$("#userTypeError").show();
					userTypeError = false;
				} else {
					$("#userTypeError").hide();
					userTypeError = true;
				}
			}
			//User Code Validation
			function validate_userCode() {
				var userCode = $("#userCode").val();
				if (userCode == '') {
					$("#userCode").addClass("errorInput");
					$("#userCodeError").addClass("errorMsg");
					$("#userCodeError").html("Please Enter User Code");
					$("#userCodeError").show();
					userCodeError = false;
				} else if (userCode.length < 4 || userCode.length > 16) {
					$("#userCode").addClass("errorInput");
					$("#userCodeError").addClass("errorMsg");
					$("#userCodeError").html("User Code should be 4 to 16 chars");
					$("#userCodeError").show();
					userCodeError = false;
				} else {
					$("#userCode").removeClass("errorInput");
					$("#userCodeError").hide();
					userCodeError = true;
				}

			}
			// Email Validation
			function validate_userEmail() {
				var userEmail = $("#userEmail").val();
				var userEmailPattern = /^[A-Za-z0-9._\-]+@[A-Za-z.]+\.[A-Za-z]{2,4}$/;
				if (userEmail == '') {
					$("#userEmail").addClass("errorInput");
					$("#userEmailError").html("Please Enter the Email");
					$("#userEmailError").addClass("errorMsg");
					$("#userEmailError").show();
					userEmailError = false;
				}else if(!userEmailPattern.test(userEmail)){
					$("#userEmail").addClass("errorInput");
					$("#userEmailError").html("Please Enter Valid Email");
					$("#userEmailError").addClass("errorMsg");
					$("#userEmailError").show();
					userEmailError = false;
				}else {
					var id = $("#id").val();
					$.ajax({
						url : '../validateemailforedit',
						data : {"userEmail" : userEmail,"id" : id},
						success : function(message){
							if(message != ""){
								$("#userEmail").addClass("errorInput");
								$("#userEmailError").html(message);
								$("#userEmailError").addClass("errorMsg");
								$("#userEmailError").show();
								userEmailError = false;
							}else{
								$("#userEmail").removeClass("errorInput");
								$("#userEmailError").hide();
								userEmailError = true;;
							}
						}
					});
				}
			}

			//User Contact Validation
			function validate_userContact() {
				var userContact = $("#userContact").val();
				var userContactExp = /^[0-9]{10,12}$/;
				if (userContact == '') {
					$("#userContact").addClass("errorInput");
					$("#userContactError").html("Enter Contact Details");
					$("#userContactError").addClass("errorMsg");
					$("#userContactError").show();
					userContactError = false;
				} else {
					$("#userContact").removeClass("errorInput");
					$("#userContactError").hide();
					userContactError = true;
				}
			}

			// User Id Type Validatio
			function validate_userIdType() {
				var userIdType = $("#userIdType").val();
				if (userIdType == '') {
					$("#userIdType").addClass("errorInput");
					$("#userIdTypeError").html("Please Select User Id Type");
					$("#userIdTypeError").addClass("errorMsg");
					$("#userIdTypeError").show();
					userIdTypeError = false;
				} else {
					$("#userIdType").removeClass("errorInput");
					$("#userIdTypeError").hide();
					userIdTypeError = true;
				}
			}

			//validate if other id
			function validate_ifOther() {
				var userIdType = $("#userIdType").val();
				if (userIdType == 'OTHER') {
					var ifOther = $("#ifOther").val();
					if (ifOther == '') {
						$("#ifOther").addClass("errorInput");
						$("#ifOtherError").html("Please Enter The Othe Id Type");
						$("#ifOtherError").addClass("errorMsg");
						$("#ifOtherError").show();
						ifOtherError = false;
					} else {
						$("#ifOther").removeClass("errorInput");
						$("#ifOtherError").hide();
						ifOtherError = true;
					}
				}
			}
			//Id number Validation
			function validate_idNumber() {
				var idNumber = $("#idNumber").val();
				if (idNumber == '') {
					$("#idNumber").addClass("errorInput");
					$("#idNumberError").html("Please Enter the Id Number");
					$("#idNumberError").addClass("errorMsg");
					$("#idNumberError").show();
					idNumberError = false;
				} else {
					$("#idNumber").removeClass("errorInput");
					$("#idNumberError").hide();
					idNumberError = true;
				}
			}
			// Extra validation

			//Auto Fill User For
			$('input[type="radio"][name="userType"]').change(function () {
				autoFillUserFor();
				validate_userType();
			});
			function autoFillUserFor() {
				var userType = $('input[type="radio"][name="userType"]:checked').val();
				if (userType == "Sale") {
					$("#userFor").val("Purchase")
				} else if (userType == "Purchase") {
					$("#userFor").val("Sale")
				}
			}

			// show text Field if id type selected other
			$("#otherId").hide();
			showHideOtherIdField();
			$("#userIdType").change(function () {
				showHideOtherIdField();
				validate_userIdType();
			});
			function showHideOtherIdField() {
				var userIdType = $("#userIdType").val();
				if (userIdType == "OTHER") {
					$("#otherId").show();
				} else {
					$("#otherId").hide();
				}
			}


			// On submit validation
			$("#whUserTypeReg").submit(function () {
				validate_userType();
				validate_userCode();
				validate_userEmail();
				validate_userContact();
				validate_userIdType();
				validate_ifOther();
				validate_idNumber();
				if (userTypeError && userCodeError && userEmailError && userContactError 
					&& userIdTypeError && ifOtherError && idNumberError) {
					return true;
				} else {
					return false;
				}
			});
		});