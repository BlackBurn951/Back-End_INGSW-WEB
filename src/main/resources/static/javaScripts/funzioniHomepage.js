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
    var popup = document.getElementById("popup");

    var costoTransazioneElement = popup.querySelector(".costoTransazione");
    var nomeBeneficiarioElement = popup.querySelector(".nomeBeneficiario");

    var costo = button.getAttribute("data-costo");
    var nomeBeneficiario = button.getAttribute("data-nome-beneficiario");

    costoTransazioneElement.innerText = costo;
    nomeBeneficiarioElement.innerText = nomeBeneficiario;

    popup.style.display = "block";
}




function closePopupTrans() {
    var popup = document.getElementById("popup");
    popup.style.display = "none";
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





