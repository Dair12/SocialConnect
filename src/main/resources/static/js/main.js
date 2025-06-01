// Общие скрипты для всех страниц
function enableImageZoom() {
    document.querySelectorAll('.post-img').forEach(img => {
        if (img.dataset.zoomEnabled) return; // чтобы не дублировать
        img.dataset.zoomEnabled = "1";
        img.addEventListener('click', function () {
            const overlay = document.createElement('div');
            overlay.style = `
                position:fixed;top:0;left:0;width:100vw;height:100vh;
                background:rgba(30,40,50,0.86);display:flex;
                align-items:center;justify-content:center;z-index:9999;
            `;
            const bigImg = document.createElement('img');
            bigImg.src = img.src;
            bigImg.style = `
                max-width:92vw;max-height:88vh;border-radius:18px;
                box-shadow:0 4px 32px #000
            `;
            overlay.appendChild(bigImg);
            overlay.onclick = () => overlay.remove();
            document.body.appendChild(overlay);
        });
    });
}