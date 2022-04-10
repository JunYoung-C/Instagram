const mainBody = document.querySelector(".main-body");

const showAddPostButton = document.querySelector(".show-add-post");
const addPostDiv = document.querySelector("#add-post");
const addPostCancelButtons = document.querySelectorAll(".add-post-cancel");
const addPostFileInput = document.querySelector(".add-post__file-input");
const addPostPreview = document.querySelector(".add-post__preview");
const addPostText = document.querySelector(".add-post__text");

const previewPrevButton = document.querySelector(".add-post__preview-prev-button");
const previewNextButton = document.querySelector(".add-post__preview-next-button");
const previewListCount = document.querySelectorAll(".add-post__preview-list").length;

const comment = document.querySelector("#comment");
const commentCancel = document.querySelector(".comment-cancel");



const PREVIEW_IMAGE_WIDTH = 453.59;
const COMMENT_POST_IMAGE_WIDTH = 555;

function showPreviewFiles(event) {
    const files = event.target.files;

    Array.from(files).forEach((file) => {
        const reader = new FileReader();

        reader.addEventListener("load", function (event) {
            const img = document.createElement("img");
            img.src = event.target.result;
            img.classList.add("add-post__preview-image");

            const li = document.createElement("li");
            li.classList.add("add-post__preview-list");

            li.appendChild(img);
            addPostPreview.appendChild(li);
        });

        reader.readAsDataURL(file);
    });

    previewSlideController.clear(files.length);
}

function postFormClear() {
    addPostPreview.innerHTML = "";
    addPostFileInput.value = "";
    addPostText.value = "";
}

function addNewPostPageEvent() {
    addPostCancelButtons.forEach((addPostCancelBtn) => {
        addPostCancelBtn.addEventListener("click", () => {
            addPostDiv.style.display = "none";
            mainBody.style.overflow = "auto";
            postFormClear();
            previewSlideController.clear(0);
        });
    });

    showAddPostButton.addEventListener("click", () => {
        addPostDiv.style.display = "block";
        mainBody.style.overflow = "hidden";
    });

    previewPrevButton.addEventListener("click", (event) => {
        previewSlideController.slide(event.currentTarget);
    });

    previewNextButton.addEventListener("click", (event) => {
        previewSlideController.slide(event.currentTarget);
    });

    addPostFileInput.addEventListener("change", showPreviewFiles);
}





const previewSlideController = new SlideController(PREVIEW_IMAGE_WIDTH, previewListCount, previewPrevButton, previewNextButton, addPostPreview);




addNewPostPageEvent();
addCommentPageEvent();