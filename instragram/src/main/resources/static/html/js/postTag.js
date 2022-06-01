const postContentTags = document.querySelector(".post-content__tags");
const postTagsInput = document.querySelector(".post-tags__input");

document.querySelector(".post-tags__input").addEventListener("keydown", function(event) {
  event.preventDefault();
  if (event.keyCode == 13 || event.keyCode == 32) {
    if (postTagsInput.value === "") {
      return;
    }
    postContentTags.appendChild(createTag());
    postTagsInput.value = "";
  }
});

function createTag() {
  const tagName = document.createElement("div");
  tagName.classList.add("post-content__tag-name");
  tagName.textContent = "#" + postTagsInput.value;

  const tag = document.createElement("div");
  tag.classList.add("post-content__tag");
  tag.appendChild(tagName);

  tag.addEventListener("click", function() {
    postContentTags.removeChild(tag);
  });

  return tag;
}
