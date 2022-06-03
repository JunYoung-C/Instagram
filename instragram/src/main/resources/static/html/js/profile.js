function addProfileModalEvent() {
  const profileEditModel = document.querySelector(".profile-edit-modal");

  document.querySelector(".profile__edit").addEventListener("click", function() {
    profileEditModel.style.display = "block";
  });
  
  document.querySelector(".profile-edit__cancel").addEventListener("click", function() {
    profileEditModel.style.display = "none";
  });
}

function showPreviewFile(event) {
  const file = event.target.files[0];
  const reader = new FileReader();

  reader.addEventListener("load", function (event) {
    document.querySelector(".profile-edit__image").src = event.target.result;
  });

  reader.readAsDataURL(file);
}

addProfileModalEvent();
document.querySelector(".profile-edit__file").addEventListener("change", showPreviewFile);