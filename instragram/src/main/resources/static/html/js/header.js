const headerSearchResult = document.querySelector(".header-search__result");
const container = document.querySelector("#container");
const navOptions = document.querySelector(".nav__options");
const headerSearchInput = document.querySelector(".header-search__input");

headerSearchInput.addEventListener("focus", () => {
  headerSearchResult.style.display = "block";
});

document.querySelector("body").addEventListener("click", (event) => {
  if (event.target.closest(".header-search__input")) {
    return;
  }

  if (!event.target.closest(".search-result")) {
    headerSearchResult.style.display = "none";
  }
});

document.querySelector(".nav--account").addEventListener("click", () => {
  navOptions.style.display = "block";
});

document.querySelector("body").addEventListener("click", (event) => {
  if (event.target.closest(".nav--account")) {
    return;
  }

  if (!event.target.closest(".nav-options")) {
    navOptions.style.display = "none";
  }
});



