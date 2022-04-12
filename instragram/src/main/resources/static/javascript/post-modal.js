const addPostDiv = document.querySelector("#add-post");
const addPostFileInput = document.querySelector(".add-post__file-input");
const addPostPreview = document.querySelector(".add-post__preview");
const previewPrevButton = document.querySelector(".add-post__preview-prev-button");
const previewNextButton = document.querySelector(".add-post__preview-next-button");
const previewListCount = document.querySelectorAll(".add-post__preview-list").length;

const PREVIEW_IMAGE_WIDTH = 453.59;

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
    const updatePostCancelButton = document.querySelectorAll(".update-post-cance");

    mainBody.addEventListener("click", (event) => {
        const target = event.target;

        if (target.classList.contains("option-box-body__modify")) {
            console.log(target);
            document.querySelector("#update-post").style.display = "block";
            optionBox.style.display = "none";
            // 해당 게시물을 조회해서 수정 폼에 넣어야함
        }
    });

    // updatePostCancelButton.addEventListener("click", () => {
    //     addPostDiv.style.display = "none";
    //     mainBody.style.overflow = "auto";
    // });
}

const previewSlideController = new SlideController(PREVIEW_IMAGE_WIDTH, previewListCount, previewPrevButton, previewNextButton, addPostPreview);

addNewPostPageEvent();
addUpdatePostPageEvent();