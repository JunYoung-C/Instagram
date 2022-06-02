const postSettingModal = document.querySelector(".post-setting-modal");

function hidePostSetting() {
  document.querySelector(".post-setting__cancel").addEventListener("click", function() {
    postSettingModal.style.display = "none";
  });
}

function showPostSetting(isMyPost) {
  const modifyButton = document.querySelector(".post-setting__modify");
  const deleteButton = document.querySelector(".post-setting__delete");

  if (isMyPost) {
    modifyButton.style.display = "";
    deleteButton.style.display = "";
  } else {
    modifyButton.style.display = "none";
    deleteButton.style.display = "none";
  }

  postSettingModal.style.display = "block";
}

hidePostSetting();
