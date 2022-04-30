const mainBody = document.querySelector(".main-body");
const headerSearchInput = document.querySelector(".header-search__input");
const searchResultWrap = document.querySelector(".search-result-wrap");
const mainPostWrap = document.querySelector(".main-post-wrap");
const mainPostMore = document.querySelector(".main-post-more");
const searchResult = document.querySelector(".search-result");
const commentPostImages = document.querySelector(".comment-post-images");
const commentPostImagePrevButton = document.querySelector(".comment-post-images__prev-button");
const commentPostImageNextButton = document.querySelector(".comment-post-images__next-button");
const optionBox = document.querySelector(".option-box");
const comment = document.querySelector("#comment");

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
                alert(request.response.message);
            }
        }
    }

    request.open("get", `/members?nickname=${nickname}&page=0&size=50`);
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
        .replace("{nickname}", members[i].nickname)
        .replace("{name}", members[i].name);
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
                nextPostPage = request.response.sliceInfo.page + 1;
            } else {
                alert(request.response.message);
            }
        }
    }

    request.open("get", `/posts?page=${nextPostPage}&size=10`);
    request.responseType = "json";
    request.send();
}

function setMainPost(response) {
    const posts = response.data;
    const sliceInfo = response.sliceInfo;

    for (let i = 0; i < posts.length; i++) {
        mainPostWrap.insertAdjacentHTML("beforeend", getReplacedMainPostTemplate(posts[i]));

        for (let j = 0; j < posts[i].filePaths.length; j++) {
            document.querySelector(`.main-post-${posts[i].postId} .main-post-images`)
                .insertAdjacentHTML("beforeend", getMainPostImagesTemplate(posts[i].filePaths[j]));
        }
        addMainPostEvent(posts[i]);

        if (posts[i].commentCount === 0) {
            removeMainPostComments(posts[i]);
        }
    }

    if (!sliceInfo.hasNext) {
        removePostMoreButton();
    }
}

function removeMainPostComments(post) {
    document.querySelector(`.main-post-${post.postId} .main-post-comments-count`).style.display = "none";
}

function getReplacedMainPostTemplate(post) {
    return document.querySelector("#template__main-post").innerHTML
        .replaceAll("{postId}", post.postId)
        .replaceAll("{member.nickname}", post.member.nickname)
        .replace("{member.imagePath}", post.member.imagePath)
        .replace("{text}", post.text)
        .replace("{commentCount}", post.commentCount)
        .replace("{createdDate}", post.createdDate);
}

function getMainPostImagesTemplate(filePath) {
    return document.querySelector("#template__main-post-images").innerHTML
        .replace("{filePath}", filePath);
}

function removePostMoreButton() {
    mainPostMore.style.display = "none";
    mainPostMore.removeEventListener("click", getPostsAjax);
}

function addMainPostEvent(post) {
    const mainPostImagesCount = post.filePaths.length;
    const mainPostImagePrevButton = document.querySelector(`.main-post-${post.postId} .post-images__prev-button`);
    const mainPostImageNextButton = document.querySelector(`.main-post-${post.postId} .post-images__next-button`);
    const mainPostImages = document.querySelector(`.main-post-${post.postId} .main-post-images`);
    const mainPostImageSlideController =
        new SlideController(MAIN_POST_IMAGE_WIDTH, mainPostImagesCount, mainPostImagePrevButton, mainPostImageNextButton, mainPostImages);
    const showComment = document.querySelector(`.main-post-${post.postId} .show-comment`);
    const mainPostProfileMore = document.querySelector(`.main-post-${post.postId} .main-post-profile__more`);

    mainPostImagePrevButton.addEventListener("click", (event) => {
        mainPostImageSlideController.slide(event.currentTarget);
    });

    mainPostImageNextButton.addEventListener("click", (event) => {
        mainPostImageSlideController.slide(event.currentTarget);
    });

    showComment.addEventListener("click", () => {
        comment.style.display = "block";
        mainBody.style.overflow = "hidden";
        document.querySelector(".comment-post-header__more .show-option-box__post")
            .setAttribute("postId", post.postId);
        setCommentPage(post);
    });

    mainPostProfileMore.addEventListener("click", () => {

    })
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
            .replace("{postId}", post.postId);
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
                setComments(request.response);
            } else {
                alert(request.response.message);
            }
        }
    }

    request.open("get", `/comments?postId=${postId}&page=${page}&size=20`);
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
            .insertAdjacentHTML("beforeend", getReplacedCommentTemplate(comments[i]));

        toggleCommentDivider(comments[i]);
    }

    toggleMoreCommentButton(sliceInfo);
}

