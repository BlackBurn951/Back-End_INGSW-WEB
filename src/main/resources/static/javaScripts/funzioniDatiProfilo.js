document.addEventListener("DOMContentLoaded", function() {

    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");

    const linkElement = document.querySelector("#buttonGoBack");
    const linkHref = linkElement.getAttribute("href");
    linkElement.setAttribute("href", linkHref + '?IDSession='+idSession);

});

function openPopup() {
    document.getElementById('popup').style.display = 'block';
}

function openPassPopup() {
    document.getElementById('popupPasword').style.display = 'block';
}

function openSosPopup(id, action) {
    const popup = document.getElementById('popupSospensione');
    const label = document.querySelector('#popupSospensione .labelPop');

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

    const emailInput = document.getElementById('nuovaEmail');
    const nuovPassInput = document.getElementById('confPass');
    const confPassInput = document.getElementById('nuovaPass');
    const errorMessage = document.getElementById('errorEmailMessage');
    const errorPass = document.getElementById('errorPassword');
    const passSops = document.getElementById('passwordSosp');
    const nuovaPassError = document.getElementById("nuovaPassError");
    const confPassError = document.getElementById("confPassError");

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
    const nuovaPass = document.getElementById("nuovaPass").value;
    const confPass = document.getElementById("confPass").value;
    const nuovaPassError = document.getElementById("nuovaPassError");
    const confPassError = document.getElementById("confPassError");

    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{8,}$/;

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
    let url = `/cambiaPass?IDSession=${idSession}&password=${stringa}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            let messaggioConferma = data ? "Password modificata con successo." : "Errore nel cambio della password.";
            showPopup(messaggioConferma);
        })
        .catch(() => {
            showPopup("Errore durante l'operazione.");
        });
}

function validateEmail() {
    const errorMessage = document.getElementById('errorEmailMessage');
    const emailInput = document.getElementById("nuovaEmail").value;

    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;


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
    let url = `/changeEmail?IDSession=${idSession}`;

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
            let messaggioConferma = data ? "Email modificata con successo." : "Errore nel cambio dell'email.";
            showPopup(messaggioConferma);
        })
        .catch(() => {
            showPopup("Errore durante l'operazione.");
        });

}


function controllaPassword() {
    const passwordInput = document.getElementById("passwordSosp").value;

    const popup = document.getElementById('popupSospensione');
    const action = popup.getAttribute('data-action');

    const errorPasswordMessage = document.getElementById('errorPassword');

    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    let url = `/checkPass?IDSession=${idSession}&password=${passwordInput}`;

    return fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            if(data) {
                errorPasswordMessage.style.display = 'none';
                if (action === 'sospendi') {
                    cambiaStato(1);
                } else if (action === 'chiudi') {
                    cambiaStato(2);
                }
                popup.style.display = 'none';
            } else {
                errorPasswordMessage.style.display = 'block';

            }
        })
        .catch(() => {
            throw new Error("Errore durante l'operazione.");
        });
}


function cambiaStato(valore) {
    const urlParams = new URLSearchParams(window.location.search);
    const idSession = urlParams.get("IDSession");
    let url = `/cambiaStatoConto?IDSession=${idSession}&stato=${valore}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            let messaggioConferma;
            if (data === false) {
                messaggioConferma = "Stato del conto cambiato con successo.";
            } else {
                messaggioConferma = "Conto chiuso con successo.";
                setTimeout(function () {
                    window.location.href = "http://localhost:4200";
                }, 2000);
            }
            showPopup(messaggioConferma);
        })
        .catch(() => {
            showPopup("Errore durante l'operazione.");
        });
}


function toggleNuovaPass() {
    const nuovaPassInput = document.getElementById("nuovaPass");
    const nuovaPassIcon = document.getElementById("nuovaPassIcon");

    if (nuovaPassInput.type === "password") {
        nuovaPassInput.type = "text";
        nuovaPassIcon.src = "/images/view.png";
    } else {
        nuovaPassInput.type = "password";
        nuovaPassIcon.src = "/images/hide.png";
    }
}

function confermaToggleNuovaPass() {
    const nuovaPassInput = document.getElementById("passwordSosp");
    const nuovaPassIcon = document.getElementById("confermaPassIcon");

    if (nuovaPassInput.type === "password") {
        nuovaPassInput.type = "text";
        nuovaPassIcon.src = "/images/view.png";
    } else {
        nuovaPassInput.type = "password";
        nuovaPassIcon.src = "/images/hide.png";
    }
}

function toggleConfPass() {
    const confPassInput = document.getElementById("confPass");
    const confPassIcon = document.getElementById("confPassIcon");

    if (confPassInput.type === "password") {
        confPassInput.type = "text";
        confPassIcon.src = "/images/view.png";
    } else {
        confPassInput.type = "password";
        confPassIcon.src = "/images/hide.png";
    }
}

function showPopup(message) {
    document.getElementById('popup-message').innerHTML = message;
    document.getElementById('popupConferma').style.display = 'block';
}

function closePopup() {
    const passwordInput = document.getElementById("passwordSosp");
    passwordInput.value = ''

    document.getElementById('popupConferma').style.display = 'none';
}





