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





function toggleNotifiche() {
    var centroNotifiche = document.querySelector('.centroNotifiche');
    var notificheMenu = centroNotifiche.querySelector('.notificheMenu');
    var badge = centroNotifiche.querySelector('.badge');

    if (notificheMenu) {
        notificheMenu.classList.toggle('show');
    }

    if (badge && badge.style.display !== 'none') {
        badge.style.display = 'none';
    }
}






function eliminaNotifica(element) {

}






