# 🧪 Fake API CRUD Test Automation

Bu proje, [https://api.escuelajs.co/api/v1/products](https://api.escuelajs.co/api/v1/products) endpoints üzerinde CRUD (Create, Read, Update, Delete) işlemlerini test eden otomasyon senaryolarını içerir.  
Testler Java, TestNG ve Rest Assured kütüphaneleri kullanılarak yazılmıştır.

## 🚀 Kurulum

### 1. Projeyi Klonla

### 2. Bağımlılıkları yükle

### 3. Testleri çalıştır

🧪 Test Senaryoları

createProduct: Dinamik bir kategoriye ait ürün oluşturur.
verifyProductCountIncreased: Ürün sayısının arttığını doğrular.
updateProductPrice: Oluşturulan ürünün fiyatını günceller.
deleteProduct: Ürünü siler.
updateDeletedProductExpectError	Silinen ürünü güncellemeye çalışır, 400 veya 404 hatası beklenir.