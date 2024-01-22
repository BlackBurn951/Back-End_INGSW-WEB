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
        cambiaEmailPass(nuovaPass)
    }
}



function cambiaEmailPass(stringa) {
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    url = `/cambiaPass?IDSession=${idSession}&password=${stringa}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            messaggioConferma = data ? "Password modificata con successo." : "Errore nel cambio della password.";
            showPopup(messaggioConferma);
        })
        .catch(() => {
            showPopup("Errore durante l'operazione.");
        });
}

function validateEmail() {
    var errorMessage = document.getElementById('errorEmailMessage');
    var emailInput = document.getElementById("nuovaEmail").value;
    console.log("Valore dell'email:", emailInput);

    var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;


    if (emailRegex.test(emailInput)) {
        closePopupFunction();
        cambiaEmail(emailInput);

    } else {
        errorMessage.style.display = 'block';
    }

}



function cambiaEmail(stringaEmail){
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    url = `/changeEmail?IDSession=${idSession}`;

    const CambioEmail ={
        email: stringaEmail
    }

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(CambioEmail)
    })
        .then(response => response.json())
        .then(data => {
            messaggioConferma = data ? "Email modificata con successo." : "Errore nel cambio dell'email.";
            showPopup(messaggioConferma);
        })
        .catch(() => {
            showPopup("Errore durante l'operazione.");
        });

}

function sospendiChiudi() {
    var popup = document.getElementById('popupSospensione');
    var action = popup.getAttribute('data-action');
    var passwordInput = document.getElementById("passwordSosp").value;

    var errorPasswordMessage = document.getElementById('errorPassword');

    var isPasswordValid = controllaPassword(passwordInput);

    if (!isPasswordValid) {
        errorPasswordMessage.style.display = 'block';
    } else {
        errorPasswordMessage.style.display = 'none';
        if (action === 'sospendi') {
            cambiaStato(1)
        } else if (action === 'chiudi') {
            cambiaStato(2)
        }
        popup.style.display = 'none';
    }
}

function controllaPassword(password) {
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    url = `/checkPass?IDSession=${idSession}&password=${password}`;

    return fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            return data;
        })
        .catch(() => {
            throw new Error("Errore durante l'operazione.");
        });
}


function cambiaStato(valore) {
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    url = `/cambiaStatoConto?IDSession=${idSession}&stato=${valore}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            if (data === false) {
                messaggioConferma = "Stato del conto cambiato con successo.";
            } else {
                messaggioConferma = "Conto chiuso con successo.";
                setTimeout(() => tornaHomepage(), 2000); // Ritarda di 2 secondi
            }
            showPopup(messaggioConferma);
        })
        .catch(() => {
            showPopup("Errore durante l'operazione.");
        });
}

function tornaHomepage() {
    fetch('/redirect/tornaHomepage', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
        } else {
            console.error('Errore nella richiesta per tornare alla homepage');
        }
    })
    .catch(error => {
        console.error('Errore durante la richiesta per tornare alla homepage:', error);
    });
}






function toggleNuovaPass() {
    var nuovaPassInput = document.getElementById("nuovaPass");
    var nuovaPassIcon = document.getElementById("nuovaPassIcon");

    if (nuovaPassInput.type === "password") {
        nuovaPassInput.type = "text";
        nuovaPassIcon.src = "/images/view.png";
    } else {
        nuovaPassInput.type = "password";
        nuovaPassIcon.src = "/images/hide.png";
    }
}

function confermaToggleNuovaPass() {
    var nuovaPassInput = document.getElementById("passwordSosp");
    var nuovaPassIcon = document.getElementById("confermaPassIcon");

    if (nuovaPassInput.type === "password") {
        nuovaPassInput.type = "text";
        nuovaPassIcon.src = "/images/view.png";
    } else {
        nuovaPassInput.type = "password";
        nuovaPassIcon.src = "/images/hide.png";
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





