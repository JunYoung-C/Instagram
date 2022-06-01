const postContentDescription = document.querySelector(".post-content__description");

function addPostSlidingEvent() {
  const POST_IMAGE_WIDTH = 555;
  const leftSlideButton = document.querySelector(".slide-button--left");
  const rightSlideButton = document.querySelector(".slide-button--right");
  const fileCount = document.querySelectorAll(".post-file").length;
  const postFiles = document.querySelector(".post-files");
  const postFileSlideController = new SlideController(POST_IMAGE_WIDTH, fileCount, leftSlideButton, rightSlideButton, postFiles);

  leftSlideButton.addEventListener("click", (event) => {
    postFileSlideController.slide(event.currentTarget);
  });

  rightSlideButton.addEventListener("click", (event) => {
    postFileSlideController.slide(event.currentTarget);
  });
}

function reduceDescription() {
  const text = postContentDescription.innerText;
  if (text.length < 300) {
    return;
  }

  const shortText = text.substring(0, 300) + "...";
  
  postContentDescription.innerText = shortText;
  postContentDescription.appendChild(createMoreSpan(text));
}

function createMoreSpan(text) {
  const span = document.createElement("span");
  span.classList.add("post-content__more-description");
  span.innerText = "더 보기";

  span.addEventListener("click", function() {
    postContentDescription.innerText = text;
  });

  return span;
}

function addPostPageEvent() {
  addPostSlidingEvent();
  reduceDescription();
}

addPostPageEvent();