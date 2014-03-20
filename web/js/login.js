$(document).ready(function() {
    $('input[name="login_type"]').change(function(){
        var login_type = $(this).val();
        if (login_type === "patient") {
            $("#login_id").attr("placeholder", "Health Card");
        }
        else if (login_type === "employee") {
            $("#login_id").attr("placeholder", "Employee ID");
        }
    });
});
