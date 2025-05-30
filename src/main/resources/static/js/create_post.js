document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('create-post-form').onsubmit = async function (e) {
        e.preventDefault();
        let content = document.getElementById('post-content').value.trim();
        if (!content) return;

        try {
            let response = await fetch('/api/posts/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ content })
            });
            if (!response.ok) throw new Error('Ошибка публикации');
            // После создания поста можно перейти на главную:
            window.location.href = '/';
        } catch (err) {
            let errBlock = document.getElementById('create-post-error');
            errBlock.innerText = 'Не удалось создать пост';
            errBlock.style.display = 'block';
        }
    };
});