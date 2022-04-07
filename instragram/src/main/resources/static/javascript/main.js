const mainBody = document.querySelector(".main-body");
const headerSearchInput = document.querySelector(".header-search__input");
const searchResultWrap = document.querySelector(".search-result-wrap");
const mainPostImagePrevButton = document.querySelector(".post-images__prev-button");
const mainPostImageNextButton = document.querySelector(".post-images__next-button");
const mainPostImagesCount = document.querySelectorAll(".main-post-image").length;
const mainPostImages = document.querySelector(".main-post-images");

const showAddPostButton = document.querySelector(".show-add-post");
const addPostDiv = document.querySelector("#add-post");
const addPostCancleButtons = document.querySelectorAll(".add-post-cancel");
const addPostFileInput = document.querySelector(".add-post__file-input");
const addPostPreview = document.querySelector(".add-post__preview");
const addPostText = document.querySelector(".add-post__text");

const previewPrevButton = document.querySelector(".add-post__preview-prev-button");
const previewNextButton = document.querySelector(".add-post__preview-next-button");
const previewListCount = document.querySelectorAll(".add-post__preview-list").length;

const showComment = document.querySelector(".show-comment");
const comment = document.querySelector("#comment");
const commentCancel = document.querySelector(".comment-cancel");
const commentPostImagePrevButton = document.querySelector(".comment-post-images__prev-button");
const commentPostImageNextButton = document.querySelector(".comment-post-images__next-button");
const commentPostImagesCount = document.querySelectorAll(".comment-post-image").length;
const commentPostImages = document.querySelector(".comment-post-images");

const MAIN_POST_IMAGE_WIDTH = 600;
const PREVIEW_IMAGE_WIDTH = 453.59;
const COMMENT_POST_IMAGE_WIDTH = 555;

class SlideController {
  constructor(slideWidth, imageCount, prevButton, nextButton, images) {
    this.index = 0;
    this.slideWidth = slideWidth;
    this.imageCount = imageCount;
    this.prevButton = prevButton;
    this.nextButton = nextButton;
    this.images = images;

    this.controlButtons();
  }

  slide (selectedButton) {
    const increment = (selectedButton === this.prevButton) ? -1 : 1;
  
    this.index += increment;
    this.images.style.left = -(this.index * this.slideWidth) + "px";
    this.controlButtons();
  }
  
  controlButtons() {
    if (this.isFirstPage()) {
      this.hideprevButton();
    } else {
      this.showprevButton();
    }

    if (this.isLastPage()) {
      this.hideNextButton();
    } else {
      this.showNextButton();
    }
  }

  isFirstPage() {
    return this.index <= 0;
  }

  isLastPage() {
    return this.index >= this.imageCount - 1;
  }

  hideprevButton() {
    this.prevButton.style.display = "none";
  }

  hideNextButton() {
    this.nextButton.style.display = "none";
  }

  showprevButton() {
    this.prevButton.style.display = "flex";
  }

  showNextButton() {
    this.nextButton.style.display = "flex";
  }

  clear(imageCount) {
    this.index = 0;
    this.imageCount = imageCount;
    this.images.style.left = "0px";
    this.controlButtons();
  }
}

function showPreviewFiles(event) {
  const files = event.target.files;

  Array.from(files).forEach((file) => {
    const reader = new FileReader();

    reader.addEventListener("load", function(event) {
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

function addMainPageEvent() {
  mainPostImagePrevButton.addEventListener("click", (event) => {
    mainPostImageSlideController.slide(event.currentTarget);
  });
  
  mainPostImageNextButton.addEventListener("click", (event) => {
    mainPostImageSlideController.slide(event.currentTarget);
  });
  
  headerSearchInput.addEventListener("focus", () => {
    searchResultWrap.style.display = "block";
  });
  
  headerSearchInput.addEventListener("blur", () => {
    searchResultWrap.style.display = "none";
  });
}

function addNewPostPageEvent() {
addPostCancleButtons.forEach((addPostCancleBtn) => {
  addPostCancleBtn.addEventListener("click", () => {
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

function addCommentPageEvent() {
  commentPostImagePrevButton.addEventListener("click", (event) => {
    commentPostSlideController.slide(event.currentTarget);
  });
  
  commentPostImageNextButton.addEventListener("click", (event) => {
    commentPostSlideController.slide(event.currentTarget);
  });

  showComment.addEventListener("click", () => {
    comment.style.display = "block";
    mainBody.style.overflow = "hidden";
  });

  commentCancel.addEventListener("click", () => {
    comment.style.display = "none";
    mainBody.style.overflow = "auto";
  });
}

const mainPostImageSlideController = new SlideController(MAIN_POST_IMAGE_WIDTH, mainPostImagesCount, mainPostImagePrevButton, mainPostImageNextButton, mainPostImages);

const previewSlideController = new SlideController(PREVIEW_IMAGE_WIDTH, previewListCount, previewPrevButton, previewNextButton, addPostPreview);

const commentPostSlideController = new SlideController(COMMENT_POST_IMAGE_WIDTH, commentPostImagesCount, commentPostImagePrevButton, commentPostImageNextButton, commentPostImages);

addMainPageEvent();
addNewPostPageEvent();
addCommentPageEvent();