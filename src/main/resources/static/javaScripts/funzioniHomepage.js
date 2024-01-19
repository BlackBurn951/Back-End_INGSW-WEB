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


function mostraNascondiDettagli(elementId) {
    var element = document.getElementById(elementId);
    var popup = document.getElementById("popup");

    if (element.style.display === "none" || element.style.display === "") {
        element.style.display = "block";
        popup.style.display = "block";
    } else {
        element.style.display = "none";
        popup.style.display = "none";
    }
}









function toggleNotifiche() {
    var notificheMenu = document.getElementById("notificheMenu");
    notificheMenu.style.display = (notificheMenu.style.display === "block") ? "none" : "block";
}

function eliminaNotifica(notificaId) {
    // Aggiungi qui la logica per eliminare la notifica con l'ID specificato
    // Puoi fare una chiamata AJAX al backend per gestire l'eliminazione
    alert("Notifica eliminata: " + notificaId);
}





