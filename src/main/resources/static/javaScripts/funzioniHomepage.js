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

// Funzione per aprire il popup e caricare i dettagli della transazione
// Funzione per aprire il popup e caricare i dettagli della transazione
function openPopup(button) {
    var transactionId = button.getAttribute('data-transaction-id');

    // Recupera gli elementi del popup
    var costoTransazioneElement = document.querySelector('.costoTransazione');
    var nomeBeneficiarioElement = document.querySelector('.nomeBeneficiario');

    // Esegui una chiamata fetch per ottenere i dettagli della transazione
    fetch('/api/transazioni/' + transactionId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore durante il recupero dei dettagli della transazione');
            }
            return response.json();
        })
        .then(data => {
            // Aggiorna il contenuto del popup con i dettagli ottenuti
            costoTransazioneElement.textContent = 'Costo: ' + data.costoTransazione;
            nomeBeneficiarioElement.textContent = 'Beneficiario: ' + data.nomeBeneficiario;

            // Mostra il popup
            document.getElementById('popup').style.display = 'block';
        })
        .catch(error => console.error(error));
}

// Funzione per chiudere il popup
function closePopupTrans() {
    document.getElementById('popup').style.display = 'none';
}




/*
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
*/




function eliminaNotifica(element) {
    var notificaId = element.getAttribute('data-notificaId');

    eliminaNotificaAjax(notificaId);

    element.parentNode.remove();
}

function eliminaNotificaAjax(notificaId) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/eliminaNotifica', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
        }
    };

    xhr.send(JSON.stringify({ notificaId: notificaId }));
}





