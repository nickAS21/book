function customReset() {
    if (document.getElementById("login") != null) {
        document.getElementById("login").value = "";
    }
    if (document.getElementById("pib") != null) {
        document.getElementById("pib").value = "";
    }
    if (document.getElementById("password") != null) {
        document.getElementById("password").value = "";
    }
    if (document.getElementById("confirmPassword") != null) {
        document.getElementById("confirmPassword").value = "";
    }
    if (document.getElementById("errorReset") != null) {
        document.getElementById("errorReset").remove();
    }
    if (document.getElementById("logoutReset") != null) {
        document.getElementById("logoutReset").outerHTML = "";
    }
    if (document.getElementById("loginReset") != null) {
        document.getElementById("loginReset").remove();
    }
    if (document.getElementById("pibReset") != null) {
        document.getElementById("pibReset").remove();
    }
    if (document.getElementById("passwordReset") != null) {
        document.getElementById("passwordReset").remove();
    }
    if (document.getElementById("confirmPasswordReset") != null) {
        document.getElementById("confirmPasswordReset").remove();
    }
}
