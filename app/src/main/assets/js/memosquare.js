// 내가 만들어본 자바스크립트

// br -> p 태그로 변환
$('div br').each ( function () {
    if (this.parentNode.nodeName != "P") {
        $.each ([this.previousSibling, this.nextSibling], function () {
            if (this.nodeType === 3) { // 3 == text
                $(this).wrap ('<p></p>');
            }
        } );

        $(this).remove (); //-- Kill the <br>
    }
} );

var isCliping = false;

var highlight = function(event) {

    console.log("content clicked");

    if (!isCliping) {
        console.log("in view mode");
        return;
    } else {
        console.log("in clip mode");
    }

    var target = $(event.target);
    var text = target.text();

//    $(event.target).addClass("highlight-by-memosquare");
    if (target.css("backgroundColor") == "rgb(225, 225, 119)") {
        target.css("backgroundColor", "transparent");
    } else {
        target.css("backgroundColor", "rgb(225, 225, 119)");
    }
    window.INTERFACE.getContent(text);
//    window.INTERFACE.getPage(document.documentElement.outerHTML);
}

var copyImg = function(event) {
    // 이미지 클릭시 예제 이미지 띄우는 것 구현해야함
    // url 받아오는건 쉬울 듯 한데, 이미지 자체를 받아올 순 없으려나?
    // 그럼 전에 선영이가 보여준거 그대로 구현할수 있을텐데
}

memosquareCliping = function() {
    console.log("isCliping changed to " + !isCliping);
    isCliping = !isCliping;

    if (isCliping) {
        // 이벤트 할당
        $('p, li, h1, h2, h3, h4, h5, h6').bind("click",highlight);
        $('img').click(copyImg);
    }
}
