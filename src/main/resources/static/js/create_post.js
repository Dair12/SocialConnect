function removeImage(n) {
    const input = document.getElementById('image' + n);
    input.value = "";
    document.getElementById('preview' + n).style.display = "none";
    document.getElementById('img-preview' + n).src = "";
}

document.addEventListener('DOMContentLoaded', function () {
    // preview для каждой картинки
    [1,2].forEach(function (n) {
        const input = document.getElementById('image' + n);
        input.addEventListener('change', function (event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    document.getElementById('img-preview' + n).src = e.target.result;
                    document.getElementById('preview' + n).style.display = "block";
                };
                reader.readAsDataURL(file);
            } else {
                removeImage(n);
            }
        });
    });

    document.getElementById('create-post-form').onsubmit = async function (e) {
        e.preventDefault();
        let content = document.getElementById('post-content').value.trim();
        let image1 = document.getElementById('image1').files[0];
        let image2 = document.getElementById('image2').files[0];

        function toBase64(file) {
            return new Promise((resolve, reject) => {
                if (!file) return resolve(null);
                const reader = new FileReader();
                reader.onload = () => resolve(reader.result.split(',')[1]);
                reader.onerror = reject;
                reader.readAsDataURL(file);
            });
        }

        let [imageBase641, imageBase642] = await Promise.all([
            toBase64(image1),
            toBase64(image2)
        ]);

        try {
            let response = await fetch('/api/posts/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    content,
                    imageBase641,
                    imageBase642
                })
            });
            if (!response.ok) throw new Error('Ошибка публикации');
            window.location.href = '/';
        } catch (err) {
            let errBlock = document.getElementById('create-post-error');
            errBlock.innerText = 'Не удалось создать пост';
            errBlock.style.display = 'block';
        }
    };
});