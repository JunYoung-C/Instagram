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

function reduceDescription(post) {
  const postContentDescription = post.querySelector(".post-content__description");
  const text = postContentDescription.innerText;
  if (text.length < 300) {
    return;
  }

  const shortText = text.substring(0, 100) + "...";
  
  postContentDescription.innerText = shortText;
  postContentDescription.appendChild(createMoreSpan(text, postContentDescription));
}

function createMoreSpan(text, postDescription) {
  const span = document.createElement("span");
  span.classList.add("post-content__more-description");
  span.innerText = "더 보기";

  span.addEventListener("click", function() {
    postDescription.innerText = text;
  });

  return span;
}

function addLikePostEvent(post) {
  const postLikeOption = post.querySelector(".post-option--like");

  postLikeOption.addEventListener("click", function() {
    post.querySelector(".post-content__like-icon").classList.toggle("post-content__like-icon--unvisible");
    post.querySelector(".post-content__like-icon--compacted").classList.toggle("post-content__like-icon--unvisible");
  });
}

// 좋아요 ajax

function addMainPageEvent() {
  postArr.forEach(post => {
    addPostSlidingEvent(post);
    reduceDescription(post);
    addLikePostEvent(post);
  });
  addPostFilterEvent();
}

addMainPageEvent();