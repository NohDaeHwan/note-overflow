function likeAdd(noteId) {

    $.ajax({
        type: "POST",
        url: "/notes/like-note/add/"+noteId,
        contentType: "application/json; charset=utf-8",
    }).done(function(response) {
        console.log(response);
        if (response == -1) {
            location.href="/login";
        } else {
            $("#note-liked").html("<button class='btn-outline-danger btn-sm mr-3' th:onclick='likeDelete("+noteId+")'>좋아요 취소</button>" +
                "<span> "+response.sharedDto.likeCount+"개</span>");
        }
    }).fail(function(error) {
        alert(JSON.stringify(error));
    });

}

function likeDelete(noteId) {

    $.ajax({
        type: "POST",
        url: "/notes/like-note/delete/"+noteId,
        contentType: "application/json; charset=utf-8",
    }).done(function(response) {
        console.log(response);
        if (response == -1) {
            location.href="/login";
        } else {
            $("#note-liked").html("<button class='btn-outline-primary btn-sm mr-3' th:onclick='likeAdd("+noteId+")'>좋아요</button>" +
                "<span> "+response+"개</span>");
        }
    }).fail(function(error) {
        alert(JSON.stringify(error));
    });

}
