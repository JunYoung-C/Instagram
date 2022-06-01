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

  slide(selectedButton) {
      const increment = (selectedButton === this.prevButton) ? -1 : 1;

      this.index += increment;
      this.images.style.left = -(this.index * this.slideWidth) + "px";
      this.controlButtons();
  }

  controlButtons() {
      if (this.isFirstPage()) {
          this.hidePrevButton();
      } else {
          this.showPrevButton();
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

  hidePrevButton() {
      this.prevButton.style.display = "none";
  }

  hideNextButton() {
      this.nextButton.style.display = "none";
  }

  showPrevButton() {
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