const postImagePrevButton = document.querySelector(".post-images__prev-button");
const postImageNextButton = document.querySelector(".post-images__next-button");
const postImagesCount = document.querySelectorAll(".post-images__list").length;
const postImages = document.querySelector(".post-images");

const mainBody = document.querySelector(".main-body");
const showAddPostButton = document.querySelector(".show-add-post");
const addPostDiv = document.querySelector("#add-post");
const addPostCancleButtons = document.querySelectorAll(".add-post-cancel");
const addPostFileInput = document.querySelector(".add-post__file-input");
const addPostPreview = document.querySelector(".add-post__preview");
const addPostText = document.querySelector(".add-post__text");

const previewPrevButton = document.querySelector(".add-post__preview-prev-button");
const previewNextButton = document.querySelector(".add-post__preview-next-button");
const previewListCount = document.querySelectorAll(".add-post__preview-list").length;

const POST_IMAGE_WIDTH = 600;
const PREVIEW_IMAGE_WIDTH = 453.59;

class SlideController {
  constructor(slideWidth, imageCount, prevButton, nextButton, images) {
    this.index = 0;
    this.slideWidth = slideWidth;
    this.imageCount = imageCount;
    this.prevButton = prevButton;
    this.nextButton = nextButton;
    this.images = images;
  }

  slide (selectedButton) {
    const increment = (selectedButton === this.prevButton) ? -1 : 1;
  
    this.index += increment;
    console.log(this.index);
    this.images.style.left = -(this.index * this.slideWidth) + "px";
    this.controlButtons();
  }
  
  controlButtons() {
    if (this.index <= 0) {
      this.removeButton(this.prevButton);
    }
    if (this.index >= this.imageCount - 1) {
      this.removeButton(this.nextButton);
    }
  
    if (this.index < this.imageCount - 1 && !this.isVisible(this.nextButton)) {
      this.addButton(this.nextButton);
    }
    if (0 < this.index && !this.isVisible(this.prevButton)) {
      this.addButton(this.prevButton);
    }
  }
  
  removeButton(imageButton) {
    imageButton.classList.add("unvisible-button");
  }
  addButton(imageButton) {
    imageButton.classList.remove("unvisible-button");
  }
  isVisible(imageButton) {
    imageButton.classList.contains("unvisible-button");
  }

  clear(imageCount) {
    this.index = 0;
    this.imageCount = imageCount;
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
  console.log(files.length);
}

function postFormClear() {
  addPostPreview.innerHTML = "";
  addPostFileInput.value = "";
  addPostText.value = "";
}

const postImageSlideController = new SlideController(POST_IMAGE_WIDTH, postImagesCount, postImagePrevButton, postImageNextButton, postImages);

postImagePrevButton.addEventListener("click", (event) => {
  postImageSlideController.slide(event.currentTarget);
});

postImageNextButton.addEventListener("click", (event) => {
  postImageSlideController.slide(event.currentTarget);
});

addPostCancleButtons.forEach((addPostCancleBtn) => {
  addPostCancleBtn.addEventListener("click", () => {
    addPostDiv.style.display = "none";
    mainBody.style.overflow = "auto";
    postFormClear();
  })
});

showAddPostButton.addEventListener("click", () => {
  addPostDiv.style.display = "block";
  mainBody.style.overflow = "hidden";
});

const previewSlideController = new SlideController(PREVIEW_IMAGE_WIDTH, previewListCount, previewPrevButton, previewNextButton, addPostPreview);

previewPrevButton.addEventListener("click", (event) => {
  previewSlideController.slide(event.currentTarget);
});

previewNextButton.addEventListener("click", (event) => {
  previewSlideController.slide(event.currentTarget);
});

addPostFileInput.addEventListener("change", showPreviewFiles);

if (postImagesCount === 1) {
  postImageNextButton.classList.add("unvisible-button");
}

