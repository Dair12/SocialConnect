let currentPage = 0;
const pageSize = 25;
let loading = false;
let feedEnd = false;

document.addEventListener('click', function (e) {
    if (e.target.closest('.like-btn')) {
        const btn = e.target.closest('.like-btn');
        const postId = btn.getAttribute('data-post-id');
        const heart = btn.querySelector('.like-heart');
        const liked = btn.classList.contains('liked');
        const likeCountSpan = document.getElementById('like-count-' + postId);

        btn.disabled = true;

        fetch(`/api/likes/${liked ? "unlike" : "like"}?postId=${postId}`, {
            method: 'POST',
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        })
        .then(r => r.json())
        .then(data => {
            btn.classList.toggle('liked');
            heart.textContent = btn.classList.contains('liked') ? "❤️" : "🤍";
            if (data.likes !== undefined) {
                likeCountSpan.textContent = data.likes;
            } else {
                // fallback (если бек вернул просто строку)
                likeCountSpan.textContent = parseInt(likeCountSpan.textContent, 10) + (btn.classList.contains('liked') ? 1 : -1);
            }
        })
        .catch(() => alert("Ошибка. Перезайдите в аккаунт."))
        .finally(() => btn.disabled = false);
    }
});

function loadPosts() {
    if (loading || feedEnd) return;
    loading = true;
    document.getElementById('loading').style.display = "block";

    fetch(`/api/posts/feed?page=${currentPage}&size=${pageSize}`)
        .then(r => r.json())
        .then(posts => {
            if (posts.length === 0) {
                feedEnd = true;
                if (currentPage === 0) {
                    document.getElementById('post-feed').innerHTML = "<p>Нет постов :(</p>";
                }
            } else {
                for (let post of posts) {
                    document.getElementById('post-feed').appendChild(renderPost(post));
                }
                currentPage++;
            }
        })
        .finally(() => {
            loading = false;
            document.getElementById('loading').style.display = "none";
        });
}

function renderPost(post) {
    const div = document.createElement('div');
    div.className = "post-card";
    const likedClass = post.likedByMe ? "liked" : "";

    div.innerHTML = `
        <div style="font-weight: bold;">${post.username}</div>
        <div style="margin: 5px 0;">${post.content}</div>
        <div style="display: flex; align-items: center; gap: 8px;">
            <button class="like-btn ${likedClass}" data-post-id="${post.id}">
                <span class="like-heart">${post.likedByMe ? "❤️" : "🤍"}</span>
            </button>
            <span class="like-count" id="like-count-${post.id}">${post.likes}</span>
            <span style="font-size:0.9em; color:#888; margin-left:auto;">${new Date(post.createdAt).toLocaleString()}</span>
        </div>
        <hr>
    `;
    return div;
}

// Загрузка первых постов
loadPosts();

// Бесконечная подгрузка при прокрутке
window.addEventListener('scroll', () => {
    if ((window.innerHeight + window.scrollY) >= (document.body.offsetHeight - 100)) {
        loadPosts();
    }
});