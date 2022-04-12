const addPostDiv = document.querySelector("#add-post");
const addPostFileInput = document.querySelector(".add-post__file-input");
const addPostPreview = document.querySelector(".add-post__preview");
const previewPrevButton = document.querySelector(".add-post__preview-prev-button");
const previewNextButton = document.querySelector(".add-post__preview-next-button");
const previewListCount = document.querySelectorAll(".add-post__preview-list").length;
const updatePost = document.querySelector("#update-post");

const PREVIEW_IMAGE_WIDTH = 453.59;

function showPreviewFiles(event) {
    const files = event.target.files;

    Array.from(files).forEach((file) => {
        const reader = new FileReader();

        reader.addEventListener("load", function (event) {
            const img = document.createElement("img");
            img.src = event.target.result;
            img.classList.add("post-modal__image-file");

            const li = document.createElement("li");
            li.classList.add("post-modal__file");

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
    document.querySelector(".add-post__text").value = "";
}

function addNewPostPageEvent() {
    const showAddPostButton = document.querySelector(".show-add-post");
    const addPostCancelButtons = document.querySelectorAll(".add-post-cancel");

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

function addUpdatePostPageEvent() {
    const updatePostCancelButtons = document.querySelectorAll(".update-post-cancel");

    updatePostCancelButtons.forEach((updatePostCancelBtn) => {
        updatePostCancelBtn.addEventListener("click", () => {
            updatePost.style.display = "none";
            mainBody.style.overflow = "auto";
            // previewSlideController.clear(0);
        });
    });
}

const previewSlideController = new SlideController(PREVIEW_IMAGE_WIDTH, previewListCount, previewPrevButton, previewNextButton, addPostPreview);

addNewPostPageEvent();
addUpdatePostPageEvent();