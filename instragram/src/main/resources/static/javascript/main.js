const headerSearchInput = document.querySelector(".header-search__input");
const searchResultWrap = document.querySelector(".search-result-wrap");
const mainPostWrap = document.querySelector(".main-post-wrap");
const mainPostMore = document.querySelector(".main-post-more");
const searchResult = document.querySelector(".search-result");
const commentPostImages = document.querySelector(".comment-post-images");
const commentPostImagePrevButton = document.querySelector(".comment-post-images__prev-button");
const commentPostImageNextButton = document.querySelector(".comment-post-images__next-button");

const MAIN_POST_IMAGE_WIDTH = 600;
const COMMENT_POST_IMAGE_WIDTH = 555;
let nextPage = 0;

class SlideController {
    constructor(slideWidth, imageCount, prevButton, nextButton, images) {
        this.index = 0;
        this.slideWidth = slideWidth;
        this.imageCount = imageCount;
        this.prevButton = prevButton;
        this.nextButton = nextButton;
        this.images = images;

        this.controlButtons();
    }

    slide(selectedButton) {
        const increment = (selectedButton === this.prevButton) ? -1 : 1;

        this.index += increment;
        this.images.style.left = -(this.index * this.slideWidth) + "px";
        this.controlButtons();
    }

    controlButtons() {
        if (this.isFirstPage()) {
            this.hidePrevButton();
        } else {
            this.showPrevButton();
        }

        if (this.isLastPage()) {
            this.hideNextButton();
        } else {
            this.showNextButton();
        }
    }

    isFirstPage() {
        return this.index <= 0;
    }

    isLastPage() {
        return this.index >= this.imageCount - 1;
    }

    hidePrevButton() {
        this.prevButton.style.display = "none";
    }

    hideNextButton() {
        this.nextButton.style.display = "none";
    }

    showPrevButton() {
        this.prevButton.style.display = "flex";
    }

    showNextButton() {
        this.nextButton.style.display = "flex";
    }

    clear(imageCount) {
        this.index = 0;
        this.imageCount = imageCount;
        this.images.style.left = "0px";
        this.controlButtons();
    }
}

function getMembersAjax(nickname) {
    const request = new XMLHttpRequest();

    if (!request) {
        alert("XMLHTTP 인스턴스 생성 불가");
        return false;
    }

    request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
            if (request.status === 200) {
                setSearchResult(request.response);
            } else {
                alert("request에 문제가 있습니다.")
            }
        }
    }

    request.open("get", "/members?nickname=" + nickname);
    request.responseType = "json";
    request.send();
}

function setSearchResult(response) {
    const members = response.members;

    let searchResultHtml = "";
    for (let i = 0; i < members.length; i++) {
        searchResultHtml += getReplacedSearchResultTemplate(members, i);
    }
    searchResult.innerHTML = searchResultHtml;
}

function getReplacedSearchResultTemplate(members, i) {
    return document.querySelector("#template__search-result").innerHTML
        .replace("{imagePath}", members[i].imagePath)
        .replace("{nickname}", members[i].nickname);
}

function getPostsAjax() {
    const request = new XMLHttpRequest();

    if (!request) {
        alert("XMLHTTP 인스턴스 생성 불가");
        return false;
    }

    request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
            if (request.status === 200) {
                setMainPost(request.response);
                nextPage = getNextPage(request.response.sliceInfo.page);
            } else {
                alert("request에 문제가 있습니다.")
            }
        }
    }

    request.open("get", "/posts?page=" + nextPage);
    request.responseType = "json";
    request.send();
}

function getNextPage(currentPage) {
    return currentPage + 1;
}

function setMainPost(response) {
    const posts = response.data;
    const sliceInfo = response.sliceInfo;

    for (let i = 0; i < posts.length; i++) {
        mainPostWrap.insertAdjacentHTML("beforeend", getReplacedMainPostTemplate(posts, i));

        for (let j = 0; j < posts[i].filePaths.length; j++) {
            document.querySelector(`.main-post-${posts[i].postId} .main-post-images`)
                .insertAdjacentHTML("beforeend", getMainPostImagesTemplate(posts, i, j));
        }
        addMainPostEvent(posts, i);

        if (posts[i].commentCount === 0) {
            removeMainPostComments(posts, i);
        }

        if (!sliceInfo.hasNext) {
            removeMoreButton();
        }
    }
}

function removeMainPostComments(posts, i) {
    document.querySelector(`.main-post-${posts[i].postId} .main-post-comments-count`).style.display = "none";
}

