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


/*
function openPopup(button) {
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    var transactionId = button.getAttribute('data-transaction-id');
    var transactionTipo = button.getAttribute('data-transaction-tipo');



    url = `/visualizzaTrans?IDSession=${idSession}&idTrans=${transactionId}&tipoTrans=${transactionTipo}`;

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

    var popupContent = document.querySelector('.contenutoPopup');


    var labelIban = document.querySelector('.labelIban');
    var labelDest = document.querySelector('.labelCCDest');

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
                importo.textContent = 'Importo: ' + data[0]+' €';
                causale.textContent = 'Causale: ' + data[1];
                labelDest.textContent = 'Conto destinatario: '
                numCcDest.textContent = data[2];
                dataEs.textContent = 'Data: ' + data[4];
                costo.textContent = 'Costo transazione: ' + data[5]

            }else if(transactionTipo === "BonificoInter"){
                nomeBeneficiario.textContent = 'Nome beneficiario: ' + data[0]
                cognomeBeneficiario.textContent = 'Cognome beneficiario: ' + data[1]
                importo.textContent = 'Importo: ' + data[2]+' €';
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
                importo.textContent = 'Importo: ' + data[2]+' €';
                causale.textContent = 'Causale: ' + data[3];
                labelIban.textContent = 'IBAN destinatario: '
                ibanDest.textContent = data[4]
                dataEs.textContent = 'Data: ' + data[5];
                costo.textContent = 'Costo transazione: ' + data[6]
            }

            document.getElementById('popup').style.display = 'block';
        })
        .catch(error => console.error(error));
}

*/
function openPopup(button) {
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    var transactionId = button.getAttribute('data-transaction-id');
    var transactionTipo = button.getAttribute('data-transaction-tipo');

    url = `/visualizzaTrans?IDSession=${idSession}&idTrans=${transactionId}&tipoTrans=${transactionTipo}`;

    var popupContent = document.querySelector('.contenutoPopup');

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore durante il recupero dei dettagli della transazione');
            }
            return response.json();
        })
        .then(data => {
            displayTransactionDetails(transactionTipo, data);

            document.getElementById('popup').style.display = 'block';
        })
        .catch(error => console.error(error));
}

function displayTransactionDetails(transactionTipo, data) {
    var popupContent = document.querySelector('.contenutoPopup');
    popupContent.innerHTML = '';

    if (transactionTipo === "Bollettino") {
        displayBollettino(data);
    } else if (transactionTipo === "BonificoInter") {
        displayBonificoInter(data);
    } else if (transactionTipo === "BonificoSepa") {
        displayBonificoSepa(data);
    }
}

function displayBollettino(data) {
    var popupContent = document.querySelector('.contenutoPopup');

    var closeButton = document.createElement('button');
    closeButton.className = 'popup-btn';
    closeButton.textContent = 'X';
    closeButton.onclick = function () {
        closePopupTrans();
    };
    popupContent.appendChild(closeButton);

    // Funzione per creare un elemento <p> con testo in grassetto
    function createBoldParagraph(label, content) {
        var element = document.createElement('p');
        element.innerHTML = '<strong>' + label + '</strong>: ' + content;
        popupContent.appendChild(element);
    }

    createBoldParagraph('Tipo bollettino', data[3]);
    createBoldParagraph('Conto destinatario', data[2]);
    createBoldParagraph('Importo', data[0] + ' €');
    createBoldParagraph('Causale', data[1]);
    createBoldParagraph('Data', data[4]);
    createBoldParagraph('Costo transazione', data[5]);
}

function displayBonificoInter(data) {
    var popupContent = document.querySelector('.contenutoPopup');

    var closeButton = document.createElement('button');
    closeButton.className = 'popup-btn';
    closeButton.textContent = 'X';
    closeButton.onclick = function () {
        closePopupTrans();
    };
    popupContent.appendChild(closeButton);

    function createBoldParagraph(label, content) {
        var element = document.createElement('p');
        element.innerHTML = '<strong>' + label + '</strong>: ' + content;
        popupContent.appendChild(element);
    }

    createBoldParagraph('Nome beneficiario', data[0]);
    createBoldParagraph('Cognome beneficiario', data[1]);
    createBoldParagraph('Importo', data[2] + ' €');
    createBoldParagraph('Causale', data[3]);
    createBoldParagraph('IBAN destinatario', '');
    var element = document.createElement('p');
    element.innerHTML = data[4];
    popupContent.appendChild(element);
    createBoldParagraph('Valuta', data[5]);
    createBoldParagraph('Paese destinatario', data[6]);
    createBoldParagraph('Data', data[7]);
    createBoldParagraph('Costo transazione', data[8]);
}

function displayBonificoSepa(data) {
    var popupContent = document.querySelector('.contenutoPopup');

    var closeButton = document.createElement('button');
    closeButton.className = 'popup-btn';
    closeButton.textContent = 'X';
    closeButton.onclick = function () {
        closePopupTrans();
    };
    popupContent.appendChild(closeButton);

    function createBoldParagraph(label, content) {
        var element = document.createElement('p');
        element.innerHTML = '<strong>' + label + '</strong>: ' + content;
        popupContent.appendChild(element);
    }

    createBoldParagraph('Nome beneficiario', data[0]);
    createBoldParagraph('Cognome beneficiario', data[1]);
    createBoldParagraph('Importo', data[2] + ' €');
    createBoldParagraph('Causale', data[3]);
    createBoldParagraph('IBAN destinatario', '');
    var element = document.createElement('p');
    element.innerHTML = data[4];
    popupContent.appendChild(element);
    createBoldParagraph('Data', data[5]);
    createBoldParagraph('Costo transazione', data[6]);
}


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





