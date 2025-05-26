let currentPage = 0;
const pageSize = 25;
let loading = false;
let feedEnd = false;

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
    div.innerHTML = `
        <div style="font-weight: bold;">${post.username}</div>
        <div style="margin: 5px 0;">${post.content}</div>
        <div style="font-size:0.9em; color:#888;">${new Date(post.createdAt).toLocaleString()}</div>
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