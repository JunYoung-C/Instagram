const profileEditModel = document.querySelector(".profile-edit-modal");

document.querySelector(".profile__edit").addEventListener("click", function() {
  profileEditModel.style.display = "block";
})

document.querySelector(".profile-edit__cancel").addEventListener("click", function() {
  profileEditModel.style.display = "none";
})