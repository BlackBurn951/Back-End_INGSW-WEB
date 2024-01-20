document.addEventListener("DOMContentLoaded", function() {

    var urlParams = new URLSearchParams(window.location.search);
    var idSession = urlParams.get("IDSession");

    var linkElement = document.querySelector("#buttonGoBack");
    var linkHref = linkElement.getAttribute("href");
    linkElement.setAttribute("href", linkHref + '?IDSession='+idSession);

});

function openPopup() {
    document.getElementById('popup').style.display = 'block';
}

function openPassPopup() {
    document.getElementById('popupPasword').style.display = 'block';
}

function openSosPopup(id, action) {
    var popup = document.getElementById('popupSospensione');
    var label = document.querySelector('#popupSospensione .labelPop');

    if (id === 1) {
        label.textContent = "Sei sicuro di voler sospendere il conto?";
    } else if (id === 2) {
        label.textContent = "Sei sicuro di voler chiudere il conto?";
    }
    popup.setAttribute('data-action', action);

    popup.style.display = 'block';
}



function closePopupFunction() {
    document.getElementById('popup').style.display = 'none';
    document.getElementById('popupPasword').style.display ='none';
    document.getElementById('popupSospensione').style.display = 'none';

    var emailInput = document.getElementById('nuovaEmail');
    var nuovPassInput = document.getElementById('confPass');
    var confPassInput = document.getElementById('nuovaPass');
    var errorMessage = document.getElementById('errorEmailMessage');
    var errorPass = document.getElementById('errorPassword');
    var passSops = document.getElementById('passwordSosp');
    var nuovaPassError = document.getElementById("nuovaPassError");
    var confPassError = document.getElementById("confPassError");

    emailInput.value = '';
    nuovPassInput.value = '';
    confPassInput.value = '';
    passSops.value = '';

    errorPass.style.display = 'none';
    errorMessage.style.display = 'none';
    nuovaPassError.style.display = 'none';
    confPassError.style.display = 'none';
}


function validateEmail() {
    var emailInput = document.getElementById('nuovaEmail');
    var errorMessage = document.getElementById('errorEmailMessage');

    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (emailRegex.test(emailInput.value)) {
        closePopupFunction()
        confermaOperazione('email')

    } else {
        errorMessage.style.display = 'block';
    }
}

function validatePassword() {
    var nuovaPass = document.getElementById("nuovaPass").value;
    var confPass = document.getElementById("confPass").value;
    var nuovaPassError = document.getElementById("nuovaPassError");
    var confPassError = document.getElementById("confPassError");

    var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{8,}$/;

    nuovaPassError.innerHTML = "";
    confPassError.innerHTML = "";

    if (!nuovaPass.match(passwordPattern)) {
        nuovaPassError.style.display = ''
        nuovaPassError.innerHTML = "La password deve contenere:<br>\n" +
            "              - Almeno 8 caratteri (massimo 16);<br>\n" +
            "              - Almeno una lettera maiuscola ed una minuscola;<br>\n" +
            "              - Almeno un numero;<br>\n" +
            "              - Almeno un carattere speciale fra i seguenti (!&#64;$#%^&*()_+).";
}

    if (nuovaPass !== confPass) {
        confPassError.style.display = ''
        confPassError.innerHTML = "Le password non coincidono!";
}

    if (nuovaPass.match(passwordPattern) && nuovaPass === confPass) {
        closePopupFunction()
        showPopup('Aidi');

    }

}

function sospendiChiudi() {
    var popup = document.getElementById('popupSospensione');
    var action = popup.getAttribute('data-action');
    var passwordInput = document.getElementById('passwordSosp');
    var errorPasswordMessage = document.getElementById('errorPassword');

    // Aggiungi la logica per la validazione della password
    var isPasswordValid = controllaPassword(passwordInput.value);

    if (!isPasswordValid) {
        // Mostra il messaggio di errore sotto il campo password
        errorPasswordMessage.style.display = 'block';
    } else {
        // Nascondi il messaggio di errore se la password è valida
        errorPasswordMessage.style.display = 'none';

        // Esegui l'azione appropriata in base all'attributo data-action
        if (action === 'sospendi') {
            // Logica per sospendere il conto
            console.log("Il conto è sospeso.");
        } else if (action === 'chiudi') {
            // Logica per chiudere il conto
            console.log("Il conto è chiuso.");
        }

        // Chiudi il popup
        popup.style.display = 'none';
    }
}

