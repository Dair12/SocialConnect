document.addEventListener('DOMContentLoaded', function () {
    const items = document.querySelectorAll('.bottom-menu .menu-item');
    // 1. Сначала снимаем все .active
    items.forEach(i => i.classList.remove('active'));

    // 2. Определяем текущий путь
    const path = window.location.pathname;

    // 3. По path ставим active на нужный пункт
    if (path.startsWith('/profile')) {
        document.querySelector('.bottom-menu .menu-item.profile')?.classList.add('active');
    } else if (path === '/' || path.startsWith('/home')) {
        items[0]?.classList.add('active');
    }
    // Можешь добавить else if для других пунктов меню, если они появятся

    // 4. Сохраняем обработчики по клику (оставь как было)
    items.forEach(item => {
        item.addEventListener('click', function () {
            items.forEach(i => i.classList.remove('active'));
            this.classList.add('active');
        });
    });

    // 5. Клик по профилю
    const profileBtn = document.querySelector('.bottom-menu .menu-item.profile');
    if (profileBtn) {
        profileBtn.addEventListener('click', function () {
            window.location.href = '/profile';
        });
    }

    // 6. Клик по главной (если нужен)
    const homeBtn = document.querySelector('.bottom-menu .menu-item:not(.profile)');
    if (homeBtn) {
        homeBtn.addEventListener('click', function () {
            window.location.href = '/';
        });
    }
});