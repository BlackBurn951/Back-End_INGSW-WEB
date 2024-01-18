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