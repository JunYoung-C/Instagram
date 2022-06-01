const addPostFormText = document.querySelector(".add-post-form__text");

function showPreviewFiles(event) {
  const files = event.target.files;
  const addPostFormFiles = document.createElement("div");
  addPostFormFiles.classList.add("add-post-form__files");
  
  Array.from(files).forEach((file) => {
      const reader = new FileReader();

      reader.addEventListener("load", function (event) {
          const img = document.createElement("img");
          img.src = event.target.result;
          img.classList.add("add-post-form__file");

          addPostFormFiles.appendChild(img);
      });

      reader.readAsDataURL(file);
  });
  document.querySelector(".add-post-form__files-wrap").appendChild(addPostFormFiles);
  addPreviewSlidingEvent(addPostFormFiles, files.length);
}

function addPreviewSlidingEvent(addPostFormFiles, fileCount) {
  const PREVIEW_IMAGE_WIDTH = 500;
  const leftSlideButton = document.querySelector(".slide-button--left");
  const rightSlideButton = document.querySelector(".slide-button--right");
  const previewSlideController = new SlideController(PREVIEW_IMAGE_WIDTH, fileCount, leftSlideButton, rightSlideButton, addPostFormFiles);

  leftSlideButton.addEventListener("click", (event) => {
    previewSlideController.slide(event.currentTarget);
  });

  rightSlideButton.addEventListener("click", (event) => {
    previewSlideController.slide(event.currentTarget);
  });
}

function addTextEvent() {
  validateStringLimit();
  document.querySelector(".add-post-form__text-count").textContent = addPostFormText.value.length;
}

function validateStringLimit() {
  const MAX_STRING_LENGTH = 2200;
  if (addPostFormText.value.length > MAX_STRING_LENGTH) {
    addPostFormText.value = addPostFormText.value.substring(0, MAX_STRING_LENGTH);
  }
}

function addPostRegistrationPageEvent() {
  document.querySelector(".add-post-form__file-input").addEventListener("change", showPreviewFiles);

  addPostFormText.addEventListener("keyup", addTextEvent);
}

addPostRegistrationPageEvent();