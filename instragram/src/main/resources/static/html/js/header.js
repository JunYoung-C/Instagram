const headerSearchResult = document.querySelector(".header-search__result");
const container = document.querySelector("#container");
const navOptions = document.querySelector(".nav__options");

document.querySelector(".header-search__input").addEventListener("focus", () => {
  headerSearchResult.style.display = "block";
});

container.addEventListener("click", () => {
  headerSearchResult.style.display = "none";
});

document.querySelector(".nav--account").addEventListener("click", () => {
  navOptions.style.display = "block";
});

container.addEventListener("click", () => {
  navOptions.style.display = "none";
});



