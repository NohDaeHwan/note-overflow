function validate() {
    let data = {
        userEmail:$("#userEmail").val(),
        userPhone:$("#userPhone").val(),
        nickname:$("#nickname").val()
    }

    const phoneValid = /(01[016789])([1-9]{1}[0-9]{2,3})([0-9]{4})$/;
    if (!phoneValid.test(data.userPhone)) {
        console.log(phoneValid.test(data.userPhone));
        alert("정확히지 않는 핸드폰 번호 입니다.")
        return false;
    }

    const nicknameValid1 = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gim;
    if (nicknameValid1.test(data.nickname)) {
        console.log(nicknameValid1.test(data.nickname));
        alert("닉네임에 특수문자는 포함 할 수 없습니다.")
        return false;
    }

    const nicknameValid2 = /^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{5,30}$/;
    if (!nicknameValid2.test(data.nickname)) {
        console.log(nicknameValid2.test(data.nickname));
        alert("닉네임은 5 ~ 30 글자 사이여야 합니다.")
        return false;
    }

    return true;
}