function getReplacedCommentTemplate(comment) {
    return document.querySelector("#template__comment").innerHTML
        .replaceAll("{commentId}", comment.commentId)
        .replace("{member.imagePath}", comment.member.imagePath)
        .replace("{member.nickname}", comment.member.nickname)
        .replace("{text}", comment.text)
        .replace("{createdDate}", comment.createdDate)
        .replace("{replyCount}", comment.replyCount);
}

function toggleCommentDivider(comment) {
    const CommentDivider = document.querySelector(`.comment-${comment.commentId} .comment-divider`);
    if (comment.replyCount === 0) {
        CommentDivider.style.display = "none";
    } else {
        addClickEvent(CommentDivider, comment);
    }
}

function addClickEvent(CommentDivider, comment) {
    CommentDivider.addEventListener("click", () => {
        const hideReplies =
            document.querySelector(`.comment-${comment.commentId} .comment-divider__hide-replies`);
        const showReplies =
            document.querySelector(`.comment-${comment.commentId} .comment-divider__show-replies`);
        const replies = document.querySelector(`.comment-${comment.commentId} .replies`);

        if (showReplies.style.display !== "none") {
            const nextPage = showReplies.getAttribute("nextPage");
            if (replies.childElementCount !== 0 && showReplies.getAttribute("hasNext") === "false") {
                showReplies.style.display = "none";
                hideReplies.style.display = "";
                replies.style.display = "block";
            } else {
                getRepliesAjax(comment.commentId, nextPage ? nextPage : 0);
            }
        } else { // 답긇 숨기기로 되어 있는 경우
            showReplies.style.display = "";
            hideReplies.style.display = "none";
            replies.style.display = "none";
        }
    })
}

function toggleMoreCommentButton(sliceInfo) {
    if (sliceInfo.hasNext) {
        document.querySelector(".more-comment").style.display = "flex";
        nextCommentPage = sliceInfo.page + 1;
    } else {
        document.querySelector(".more-comment").style.display = "none";
    }
}

function getRepliesAjax(commentId, page) {
    const request = new XMLHttpRequest();

    if (!request) {
        alert("XMLHTTP 인스턴스 생성 불가");
        return false;
    }

    request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
            if (request.status === 200) {
                setReplies(request.response, commentId);
            } else {
                alert(request.response.message);
            }
        }
    }

    request.open("get", `/replies?commentId=${commentId}&page=${page}&size=20`);
    request.responseType = "json";
    request.send();
}

function setReplies(response, commentId) {
    const replies = response.data;
    const sliceInfo = response.sliceInfo;

    for (let i = 0; i < replies.length; i++) {
        document.querySelector(`.comment-${commentId} .replies`)
            .insertAdjacentHTML("beforeend", getReplacedReplyTemplate(replies[i]));
    }

    const hideReplies = document.querySelector(`.comment-${commentId} .comment-divider__hide-replies`);
    const showReplies = document.querySelector(`.comment-${commentId} .comment-divider__show-replies`);

    if (sliceInfo.hasNext) {
        showReplies.setAttribute("hasNext", "true");
        showReplies.setAttribute("nextPage", sliceInfo.page + 1);
        document.querySelector(`.comment-${commentId} .comment-divider__reply-count`).innerText -= replies.length;
    } else {
        document.querySelector(`.comment-${commentId} .comment-divider__show-replies`).innerText = "답글 모두 보기"
        showReplies.style.display = "none";
        hideReplies.style.display = "";
        showReplies.setAttribute("hasNext", "false");
    }
}

function getReplacedReplyTemplate(reply) {
    return document.querySelector("#template__reply").innerHTML
        .replaceAll("{replyId}", reply.replyId)
        .replace("{member.imagePath}", reply.member.imagePath)
        .replace("{member.nickname}", reply.member.nickname)
        .replace("{text}", reply.text)
        .replace("{createdDate}", reply.createdDate);
}

