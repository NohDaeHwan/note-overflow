let index = {
    init: function() {
        $('#btn-join').on('click', () => {
            this.join();
        });
    },

    join: function() {
        let data = {
            userEmail: $("#userEmail").val(),
            userPassword: $("#userPassword").val(),
            userName: $("#userName").val(),
            userPhone: $("#userPhone").val()
        }

        $.ajax({
            type: "POST",
            url: "/join_request",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp) {
            if (resp == 1) {
                alert("회원가입이 완료되었습니다.");
                location.href="/login";
            } else {
                alert("이미 존재하는 이메일 입니다.");
            }
        }).fail(function(error) {
            alert("회원가입 실패");
        });
    }
}

index.init();
