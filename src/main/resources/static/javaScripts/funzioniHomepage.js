function modifyLinks() {
    var urlParams = new URLSearchParams(window.location.search);
    var idSession = urlParams.get("IDSession");
    var links = document.querySelectorAll('[id^="redirect"]');
    links.forEach(function(link) {
        var linkHref = link.getAttribute("href");
        link.setAttribute("href", linkHref + '?IDSession=' + idSession);
    });
}

document.addEventListener("DOMContentLoaded", modifyLinks);


function openPopup() {
    document.getElementById('popup').style.display = 'block';
}

function closePopupTrans() {
    document.getElementById('popup').style.display = 'none';
}

function toggleNotifiche() {
    var notificheMenu = document.querySelector('.notificheMenu');
    notificheMenu.classList.toggle('show');

    // Nascondi il badge quando clicchi sull'icona e ci sono notifiche
    var badge = document.querySelector('.badge');
    if (badge.style.display !== 'none') {
        badge.style.display = 'none';
    }
}

    // Chiudi il menu delle notifiche se clicchi al di fuori
    window.onclick = function (event) {
        if (!event.target.matches('.notificaImage')) {
            var notificheMenu = document.querySelector('.notificheMenu');
            if (notificheMenu.classList.contains('show')) {
                notificheMenu.classList.remove('show');
            }
        }
};

function eliminaNotifica(element) {
    // Recupera l'ID della notifica dalla proprietà data-notificaId
    var notificaId = element.getAttribute('data-notificaId');

    eliminaNotificaAjax(notificaId);

    // Rimuovi visivamente la notifica dalla lista
    element.parentNode.remove();
}

function eliminaNotificaAjax(notificaId) {
    // Invia una richiesta AJAX al server
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/eliminaNotifica', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Logica aggiuntiva dopo l'eliminazione (se necessario)
        }
    };

    // Invia l'ID della notifica come parte del corpo della richiesta
    xhr.send(JSON.stringify({ notificaId: notificaId }));
}





