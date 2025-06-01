document.addEventListener('DOMContentLoaded', function () {
    const tabs = document.querySelectorAll('.tab-btn');
    const contents = document.querySelectorAll('.tab-content');

    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            tabs.forEach(btn => btn.classList.remove('active'));
            tab.classList.add('active');
            contents.forEach(cont => cont.style.display = 'none');
            document.getElementById('tab-' + tab.dataset.tab).style.display = 'block';
        });
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const btn = document.getElementById('subscribe-btn');
    const followersCountSpan = document.getElementById('followers-count');
    if (!btn) return;

    btn.addEventListener('click', function() {
        const userId = btn.getAttribute('data-user-id');
        const isFollowing = btn.textContent === 'Отписаться';
        fetch(`/api/subscription/${isFollowing ? 'unsubscribe' : 'subscribe'}?userId=${userId}`, {
            method: 'POST',
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        })
        .then(r => r.text())
        .then(msg => {
            // Меняем текст кнопки
            btn.textContent = isFollowing ? 'Подписаться' : 'Отписаться';

            // Парсим текущее количество (например, "Подписчиков: 12")
            let cur = parseInt(followersCountSpan.textContent.replace(/\D/g, ""));
            if (isNaN(cur)) cur = 0;
            // Увеличиваем или уменьшаем
            cur += isFollowing ? -1 : 1;
            if (cur < 0) cur = 0;
            followersCountSpan.textContent = 'Подписчиков: ' + cur;
        })
        .catch(() => alert("Ошибка при подписке/отписке"));
    });
});

document.addEventListener('DOMContentLoaded', function() {
    enableImageZoom();

    // Если у тебя есть переключение вкладок:
    const tabs = document.querySelectorAll('.tab-btn');
    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            setTimeout(enableImageZoom, 20); // Дождаться отрисовки DOM
        });
    });
});