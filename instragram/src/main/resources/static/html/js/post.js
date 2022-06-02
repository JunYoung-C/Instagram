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

function addCommentInputEvent() {
  const commentForm = document.querySelector(".post__comment-form");
  const commentFormText = document.querySelector(".comment-form__text");

  document.querySelector(".post__comments").addEventListener("click", (event) => {
    if (event.target.classList.contains("comment_owner__reply")) {
      commentFormText.placeholder = "답글 달기...";
      commentForm.setAttribute("action", "/replies");
      // document.querySelector(".post-comment__comment-id-input").value = event.target.getAttribute("commentId");
      commentFormText.focus();
    }
  });
  
  document.querySelector(".post-option__comment").addEventListener("click", function() {
    commentFormText.placeholder = "댓글 달기...";
    commentForm.setAttribute("action", "/comments")
    commentFormText.focus();
  })
}

function addLikePostEvent() {
  const postOptionHeart = document.querySelector(".post-option__heart");
  const postOptionCompactedHeart = document.querySelector(".post-option__heart--compacted");
  
  postOptionHeart.addEventListener("click", function() {
    postOptionCompactedHeart.classList.remove("post-option__heart--unvisible");
    postOptionHeart.classList.add("post-option__heart--unvisible");
  });
  
  postOptionCompactedHeart.addEventListener("click", function() {
    postOptionHeart.classList.remove("post-option__heart--unvisible");
    postOptionCompactedHeart.classList.add("post-option__heart--unvisible");
  });
}

function addLikeCommentEvent() {
  const UNVISIBLE_CLASS = "comment-owner__heart--unvisible";

  document.querySelector(".post__comments").addEventListener("click", function(event) {
    const target = event.target;
    console.log(target);
    if (target.classList.contains("comment-owner__heart")) {
      target.classList.add(UNVISIBLE_CLASS);
      target.parentNode.querySelector(".comment-owner__heart--compacted").classList.remove(UNVISIBLE_CLASS);
    }
  });

  document.querySelector(".post__comments").addEventListener("click", function(event) {
    const target = event.target;
    console.log(target);
    if (target.classList.contains("comment-owner__heart--compacted")) {
      target.classList.add(UNVISIBLE_CLASS);
      target.parentNode.querySelector(".comment-owner__heart").classList.remove(UNVISIBLE_CLASS);
    }
  });
}


function addPostPageEvent() {
  addPostSlidingEvent();
  reduceDescription();
  addCommentInputEvent();
  addLikePostEvent();
  addLikeCommentEvent();
}

addPostPageEvent();