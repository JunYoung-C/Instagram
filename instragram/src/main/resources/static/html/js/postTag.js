const postContentTags = document.querySelector(".post-content__tags");
const postTagsInput = document.querySelector(".post-tags__input");

function addTagEvent(event) {
  if (event.keyCode == 13 || event.keyCode == 32) {
    event.preventDefault();
    if (postTagsInput.value === "") {
      return;
    }
    postContentTags.appendChild(createTag());
    postTagsInput.value = "";
  }
}

function createTag() {
  const tagName = document.createElement("div");
  tagName.classList.add("post-content__tag-name");
  tagName.textContent = "#" + postTagsInput.value;

  const tag = document.createElement("div");
  tag.classList.add("post-content__tag");
  tag.appendChild(tagName);
  tag.appendChild(createHiddenInput());

  tag.addEventListener("click", function() {
    postContentTags.removeChild(tag);
  });

  return tag;
}

function createHiddenInput() {
  const input = document.createElement("input");
  input.setAttribute("type", "hidden");
  input.setAttribute("id", "tag");
  input.setAttribute("name", "tag");
  input.value = postTagsInput.value;

  return input;
}

document.querySelector(".post-tags__input").addEventListener("keydown", addTagEvent);
