const keyword = document.querySelector(".keyword")
const keywords = document.querySelector(".keywords")

function closeKeywords() {
    keywords.style.display = "none"
    keywords.innerHTML = ""
}

keyword.addEventListener("keyup", (e) => { 
    
    const selectedKeyword = keywords.querySelector("li.selected")
    
    // li.selected 가 없는 경우에만 DB에서 데이터를 가져옴
    if(keyword.value.length > 0 && !selectedKeyword) {

		var query = $(".keyword").val();
		
		$.ajax({
			type: "GET",
			url: "/notes/recommend?query="+query,
			contentType: "application/json; charset=utf-8"
		}).done(function(response) {
			console.log(response);
			keywords.innerHTML = ""
        
        	const $ul = document.createElement("ul")
        	for(let tag of response) {
            	const $li = document.createElement("li")
            	$li.textContent = `${tag}`
            	$ul.append($li)
        	}
        	keywords.append($ul)
        
       	 	keywords.style.display = "block"
			
		}).fail(function(error) {
			alert(JSON.stringify(error));
			console.log(error);
		});
        
    } 

    if(keyword.value.length === 0) {
        keywords.innerHTML = ""
    }
    
    // Esc를 누르면 추천 검색어 창이 닫힌다.
    if(e.key === "Escape") {
        closeKeywords()
    }

    // 키보드의 위, 아래 키를 누르면 추천 검색어 하이라이트가 옮겨지고 엔터를 누르면 하이라이트가 위치한 검색어가 입력창에 반영된다.
    const keywordsList = keywords.querySelectorAll("li")
    
    if((e.key === "ArrowUp" || e.key === "ArrowDown") && keywords.style.display === "block") {
        let target
        const initIndex = e.key === "ArrowUp" ? keywordsList.length - 1 : 0
        const adjacentSibling = selectedKeyword && (e.key === "ArrowUp" ? selectedKeyword.previousElementSibling : selectedKeyword.nextElementSibling)
        
        if(adjacentSibling) {
            target = adjacentSibling
        } else {
            target = keywordsList.item(initIndex)
        } 
        
        selectedKeyword && selectedKeyword.classList.remove("selected")
        target.classList.add("selected")
        keyword.value = target.textContent
    }
})

// 마우스로 다른 곳을 클릭하여 input이 focus를 잃어버리는 경우 추천 검색어 창이 닫힌다.
document.addEventListener("click", e => {
    const closestKeywords = e.target.closest(".keywords") // 부모 요소 중에 keywords 클래스를 가진 부모가 있는지 확인
    if(!closestKeywords && keywords.style.display === "block") {
        closeKeywords()
    }
})

// 마우스로 추천 검색어를 누르면 커서가 위치한 검색어가 입력창에 반영딘다.
keywords.addEventListener("click", e => {
    keyword.value = e.target.textContent
})