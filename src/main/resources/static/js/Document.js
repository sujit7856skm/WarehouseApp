$(document).ready(function(){
            $("#docIdError").hide();
            $("#docError").hide();

            var docIdError = false;
            var docError = false;

            $("#docId").keyup(function(){
                validate_docId();
            });
            $("#doc").change(function(){
                validate_doc(this.files[0]);
            });

            function validate_docId(){
                var docId = $("#docId").val();
                var exp = /^[1-9][0-9]{1,6}$/;
                if(docId == ''){
                    $("#docId").addClass("errorInput");
                    $("#docIdError").addClass("errorMsg");
                    $("#docIdError").html("Enter the Document Id");
                    $("#docIdError").show();
                    docIdError = false;
                }else if(!exp.test(docId)){
                    $("#docId").addClass("errorInput");
                    $("#docIdError").addClass("errorMsg");
                    $("#docIdError").html("Enter the Valid Document Id");
                    $("#docIdError").show();
                    docIdError = false;
                }else{
                	$("#docId").removeClass("errorInput");
                    $("#docIdError").hide();
                    docIdError = true;
                }
            }
            function validate_doc(file){
                var fileName = file.name;
                var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
                var allowedExt = ["jpeg", "jpg", "png", "doc", "docx", "pdf", "zip"]; 
                if($.inArray(fileExt, allowedExt) == -1){
                    $("#doc").addClass("errorInput");
                    $("#docError").addClass("errorMsg");
                    $("#docError").html("Choose valid File [jpeg, jpg, png, doc, pdf, war]");
                    $("#docError").show();
                    docError = false;
                }else if(file.size < 1024 * 150 || file.size > 1024 * 1024 * 2){
                    $("#doc").addClass("errorInput");
                    $("#docError").addClass("errorMsg");
                    $("#docError").html("File size should be 150KB to 2MB");
                    $("#docError").show();
                    docError = false;

                }else{
                    $("#doc").removeClass("errorInput");
                    $("#docError").hide();
                    docError = true;
                }
            }
            function fileRequired(fileUrl){
                if(fileUrl == "" || fileUrl == null){
                    $("#doc").addClass("errorInput");
                    $("#docError").addClass("errorMsg");
                    $("#docError").html("Please Choose Document [jpeg, jpg, png, doc, pdf, war]");
                    $("#docError").show();
                    docError = false;
                }
            }
         // On from submit
            $("#documentForm").submit(function(){
                validate_docId();
                fileRequired($("#doc").val());
                if(docIdError && docError){
                    return true;
                }else{
                    return false;
                }
            });
            
        });