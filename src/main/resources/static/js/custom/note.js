let index = {
	init: function() {
		$('#btn-save').on('click', () => {
			this.save();
		});
	},
	
	save: function() {
		let data = {
			title:$("#title").val(),
			mCategory:$("#mCategory").val(),
			sCategory:$("#sCategory").val(),
			content:$("textarea#main-content").val(),
			noteTagsRequests: [{tag:$("#tag1").val()}, {tag:$("#tag2").val()}, {tag:$("#tag3").val()},
				{tag:$("#tag4").val()}, {tag:$("#tag5").val()}]
		}
		
		$.ajax({
			type: "POST",
			url: "/save_request",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			if (resp == 1) {
				alert("노트 작성이 완료되었습니다.");
				location.href="/notes";
			} else {
				alert("노트 작성 실패");
			}
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	}
	
}

function note(noteId, userId) {
	let principal = document.querySelector("#principal").value;

	$.ajax({
		type: "GET",
		url: "/mypage-note/"+noteId,
		contentType: "application/json; charset=utf-8"
	}).done(function(response) {
		$("#note-title").text("");
		$("#note-tag").text("");
		$("#note-main").text("");
		if (userId == principal) {		
			if(response.sharing == false) {
				$("#note-main").html("<button class='btn btn-warning btn-sm mr-4' onclick='shared("+noteId+","+ userId+")'>공유하기</button>"
					+ "<button class='btn btn-warning btn-sm' onclick='noteUpdate("+noteId+")'>수정하기</button><br/>");
			} else {
				$("#note-main").html("<button class='btn btn-warning btn-sm mr-4' onclick='sharedCancel("+noteId+","+ userId+")'>공유취소</button>"
				+ "<button class='btn btn-warning btn-sm' onclick='noteUpdate("+noteId+")'>수정하기</button>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "<a class='btn btn-warning btn-sm' href='/notes/detail/"+noteId+"'>자세히보기</a><br/>");
			}
		} else {
			$("#note-main").html("<a class='btn btn-warning btn-sm' href='/notes/detail/"+noteId+"'>자세히보기</a><br/>");
		}
		
		$("#note-main").append(response.content);
	}).fail(function(error) {
		alert(JSON.stringify(error));
	});
}

function shared(noteId, userId) {
	
	$.ajax({
		type: "POST",
		url: "/notes/"+noteId+"/shared",
		contentType: "application/json; charset=utf-8",
	}).done(function(resp) {
		alert("노트를 공유했습니다.");
		location.href="/users/"+userId;
	}).fail(function(error) {
		alert(JSON.stringify(error));
	});
}

function sharedCancel(noteId, userId) {
	
	$.ajax({
		type: "POST",
		url: "/notes/"+noteId+"/sharedCancel",
		contentType: "application/json; charset=utf-8",
	}).done(function(resp) {
		alert("노트를 공유를 취소했습니다.");
		location.href="/users/"+userId;
	}).fail(function(error) {
		alert(JSON.stringify(error));
	});
}

function noteUpdate(noteId) {
	$.ajax({
		type: "GET",
		url: "/note_update/"+noteId,
		contentType: "application/json; charset=utf-8"
	}).done(function(response) {
		console.log(response);
		if (response == "") {
			location.href="/login";
		}
		$("#note-title").text("");
		$("#note-tag").text("");
		$("#note-main").text("");
		$("#note-title").html('<div class="form-group"> <label for="title">Title : </label>'
			+ '<input class="form-control" id="title" name="title" type="text" value="'+response.title+'" placeholder="Title Here" required></div>'
			+ '<div class="form-group"> <label for="mCategory">Major Category : </label>'
			+ '<input class="form-control" id="mCategory" name="mCategory" type="text" value="'+response.mCategory+'" placeholder="Major Category Here" required></div>'
			+ '<div class="form-group"> <label for="sCategory">Sub Category : </label>'
			+ '<input class="form-control" id="sCategory" name="sCategory" type="text" value="'+response.sCategory+'" placeholder="Sub Category Here" required></div>'
			+ '<div class="form-group"> <label for="tags">Tags : </label> <div class="input-group mb-3">');
			for (var i = 0; i < response.noteTagsResponses.length; i++) {
				$("#note-tag").html('<input class="form-control" id="tag'+i+'" name="tag'+i+'" type="text" value="'+response.noteTagsResponses[i].tag+'" placeholder="Tags Here" disabled>');
			}
		$("#note-main").html('</div>'
		+ '<div class="form-group">'
		+ '<textarea id="content" class="form-control summernote" name="content" required>'+response.content+'</textarea></div>'
		+ '<div class="form-group mt-3"><button onclick="updateRequest('+noteId+')" class="btn btn-warning">노트 수정</button></div>');

		$('.summernote').summernote({
			placeholder: 'Content Here',
			tabsize: 2,
			height: 800,
			maxHeight: 1200,
			minHeight: 400,
			toolbar: [
		          ['style', ['style']],
		          ['font', ['bold', 'underline', 'clear']],
		          ['color', ['color']],
		          ['para', ['ul', 'ol', 'paragraph']],
		          ['table', ['table']],
		          ['insert', ['link', 'picture', 'video']],
		          ['view', ['help']]
		    ]
		});
	}).fail(function(error) {
		alert(JSON.stringify(error));
	});
}

function updateRequest(noteId) {
	let principal = document.querySelector("#principal").value;
	
	let data = {
		id:noteId,
		title:$("#title").val(),
		mCategory:$("#mCategory").val(),
		sCategory:$("#sCategory").val(),
		noteTagsRequests: [{tag:$("#tag1").val()}, {tag:$("#tag2").val()}, {tag:$("#tag3").val()},
			{tag:$("#tag4").val()}, {tag:$("#tag5").val()}],
		content:$("textarea#content").val()
	}

	$.ajax({
		type: "POST",
		url: "/update_request",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).done(function(response) {	
		if (response == "") {
			location.href="/login";
		} else {
			location.href="/users/"+principal;
		}	
	}).fail(function(error) {
		alert(JSON.stringify(error));
	});
}

index.init();