function getReplacedMainPostTemplate(posts, i) {
    return document.querySelector("#template__main-post").innerHTML
        .replaceAll("{postId}", posts[i].postId)
        .replace("{member.memberId}", posts[i].member.memberId)
        .replaceAll("{member.nickname}", posts[i].member.nickname)
        .replace("{member.imagePath}", posts[i].member.imagePath)
        .replace("{text}", posts[i].text)
        .replace("{commentCount}", posts[i].commentCount)
        .replace("{createdDate}", posts[i].createdDate);
}

function getMainPostImagesTemplate(posts, i, j) {
    return document.querySelector("#template__main-post-images").innerHTML
        .replace("{filePath}", posts[i].filePaths[j]);
}

function removeMoreButton() {
    mainPostMore.style.display = "none";
    mainPostMore.removeEventListener("click", getPostsAjax);
}

function addMainPostEvent(posts, i) {
    const mainPostImagesCount = posts[i].filePaths.length;
    const mainPostImagePrevButton = document.querySelector(`.main-post-${posts[i].postId} .post-images__prev-button`);
    const mainPostImageNextButton = document.querySelector(`.main-post-${posts[i].postId} .post-images__next-button`);
    const mainPostImages = document.querySelector(`.main-post-${posts[i].postId} .main-post-images`);
    const mainPostImageSlideController =
        new SlideController(MAIN_POST_IMAGE_WIDTH, mainPostImagesCount, mainPostImagePrevButton, mainPostImageNextButton, mainPostImages);
    const showComment = document.querySelector(`.main-post-${posts[i].postId} .show-comment`);

    mainPostImagePrevButton.addEventListener("click", (event) => {
        mainPostImageSlideController.slide(event.currentTarget);
    });

    mainPostImageNextButton.addEventListener("click", (event) => {
        mainPostImageSlideController.slide(event.currentTarget);
    });

    showComment.addEventListener("click", () => {
        comment.style.display = "block";
        mainBody.style.overflow = "hidden";

        setCommentPage(posts[i]);
        getCommentsAjaxWithPost(posts[i].postId, 0);
    });
}

function getCommentsAjaxWithPost(postId, page) {
    const request = new XMLHttpRequest();

    if (!request) {
        alert("XMLHTTP 인스턴스 생성 불가");
        return false;
    }

    request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
            if (request.status === 200) {
                console.log(request.response);
                // setCommentPage(request.response, post);
            } else {
                alert("request에 문제가 있습니다.")
            }
        }
    }

    request.open("get", `/comments?postId=${postId}&page=${page}`);
    request.responseType = "json";
    request.send();
}

function setCommentPage(post) {
    console.log(post);
    commentPostImages.innerHTML = getReplacedCommentPostImageTemplate(post.filePaths);
    commentPostSlideController.clear(post.filePaths.length);

}

function getReplacedCommentPostImageTemplate(filePaths) {
    let html = "";
    for (let i = 0; i < filePaths.length; i++) {
        html += document.querySelector("#template__comment-post-image").innerHTML
            .replace("{filePath}", filePaths[i]);
    }
    return html;
}

function addMainPageEvent() {
    mainPostMore.addEventListener("click", getPostsAjax);

    headerSearchInput.addEventListener("focus", () => {
        searchResultWrap.style.display = "block";
    });

    headerSearchInput.addEventListener("blur", () => {
        searchResultWrap.style.display = "none";
    });

    headerSearchInput.addEventListener("input", () => {
        const nickname = document.querySelector(".header-search__input").value;
        if (isBlank(nickname)) {
            searchResult.innerHTML = "";
            return;
        }
        getMembersAjax(nickname);
    });
}

function addCommentPageEvent() {
    commentPostImagePrevButton.addEventListener("click", (event) => {
        commentPostSlideController.slide(event.currentTarget);
    });

    commentPostImageNextButton.addEventListener("click", (event) => {
        commentPostSlideController.slide(event.currentTarget);
    });

    commentCancel.addEventListener("click", () => {
        comment.style.display = "none";
        mainBody.style.overflow = "auto";
        commentPostImages.style.left = "0px";
    });
}

function isBlank(nickname) {
    return !nickname.trim();
}

const commentPostSlideController =
    new SlideController(COMMENT_POST_IMAGE_WIDTH, 0, commentPostImagePrevButton, commentPostImageNextButton, commentPostImages);

getPostsAjax();
addMainPageEvent();
