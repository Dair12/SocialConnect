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