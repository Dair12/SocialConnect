document.addEventListener('DOMContentLoaded', function () {
    const bell = document.getElementById('bell-icon');
    const dropdown = document.getElementById('notifications-dropdown');
    if (!bell || !dropdown) return;

    bell.addEventListener('click', function (e) {
        e.stopPropagation();
        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
    });

    // Закрыть по клику вне уведомлений
    document.addEventListener('click', function (e) {
        if (!dropdown.contains(e.target) && e.target !== bell) {
            dropdown.style.display = 'none';
        }
    });
});