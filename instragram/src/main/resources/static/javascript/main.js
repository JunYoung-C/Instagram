const imagePrevButton = document.querySelector(".image-prev-button");
const imageNextButton = document.querySelector(".image-next-button");
const postImages = document.querySelector(".post-images");
const postImagesCnt = document.querySelectorAll(".post-images__list").length;
const slideWitdh = 600;
let currentIdx = 0;
const delayTime = 500;

function slide(event) {
  const target = event.currentTarget;
  const increment = (target === imagePrevButton) ? -1 : 1;

  currentIdx += increment;
  postImages.style.left = -(currentIdx * slideWitdh) + "px";

  if (currentIdx <= 0) {
    removeButton(imagePrevButton);
  } 
  if (currentIdx >= postImagesCnt - 1) {
    removeButton(imageNextButton);
  }

  if (currentIdx < postImagesCnt - 1 && !isVisible(imageNextButton)) {
    addButton(imageNextButton);
  }
  if (0 < currentIdx && !isVisible(imagePrevButton)) {
    addButton(imagePrevButton);
  }
}

function removeButton(imageButton) {
  imageButton.classList.add("unvisible-button");
}
function addButton(imageButton) {
  imageButton.classList.remove("unvisible-button");
}
function isVisible(imageButton) {
  imageButton.classList.contains("unvisible-button");
}

imagePrevButton.addEventListener("click", slide);
imageNextButton.addEventListener("click", slide);

window.onload = function() {
  if (postImagesCnt === 1) {
    removeButton(imageNextButton);
  }
}