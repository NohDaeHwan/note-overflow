function followAdd(userId) {

    $.ajax({
        type: "POST",
        url: "/user-follow/add/"+userId,
        contentType: "application/json; charset=utf-8",
    }).done(function(response) {
        if (response == -1) {
            location.href="/login";
        } else {
            $("#user-follow").html("<span class='mr-1'>"+response+"명</span>" +
                "<button class='btn btn-outline-danger btn-sm' th:onclick='followDelete("+userId+")'>팔로우 취소</button>");
        }
    }).fail(function(error) {
        alert(JSON.stringify(error));
    });

}

function followDelete(userId) {

    $.ajax({
        type: "POST",
        url: "/user-follow/delete/"+userId,
        contentType: "application/json; charset=utf-8",
    }).done(function(response) {
        if (response == -1) {
            location.href="/login";
        } else {
            $("#user-follow").html("<span class='mr-1'>"+response+"명</span>" +
                "<button class='btn btn-outline-primary btn-sm' th:onclick='followAdd("+userId+")'>팔로우</button>");
        }
    }).fail(function(error) {
        alert(JSON.stringify(error));
    });

}