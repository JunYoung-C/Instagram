const mainBody = document.querySelector(".main-body");
const showAddPostBtn = document.querySelector(".show-add-post");
const addPostDiv = document.querySelector("#add-post");
const addPostCancleBtns = document.querySelectorAll(".add-post-cancel");
const addPostFileInput = document.querySelector(".add-post__file-input");
const addPostPreview = document.querySelector(".add-post__preview");
const addPostText = document.querySelector(".add-post__text");

function showPreviewFiles(event) {
  const files = event.target.files;

  Array.from(files).forEach((file) => {
    const reader = new FileReader();

    reader.addEventListener("load", function(event) {
      const img = document.createElement("img");
      img.src = event.target.result;
      addPostPreviewImages.appendChild(img);
    });
  
    reader.readAsDataURL(file);
  });
}

addPostCancleBtns.forEach((addPostCancleBtn) => {
  addPostCancleBtn.addEventListener("click", () => {
    addPostDiv.style.display = "none";
    mainBody.style.overflow = "auto";
    addPostPreview.innerHTML = "";
    addPostFileInput.value = "";
    addPostText.value = "";
  })
});

showAddPostBtn.addEventListener("click", () => {
  addPostDiv.style.display = "block";
  mainBody.style.overflow = "hidden";
});

addPostFileInput.addEventListener("change", showPreviewFiles);