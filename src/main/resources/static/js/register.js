document.getElementById('registerForm').addEventListener('submit', async (e) => {
  e.preventDefault();

  const form = e.target;
  const username = form.username.value;
  const email = form.email.value;
  const password = form.password.value;

  const response = await fetch('/api/auth/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ username, email, password })
  });

  if (response.ok) {
    alert('Регистрация успешна! Переход на вход.');
    window.location.href = '/login';
  } else {
    alert('Ошибка регистрации. Возможно, пользователь уже существует.');
  }
});
