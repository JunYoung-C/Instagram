const showAddPostBtn = document.querySelector(".show-add-post");
const addPostDiv = document.querySelector("#add-post");
const addPostCancleBtn = document.querySelector(".add-post__cancle");

addPostCancleBtn.addEventListener('click', () => {
  addPostDiv.style.display = "none";
})

showAddPostBtn.addEventListener('click', () => {
  addPostDiv.style.display = "";
})