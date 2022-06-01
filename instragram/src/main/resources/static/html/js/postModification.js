const updatePostFormText = document.querySelector(".update-post-form__text");

function addPostSlidingEvent() {
  const POST_IMAGE_WIDTH = 500;
  const leftSlideButton = document.querySelector(".slide-button--left");
  const rightSlideButton = document.querySelector(".slide-button--right");
  const fileCount = document.querySelectorAll(".update-post-form__file").length;
  const postFiles = document.querySelector(".update-post-form__files");
  const postFileSlideController = new SlideController(POST_IMAGE_WIDTH, fileCount, leftSlideButton, rightSlideButton, postFiles);

  leftSlideButton.addEventListener("click", (event) => {
    postFileSlideController.slide(event.currentTarget);
  });

  rightSlideButton.addEventListener("click", (event) => {
    postFileSlideController.slide(event.currentTarget);
  });
}

function addTextEvent() {
  validateStringLimit();
  document.querySelector(".update-post-form__text-count").textContent = updatePostFormText.value.length;
}

function validateStringLimit() {
  const MAX_STRING_LENGTH = 2200;
  if (updatePostFormText.value.length > MAX_STRING_LENGTH) {
    updatePostFormText.value = updatePostFormText.value.substring(0, MAX_STRING_LENGTH);
  }
}

function addExistingTagEvent(tag) {
  tag.addEventListener("click", function() {
    postContentTags.removeChild(tag);
  });
}

function addPostModificationPageEvent() {
  addPostSlidingEvent();
  document.querySelectorAll(".post-content__tag").forEach(addExistingTagEvent);
  updatePostFormText.addEventListener("keyup", addTextEvent);
}

addPostModificationPageEvent();