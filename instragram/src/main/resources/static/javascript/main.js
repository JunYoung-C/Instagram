const imagePrevButton = document.querySelector(".image-prev-button");
const imageNextButton = document.querySelector(".image-next-button");
const postImagesCnt = document.querySelectorAll(".post-images__list").length;
const postImages = document.querySelector(".post-images");
const slideWidth = 600;

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
}

const postImageSlideController = new SlideController(slideWidth, postImagesCnt, imagePrevButton, imageNextButton, postImages);

imagePrevButton.addEventListener("click", (event) => {
  postImageSlideController.slide(event.currentTarget);
});

imageNextButton.addEventListener("click", (event) => {
  postImageSlideController.slide(event.currentTarget);
});

window.onload = function() {
  if (postImagesCnt === 1) {
    removeButton(imageNextButton);
  }
}
