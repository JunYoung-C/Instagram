const imagePrevButton = document.querySelector(".image-prev-button");
const imageNextButton = document.querySelector(".image-next-button");
const postImagesCnt = document.querySelectorAll(".post-images__list").length;
const postImages = document.querySelector(".post-images");
const slideWitdh = 600;
let index = 0;

function slide (event, slideWitdh) {
  const target = event.currentTarget;
  const increment = (target === imagePrevButton) ? -1 : 1;

  index += increment;
  postImages.style.left = -(index * slideWitdh) + "px";

  controlButtons(index, imagePrevButton, imageNextButton);
}

function controlButtons(index, imagePrevButton, imageNextButton) {
  if (index <= 0) {
    removeButton(imagePrevButton);
  }
  if (index >= postImagesCnt - 1) {
    removeButton(imageNextButton);
  }

  if (index < postImagesCnt - 1 && !isVisible(imageNextButton)) {
    addButton(imageNextButton);
  }
  if (0 < index && !isVisible(imagePrevButton)) {
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

imagePrevButton.addEventListener("click", (event) => {
  slide(event, slideWitdh);
});

imageNextButton.addEventListener("click", (event) => {
  slide(event, slideWitdh);
});

window.onload = function() {
  if (postImagesCnt === 1) {
    removeButton(imageNextButton);
  }
}
