const mainPostWrap = document.querySelector(".main-post-wrap");

function ajax() {
  const request = new XMLHttpRequest();

  if (!request) {
    alert("XMLHTTP 인스턴스 생성 불가");
    return false;
  }

  request.onreadystatechange = function() {
    if (request.readyState === XMLHttpRequest.DONE) {
      if (request.status === 200) {
        const response = request.response;
        mainPostWrap.innerHTML = response;
      } else {
        alert("request에 문제가 있습니다.")
      }
    }
  }

  request.open("get", "/html/mainPostTemplate.html");
  request.send();
}

ajax();