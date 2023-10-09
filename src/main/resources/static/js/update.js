// (1) 회원정보 수정
function update(userId, event) {
    event.preventDefault();

    let data = $("#profileUpdate").serialize();

    $.ajax({
        type: "put",
        url : `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res=>{
        location.href = `/user/${userId}`;
    }).fail(error=>{
        if (error.data == null) {
            alert(error.responseJSON.message);  // 에러 메시지 출력
        } else {
            alert(JSON.stringify(error.responseJSON.data));  // errorMap 출력
        }
    });
}