function addMainPageEvent() {
    const optionBoxBodyButtons = document.querySelector(".option-box-body__buttons");

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

    mainBody.addEventListener("click", (event) => {
        const target = event.target;

        if (target.classList.contains("show-option-box__post")) {
            optionBox.style.display = "block";
            document.querySelector(".option-box-body__modify").style.display = "";
            optionBoxBodyButtons.setAttribute("num", target.getAttribute("postId"));
            optionBoxBodyButtons.setAttribute("kinds", "posts");
        } else if (target.classList.contains("show-option-box__comment")) {
            optionBox.style.display = "block";
            document.querySelector(".option-box-body__modify").style.display = "none";
            optionBoxBodyButtons.setAttribute("num", target.getAttribute("commentId"));
            optionBoxBodyButtons.setAttribute("kinds", "comments");
        } else if (target.classList.contains("show-option-box__reply")) {
            optionBox.style.display = "block";
            document.querySelector(".option-box-body__modify").style.display = "none";
            optionBoxBodyButtons.setAttribute("num", target.getAttribute("replyId"));
            optionBoxBodyButtons.setAttribute("kinds", "replies");
        }
    });

    document.querySelector(".option-box-body__cancel").addEventListener("click", () => {
        optionBox.style.display = "none";
    });

    document.querySelector(".option-box-body__modify").addEventListener("click", () => {
        getPostAjax(optionBoxBodyButtons.getAttribute("num"));
    });

    document.querySelector(".option-box-body__delete").addEventListener("click", () => {
        deleteCommentAjax(optionBoxBodyButtons.getAttribute("num"),
            optionBoxBodyButtons.getAttribute("kinds"));
    });
}

function getPostAjax(postId) {
    const request = new XMLHttpRequest();

    if (!request) {
        alert("XMLHTTP 인스턴스 생성 불가");
        return false;
    }

    request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
            if (request.status === 200) {
                optionBox.style.display = "none";
                setUpdatePost(request.response, postId);
                updatePost.style.display = "block";
            } else {
                alert(request.response.message);
            }
        }
    }

    request.open("get", `/posts/${postId}`);
    request.responseType = "json";
    request.send();
}

function deleteCommentAjax(id, kinds) {
    const request = new XMLHttpRequest();

    if (!request) {
        alert("XMLHTTP 인스턴스 생성 불가");
        return false;
    }

    request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
            if (request.status === 200) {
                optionBox.style.display = "";
                removeDeletedNode(kinds, id);
            } else {
                alert(request.response.message);
            }
        }
    }

    request.open("delete", `/${kinds}/${id}`);
    request.responseType = "json";
    request.send();
}

function removeDeletedNode(kinds, id) {
    switch (kinds) {
        case "posts":
            document.querySelector(`.main-post-${id}`).remove();
            comment.style.display = "none";
            mainBody.style.overflow = "auto";
            break;
        case "comments":
            document.querySelector(`.comment-${id}`).remove();
            break;
        case "replies":
            document.querySelector(`.reply-${id}`).remove();
    }
}

function addCommentPageEvent() {
    const commentCancel = document.querySelector(".comment-cancel");
    const moreCommentButton = document.querySelector(".more-comment__button");
    const commentPostCommentText = document.querySelector(".comment-post__comment-text");

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

    moreCommentButton.addEventListener("click", () => {
        getCommentsAjax(selectedPostId, nextCommentPage);
    });

    document.querySelector(".comments").addEventListener("click", (event) => {
        if (event.target.classList.contains("comment_owner__reply")) {
            commentPostCommentText.placeholder = "답글 달기...";
            // form action 답글로 변경
            document.querySelector(".comment-post__comment-form")
                .setAttribute("action", "/replies");
            document.querySelector(".post-comment__comment-id-input").value =
                event.target.getAttribute("commentId");
            document.querySelector(".comment-post__comment-text").focus();
        }
    });

    document.querySelector(".comment-post__add-comment-button").addEventListener("submit", () => {
        commentPostCommentText.placeholder = "댓글 달기...";
        // form action 댓글로 변경
        document.querySelector(".comment-post__comment-form")
            .setAttribute("action", "/comments")
    });

}

function isBlank(nickname) {
    return !nickname.trim();
}

const commentPostSlideController =
    new SlideController(COMMENT_POST_IMAGE_WIDTH, 0, commentPostImagePrevButton, commentPostImageNextButton, commentPostImages);

getPostsAjax();
addMainPageEvent();
addCommentPageEvent();
