# Patika-Dev-FT-Practicum-Final-Case
Backend

## Araçlar
- Maven
- Jdk 11
- Eclipse
- PostgreSQL

## Mimari
- Service-Registry - Ayağa kaldırılan her instance buraya kaydolur. 
    Belli aralıklarla instance ın ayakta olup olmadığını kontrol eder.
- Cloud-config-server - Harici yapılandırma ayarlarını yönetir.
- Api-gateway - Clientten gelen istekleri path bilgisine göre mikroservislere yönlendirir.
- Account-service - Kullanıcı ve hesap bilgileri ile ilgili işlemlerin yönetildiği servis.
- Exchange-service - Alım satım işlemlerinin yönetildiği servis.

## Run
- Project > Run As> Maven Build  ``spring-boot:run``
- Servislerin belli bir sıraya göre ayağa kaldırılması önemlidir. Sıralama şu şekildedir.
- Service-registry
- Cloud-config-server
- Api-gateway
- Account-service yada Exchange-service
