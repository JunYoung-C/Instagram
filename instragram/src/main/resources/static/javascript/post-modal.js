const addPostDiv = document.querySelector("#add-post");
const addPostFileInput = document.querySelector(".add-post__file-input");
const addPostPreview = document.querySelector(".add-post__preview");
const previewPrevButton = document.querySelector(".add-post__preview-prev-button");
const previewNextButton = document.querySelector(".add-post__preview-next-button");
const previewListCount = document.querySelectorAll(".add-post__preview-list").length;

const updatePost = document.querySelector("#update-post");
const updatePostPreview = document.querySelector(".update-post__preview");
const updatePostImagePrevButton = document.querySelector(`.update-post__preview-prev-button`);
const updatePostImageNextButton = document.querySelector(`.update-post__preview-next-button`);

const PREVIEW_IMAGE_WIDTH = 453.59;
const UPDATE_POST_IMAGE_WIDTH = 453.59;

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
        });
    });

    updatePostImagePrevButton.addEventListener("click", (event) => {
        UpdatePostSlideController.slide(event.currentTarget);
    });

    updatePostImageNextButton.addEventListener("click", (event) => {
        UpdatePostSlideController.slide(event.currentTarget);
    });
}

function setUpdatePost(post, postId) {
    const filesCount = post.filePaths.length;

    document.querySelector(".update-post__form").setAttribute("action", `/posts/${postId}`);

    setUpdatePostFiles(filesCount, post);
    UpdatePostSlideController.clear(filesCount);

    document.querySelector(".update-post__profile-image")
        .setAttribute("src", `/images/${post.member.imagePath}`);
    document.querySelector(".update-post__text").value = post.text;
    document.querySelector(".update-post__profile-name").textContent = post.member.nickname;
}

function setUpdatePostFiles(filesCount, post) {
    updatePostPreview.innerHTML = "";
    for (let i = 0; i < filesCount; i++) {
        const img = document.createElement("img");
        img.src = `/images/${post.filePaths[i]}`;
        img.classList.add("post-modal__image-file");

        const li = document.createElement("li");
        li.classList.add("post-modal__file");

        li.appendChild(img);
        updatePostPreview.appendChild(li);
    }
}

const previewSlideController =
    new SlideController(PREVIEW_IMAGE_WIDTH, previewListCount, previewPrevButton, previewNextButton, addPostPreview);
const UpdatePostSlideController =
    new SlideController(UPDATE_POST_IMAGE_WIDTH, null, updatePostImagePrevButton, updatePostImageNextButton, updatePostPreview);

addNewPostPageEvent();
addUpdatePostPageEvent();