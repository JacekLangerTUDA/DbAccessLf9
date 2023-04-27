function sha512(str) {
    return crypto.subtle.digest("SHA-512", new TextEncoder("utf-8").encode(str)).then(buf => {
        return Array.prototype.map.call(new Uint8Array(buf), x => (('00' + x.toString(16)).slice(-2))).join('');
    });
}

function setCookie(cname, cvalue, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function sendLoginRequest() {
    try {
        let username = $('#user-tb').val()
        sha512($("input:password").val()).then(hash => {
            $.ajax({
                type: "POST",
                url: "/",
                data: `{"username":"${username}","password":"${hash}"}`,
                statusCode: {
                    200: (result) => {
                        setCookie("loginSuccessful", true, 1)
                        location.replace("/home")
                    },
                    401: (result) => {
                        $('#error-msg').text("Access denied! Invalid password or username.")
                    },
                },
                dataType: "json"
            });
        })
    } catch (e) {
        console.log(e)
    }
}

let emailMatch = true;

function verifyEmail() {
    const mail2 = $('#tb-mail2')
    if ($('#tb-mail1').val() !== mail2.val()) {
        emailMatch = false
        mail2.css("outline:red;")
    } else {
        mail2.css("outline:none;")
    }
}

let passwordMatch = true

function verifyPassword() {
    const mail2 = $('#inp-password2')
    if ($('#inp-password1').val() !== mail2.val()) {
        emailMatch = false
        mail2.css("outline:red;")
    } else {
        mail2.css("outline:none;")
    }
}

function verifyUsername() {
    return true
}

function verifyEmailNotTaken() {
    return true;
}

function sendRegisterRequest() {


    let userNameValid = verifyUsername()


    let emailNotTaken = verifyEmailNotTaken()

    try {
        const username = $('#tb-username').val()

        if (passwordMatch && emailMatch && userNameValid && emailNotTaken) {

            const email = $('#tb-email1').val()
            sha512($('#inp-password1').val()).then(hash => {

                $.ajax({
                    type: "POST",
                    url: "/signup",
                    data: `{"username":"${username}",email:"${email}","password":"${hash}"}`,
                    // data:{username:username,email:email,password:hash},
                    statusCode:{
                        200:(result)=>{
                            console.log(result)
                            userNameValid = true
                            location.replace("/home")
                        },
                    },
                    dataType: "json"
                })
            })

        } else {
            if (!userNameValid)
                $('#error-msg').text("The username is already in use")
        }
    } catch (e) {
        alert("External resources could not be fetched. please try again later.")
    }
}

$(document).ajaxSuccess(function (event, request, settings) {
    if (request.getResponseHeader('REQUIRES_AUTH') === '1') {
        window.location = '/';
    }
});

