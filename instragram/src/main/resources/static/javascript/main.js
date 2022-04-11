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
let nextPostPage = 0;
let selectedPostId;
let nextCommentPage = 0;

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
                nextPostPage = getNextPage(request.response.sliceInfo.page);
            } else {
                alert("request에 문제가 있습니다.")
            }
        }
    }

    request.open("get", "/posts?page=" + nextPostPage);
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
    }

    if (!sliceInfo.hasNext) {
        removePostMoreButton();
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

function removePostMoreButton() {
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
    });
}

function setCommentPage(post) {
    commentPostSlideController.clear(post.filePaths.length);
    setCommentPageWithOutComments(post);
    getCommentsAjax(post.postId, 0);
}

function setCommentPageWithOutComments(post) {
    selectedPostId = post.postId;
    commentPostImages.innerHTML = getReplacedCommentPostImageTemplate(post.filePaths);
    document.querySelector(".comment-post-header__info").innerHTML =
        getReplacedCommentPostProfileTemplate(post);
    document.querySelector(".not-comment").innerHTML =
        getReplacedCommentPostTextTemplate(post);
    document.querySelector(".comment-post-time-wrap").innerHTML =
        getReplacedCommentPostTimeTemplate(post);
    document.querySelector(".post-comment__hidden-inputs").innerHTML =
        document.querySelector("#template__comment-post__hidden-inputs").innerHTML
            .replace("{postId}", post.postId)
            .replace("{memberId}", post.member.memberId);
}

function getReplacedCommentPostImageTemplate(filePaths) {
    let html = "";
    for (let i = 0; i < filePaths.length; i++) {
        html += document.querySelector("#template__comment-post-image").innerHTML
            .replace("{filePath}", filePaths[i]);
    }
    return html;
}

function getReplacedCommentPostProfileTemplate(post) {
    return document.querySelector("#template__comment-post-profile").innerHTML
        .replace("{imagePath}", post.member.imagePath)
        .replace("{nickname}", post.member.nickname);
}

function getReplacedCommentPostTextTemplate(post) {
    return document.querySelector("#template__comment-post-text").innerHTML
        .replace("{imagePath}", post.member.imagePath)
        .replace("{nickname}", post.member.nickname)
        .replace("{text}", post.text)
        .replace("{createdDate}", post.createdDate);

}

function getReplacedCommentPostTimeTemplate(post) {
    return document.querySelector("#template__comment-post-createdDate").innerHTML
        .replace("{createdDate}", post.createdDate);
}

function getCommentsAjax(postId, page) {
    const request = new XMLHttpRequest();

    if (!request) {
        alert("XMLHTTP 인스턴스 생성 불가");
        return false;
    }

    request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
            if (request.status === 200) {
                console.log(request.response);
                setComments(request.response);
            } else {
                alert("request에 문제가 있습니다.")
            }
        }
    }

    request.open("get", `/comments?postId=${postId}&page=${page}`);
    request.responseType = "json";
    request.send();
}

function setComments(response) {
    const comments = response.data;
    const sliceInfo = response.sliceInfo;

    if (sliceInfo.page === 0) {
        document.querySelector(".comment-wrap").innerHTML = "";
    }
    for (let i = 0; i < comments.length; i++) {
        document.querySelector(".comment-wrap")
            .insertAdjacentHTML("beforeend", getReplacedCommentTemplate(comments, i));

        if (comments[i].replyCount === 0) {
            removeCommentDivider(comments, i);
        }
    }

    if (sliceInfo.hasNext) {
        document.querySelector(".more-comment").style.display = "flex";
        nextCommentPage = sliceInfo.page + 1;
    } else {
        document.querySelector(".more-comment").style.display = "none";
    }
    // 답글 보기 누르면 답글 가져오기
}

function getReplacedCommentTemplate(comments, i) {
    return document.querySelector("#template__comment").innerHTML
        .replace("{commentId}", comments[i].commentId)
        .replace("{member.imagePath}", comments[i].member.imagePath)
        .replace("{member.nickname}", comments[i].member.nickname)
        .replace("{text}", comments[i].text)
        .replace("{createdDate}", comments[i].createdDate)
        .replace("{replyCount}", comments[i].replyCount);
}

function removeCommentDivider(comments, i) {
    document.querySelector(`.comment-${comments[i].commentId} .comment-divider`).style.display = "none";
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

    document.querySelector(".more-comment__button").addEventListener("click", () => {
        getCommentsAjax(selectedPostId, nextCommentPage);
    })
}

function isBlank(nickname) {
    return !nickname.trim();
}

const commentPostSlideController =
    new SlideController(COMMENT_POST_IMAGE_WIDTH, 0, commentPostImagePrevButton, commentPostImageNextButton, commentPostImages);

getPostsAjax();
addMainPageEvent();
