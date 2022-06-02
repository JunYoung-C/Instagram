const postArr = document.querySelectorAll(".post");

function addPostSlidingEvent(post) {
  const MAIN_POST_IMAGE_WIDTH = 600;
  const leftSlideButton = post.querySelector(".slide-button--left");
  const rightSlideButton = post.querySelector(".slide-button--right");
  const fileCount = post.querySelectorAll(".post-file").length;
  const postFiles = post.querySelector(".post-files");
  const postFileSlideController = new SlideController(MAIN_POST_IMAGE_WIDTH, fileCount, leftSlideButton, rightSlideButton, postFiles);

  leftSlideButton.addEventListener("click", (event) => {
    postFileSlideController.slide(event.currentTarget);
  });

  rightSlideButton.addEventListener("click", (event) => {
    postFileSlideController.slide(event.currentTarget);
  });
}

function addPostFilterEvent() {
  const postFilterModal = document.querySelector(".post-filter-modal");
  document.querySelector(".show-post-filter").addEventListener("click", function() {
    postFilterModal.style.display = "block";
  });
  
  document.querySelector(".post-filter-form__reset").addEventListener("click", function() {
    postFilterModal.style.display = "none";
  });
}

// 좋아요 ajax

function addMainPageEvent() {
  postArr.forEach(post => addPostSlidingEvent(post));
  addPostFilterEvent();
}

addMainPageEvent();