// Funzione di validazione della password
function controllaPassword(password) {
    // Aggiungi la tua logica di validazione della password qui
    // Ad esempio, verifica se la lunghezza della password è sufficiente, ecc.
    return password.length >= 8;  // Esempio: richiede una password di almeno 8 caratteri
}


function toggleNuovaPass() {
    var nuovaPassInput = document.getElementById("nuovaPass");
    var nuovaPassIcon = document.getElementById("nuovaPassIcon");

    if (nuovaPassInput.type === "password") {
        nuovaPassInput.type = "text";
        nuovaPassIcon.src = "/images/view.png"; // Sostituisci con l'immagine dell'icona visibile
    } else {
        nuovaPassInput.type = "password";
        nuovaPassIcon.src = "/images/hide.png"; // Sostituisci con l'immagine dell'icona nascosta
    }
}

function confermaToggleNuovaPass() {
    var nuovaPassInput = document.getElementById("passwordSosp");
    var nuovaPassIcon = document.getElementById("confermaPassIcon");

    if (nuovaPassInput.type === "password") {
        nuovaPassInput.type = "text";
        nuovaPassIcon.src = "/images/view.png"; // Sostituisci con l'immagine dell'icona visibile
    } else {
        nuovaPassInput.type = "password";
        nuovaPassIcon.src = "/images/hide.png"; // Sostituisci con l'immagine dell'icona nascosta
    }
}

function toggleConfPass() {
    var confPassInput = document.getElementById("confPass");
    var confPassIcon = document.getElementById("confPassIcon");

    if (confPassInput.type === "password") {
        confPassInput.type = "text";
        confPassIcon.src = "/images/view.png"; // Sostituisci con l'immagine dell'icona visibile
    } else {
        confPassInput.type = "password";
        confPassIcon.src = "/images/hide.png"; // Sostituisci con l'immagine dell'icona nascosta
    }
}







    function showPopup(message) {
        document.getElementById('popup-message').innerHTML = message;
        document.getElementById('popupConferma').style.display = 'block';
    }

    function closePopup() {
        document.getElementById('popupConferma').style.display = 'none';
    }


    function confermaOperazione(tipoModifica) {

        var url = "";  // Inizializza l'URL corretto per la tua operazione

        // Determina l'URL in base al tipo di modifica
        if (tipoModifica === 'email') {
            url = '/modificaEmail';
        } else if (tipoModifica === 'password') {
            url = '/modificaPassword';
        } else if (tipoModifica === 'sospendi') {
            url = '/sospendiConto';
        } else if (tipoModifica === 'chiudi') {
            url = '/chiudiConto';
        }

    // Esempio di chiamata AJAX a Spring Boot (assumendo l'utilizzo di jQuery)
    $.ajax({
        url: url,
        type: 'POST',
        data: { tipoModifica: tipoModifica },
        success: function (risposta) {
            // Risposta ottenuta dal server
            var messaggioConferma = "Operazione riuscita.";

            if (tipoModifica === 'email') {
                messaggioConferma = "Email modificata con successo.";
            } else if (tipoModifica === 'password') {
                messaggioConferma = "Password modificata con successo.";
            } else if (tipoModifica === 'sospendi') {
                messaggioConferma = "Conto sospeso con successo.";
            } else if (tipoModifica === 'chiudi') {
                messaggioConferma = "Conto chiuso con successo.";
            }

            // Mostro il popup con il messaggio personalizzato
            showPopup(messaggioConferma);
        },
        error: function () {
            // Gestisci eventuali errori
            showPopup("Errore durante l'operazione.");
        }
    });




}
