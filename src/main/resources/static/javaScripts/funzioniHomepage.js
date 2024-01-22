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
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    var transactionId = button.getAttribute('data-transaction-id');
    var transactionTipo = button.getAttribute('data-transaction-tipo');

    url = `/visualizzaTrans?IDSession=${idSession}&idTrans=${transactionId}&tipoTrans=${transactionTipo}`;

    var tipoBol = document.querySelector('.tipoBollettino');
    var labelDest = document.querySelector('.labelCCDest');
    var numCcDest = document.querySelector('.numCcDest');
    var nomeBeneficiario = document.querySelector('.nomeBeneficiario');
    var cognomeBeneficiario = document.querySelector('.cognomeBeneficiario');
    var importo = document.querySelector('.importo');
    var causale = document.querySelector('.causale');
    var labelIban = document.querySelector('.labelIban');
    var ibanDest = document.querySelector('.ibanDest');
    var valuta = document.querySelector('.valuta');
    var paeseDest = document.querySelector('.paeseDest');
    var costo = document.querySelector('.costoTransazione');
    var dataEs = document.querySelector('.data');

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore durante il recupero dei dettagli della transazione');
            }
            return response.json();
        })
        .then(data => {
            if(transactionTipo === "Bollettino"){
                tipoBol.textContent = 'Tipo bollettino: ' + data[3];
                importo.textContent = 'Importo: ' + data[0];
                causale.textContent = 'Causale: ' + data[1];
                labelDest.textContent = 'Conto destinatario: '
                numCcDest.textContent = data[2];
                dataEs.textContent = 'Data: ' + data[4];
                costo.textContent = 'Costo transazione: ' + data[5]
            }else if(transactionTipo === "BonificoInter"){
                nomeBeneficiario.textContent = 'Nome beneficiario: ' + data[0]
                cognomeBeneficiario.textContent = 'Cognome beneficiario: ' + data[1]
                importo.textContent = 'Importo: ' + data[2];
                causale.textContent = 'Causale: ' + data[3];
                labelIban.textContent = 'IBAN destinatario: '
                ibanDest.textContent = data[4]
                valuta.textContent = 'Valuta: ' + data[5]
                paeseDest.textContent = 'Paese destinatario: ' + data[6]
                dataEs.textContent = 'Data: ' + data[7];
                costo.textContent = 'Costo transazione: ' + data[8]
            }else if(transactionTipo === "BonificoSepa"){
                nomeBeneficiario.textContent = 'Nome beneficiario: ' + data[0]
                cognomeBeneficiario.textContent = 'Cognome beneficiario: ' + data[1]
                importo.textContent = 'Importo: ' + data[2];
                causale.textContent = 'Causale: ' + data[3];
                labelIban.textContent = 'IBAN destinatario: '
                ibanDest.textContent = data[4]
                dataEs.textContent = 'Data: ' + data[5];
                costo.textContent = 'Costo transazione: ' + data[6]
            }

            // Mostra il popup
            document.getElementById('popup').style.display = 'block';
        })
        .catch(error => console.error(error));
}

function closePopupTrans() {
    // Nascondi il popup
    document.getElementById('popup').style.display = 'none';

    // Reimposta gli elementi dell'HTML allo stato predefinito
    var labelDest = document.querySelector('.labelCCDest');
    var labelIban = document.querySelector('.labelIban');
    var tipoBol = document.querySelector('.tipoBollettino');
    var numCcDest = document.querySelector('.numCcDest');
    var nomeBeneficiario = document.querySelector('.nomeBeneficiario');
    var cognomeBeneficiario = document.querySelector('.cognomeBeneficiario');
    var importo = document.querySelector('.importo');
    var causale = document.querySelector('.causale');
    var ibanDest = document.querySelector('.ibanDest');
    var valuta = document.querySelector('.valuta');
    var paeseDest = document.querySelector('.paeseDest');
    var costo = document.querySelector('.costoTransazione');
    var dataEs = document.querySelector('.data');

    tipoBol.textContent = '';
    numCcDest.textContent = '';
    nomeBeneficiario.textContent = '';
    cognomeBeneficiario.textContent = '';
    importo.textContent = '';
    causale.textContent = '';
    ibanDest.textContent = '';
    valuta.textContent = '';
    paeseDest.textContent = '';
    costo.textContent = '';
    dataEs.textContent = '';
    labelDest.textContent = '';
    labelIban.textContent = '';
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





