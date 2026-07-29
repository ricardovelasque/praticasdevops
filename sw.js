const CACHE_NAME = 'task-pwa-v2';
const FILES_TO_CACHE = [
  "index.html",
  "sobre.html",
  "style.css",
  "manifest.json",
  "icons/icon-192.png",
  "icons/icon-512.png",
  "icons/logo.jpg"
];

// Instalação: Salva os arquivos essenciais no cache
self.addEventListener('install', (e) => {
  e.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      // Força o cache do index mesmo se a URL terminar apenas com a barra "/" do repositório
      cache.add(''); 
      return cache.addAll(ASSETS);
    })
  );
});

// Ativação: Limpa caches antigos
self.addEventListener('activate', (e) => {
  e.waitUntil(
    caches.keys().then((keys) => {
      return Promise.all(
        keys.map((key) => {
          if (key !== CACHE_NAME) {
            return caches.delete(key);
          }
        })
      );
    })
  );
});

// Estratégia de Cache: Cache First, depois Rede (Tratando as rotas do GitHub Pages)
self.addEventListener('fetch', (e) => {
  e.respondWith(
    // O {ignoreSearch: true} ignora parâmetros extras na URL que o GitHub Pages põe às vezes
    caches.match(e.request, {ignoreSearch: true}).then((cachedResponse) => {
      if (cachedResponse) {
        return cachedResponse;
      }
      return fetch(e.request);
    })
  );
});