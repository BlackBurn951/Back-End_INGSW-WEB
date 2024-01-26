document.addEventListener("DOMContentLoaded", function() {
    modifyLinks();

    window.addEventListener('click', function(event) {
        const centroNotifiche = document.querySelector('.centroNotifiche');
        const notificheMenu = centroNotifiche.querySelector('.notificheMenu');
        const badge = centroNotifiche.querySelector('.badge');

        if (notificheMenu && !centroNotifiche.contains(event.target)) {
            notificheMenu.classList.remove('show');
        }


    });

});



function modifyLinks() {
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    const links = document.querySelectorAll('[id^="redirect"]');
    links.forEach(function(link) {
        const linkHref = link.getAttribute("href");
        link.setAttribute("href", linkHref + '?IDSession=' + idSession);
    });
}



function openPopup(button) {
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    const transactionId = button.getAttribute('data-transaction-id');
    const transactionTipo = button.getAttribute('data-transaction-tipo');

    let url = `/visualizzaTrans?IDSession=${idSession}&idTrans=${transactionId}&tipoTrans=${transactionTipo}`;

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
    const popupContent = document.querySelector('.contenutoPopup');
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
    const popupContent = document.querySelector('.contenutoPopup');

    const closeButton = document.createElement('button');
    closeButton.className = 'popup-btn';
    closeButton.textContent = 'X';
    closeButton.onclick = function () {
        closePopupTrans();
    };
    popupContent.appendChild(closeButton);

    function createBoldParagraph(label, content) {
        const element = document.createElement('p');
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
    const popupContent = document.querySelector('.contenutoPopup');

    const closeButton = document.createElement('button');
    closeButton.className = 'popup-btn';
    closeButton.textContent = 'X';
    closeButton.onclick = function () {
        closePopupTrans();
    };
    popupContent.appendChild(closeButton);

    function createBoldParagraph(label, content) {
        const element = document.createElement('p');
        element.innerHTML = '<strong>' + label + '</strong>: ' + content;
        popupContent.appendChild(element);
    }
    createBoldParagraph('Tipo bonifico', 'Internazionale');
    createBoldParagraph('Nome beneficiario', data[0]);
    createBoldParagraph('Cognome beneficiario', data[1]);
    createBoldParagraph('Importo', data[2] + ' €');
    createBoldParagraph('Causale', data[3]);
    createBoldParagraph('IBAN destinatario', '');
    const element = document.createElement('p');
    element.innerHTML = data[4];
    popupContent.appendChild(element);
    createBoldParagraph('Valuta', data[5]);
    createBoldParagraph('Paese destinatario', data[6]);
    createBoldParagraph('Data', data[7]);
    createBoldParagraph('Costo transazione', data[8]);
}

function displayBonificoSepa(data) {
    const popupContent = document.querySelector('.contenutoPopup');

    const closeButton = document.createElement('button');
    closeButton.className = 'popup-btn';
    closeButton.textContent = 'X';
    closeButton.onclick = function () {
        closePopupTrans();
    };
    popupContent.appendChild(closeButton);

    function createBoldParagraph(label, content) {
        const element = document.createElement('p');
        element.innerHTML = '<strong>' + label + '</strong>: ' + content;
        popupContent.appendChild(element);
    }
    createBoldParagraph('Tipo bonifico', 'Area SEPA');
    createBoldParagraph('Nome beneficiario', data[0]);
    createBoldParagraph('Cognome beneficiario', data[1]);
    createBoldParagraph('Importo', data[2] + ' €');
    createBoldParagraph('Causale', data[3]);
    createBoldParagraph('IBAN destinatario', '');
    const element = document.createElement('p');
    element.innerHTML = data[4];
    popupContent.appendChild(element);
    createBoldParagraph('Data', data[5]);
    createBoldParagraph('Costo transazione', data[6]);
}


function closePopupTrans() {
    document.getElementById('popup').style.display = 'none';
}





function toggleNotifiche() {

    const centroNotifiche = document.querySelector('.centroNotifiche');
    const notificheMenu = centroNotifiche.querySelector('.notificheMenu');
    const badge = centroNotifiche.querySelector('.badge');


    if (notificheMenu) {
        notificheMenu.classList.toggle('show');
    }

    if (badge && badge.style.display !== 'none') {
        badge.style.display = 'none';
    }
}




function eliminaNotifica(element) {
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    const idNotifica = element.getAttribute('data-notifica-id');

    let url = `/eliminaNotifica?IDSession=${idSession}&idNotifica=${idNotifica}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore durante l\'eliminazione della notifica');
            }
            return response.json();
        })
        .then(data => {
            if (data === true) {
                element.parentNode.remove();
            } else {
            }
        })
        .catch(error => console.error(error));
}
























function searchTransactions() {
    var input = document.getElementById("searchInput");
    var filter = input.value.toUpperCase();

    var table = document.getElementById("transazioniTable");
    var tbody = table.getElementsByTagName("tbody")[0];

    var rows = tbody.getElementsByTagName("tr");

    for (var i = 0; i < rows.length; i++) {
        var transactionData = rows[i].getElementsByTagName("td");
        var shouldDisplay = false;

        for (var j = 0; j < transactionData.length; j++) {
            var data = transactionData[j].textContent || transactionData[j].innerText;
            if (data.toUpperCase().indexOf(filter) > -1) {
                shouldDisplay = true;
                break;
            }
        }
        if (shouldDisplay) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const transactionTipo = urlParams.get("tipoTrans");

    if (transactionTipo) {
        sessionStorage.setItem('selectedTransactionType', transactionTipo);
    }
});


function filterTransactions() {
    var select = document.getElementById("filterSelect");
    var filterValue = select.value.toUpperCase();

    var table = document.getElementById("transazioniTable");
    var tbody = table.getElementsByTagName("tbody")[0];

    var rows = tbody.getElementsByTagName("tr");

    for (var i = 0; i < rows.length; i++) {
        var transactionType = rows[i].getElementsByTagName("td")[0].textContent.trim().toUpperCase();

        if (filterValue === "ALL" || transactionType === filterValue) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
}



