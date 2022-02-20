const addPostCancleBtn = document.querySelector(".add-post__cancle");
const addPostDiv = document.querySelector("#add-post");

addPostCancleBtn.addEventListener('click', () => {
  if (addPostDiv.getAttribute("display") == "none") {
    addPostDiv.setAttribute("display") = block;
  } else {
    addPostDiv.setAttribute("display") = "none";
  }
})