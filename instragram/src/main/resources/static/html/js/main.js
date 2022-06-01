const postArr = document.querySelectorAll(".post");
const postSettingModal = document.querySelector(".post-setting-modal");

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

function hidePostSetting() {
  document.querySelector(".post-setting__cancel").addEventListener("click", function() {
    postSettingModal.style.display = "none";
  });
}

function showPostSetting(isMyPost, isFollowed) {
  const modifyButton = document.querySelector(".post-setting__modify");
  const deleteButton = document.querySelector(".post-setting__delete");
  const followButton = document.querySelector(".post-setting__follow");

  if (isMyPost) {
    modifyButton.style.display = "";
    deleteButton.style.display = "";
    followButton.style.display = "none";
  } else {
    modifyButton.style.display = "none";
    deleteButton.style.display = "none";
    followButton.style.display = "";

    let followText = "팔로우";
    if (isFollowed) {
      followText = "팔로우 취소"
    } 
    followButton.querySelector(".post-setting__follow-text").textContent = followText
  }

  postSettingModal.style.display = "block";
}

postArr.forEach(post => addPostSlidingEvent(post));
hidePostSetting();