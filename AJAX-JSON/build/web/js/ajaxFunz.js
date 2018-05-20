//AJAX-JSON
var req;
var isIE;
var tblDati;

function cambia(codice) {
	//alert("OK");
	var url = "libriXGenere.jsp?codice=" + codice;
	req = initRequest();
 	//alert("terminata request");
 	req.open("GET", url, true);
        req.onreadystatechange = aggListaLibri;
	req.send(null);
}

function initRequest() {
        //alert("initRequest");
	if (window.XMLHttpRequest) {
		if (navigator.userAgent.indexOf('MSIE') != -1) {
			isIE = true;
		}
		return new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		isIE = true;
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
}

function appendLibro(titolo,  libroId) {

    var row;
    var cell;
    var linkElement;

    if (isIE) {
        tblDati.style.display = 'block';
        row = tblDati.insertRow(tblDati.rows.length);
        cell = row.insertCell(0);
    } else {
        tblDati.style.display = 'table';
        row = document.createElement("tr");
        cell = document.createElement("td");
        row.appendChild(cell);
        tblDati.appendChild(row);
    }

    cell.className = "popupCell";
    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.setAttribute("href", "doLibro.jsp?id=" + libroId);
    linkElement.appendChild(document.createTextNode(titolo));
    cell.appendChild(linkElement);
}

function getElementY(element) {

    var targetTop = 0;

    if (element.offsetParent) {
        while (element.offsetParent) {
            targetTop += element.offsetTop;
            element = element.offsetParent;
        }
    } else if (element.y) {
        targetTop += element.y;
    }
    return targetTop;
}

function clearTable() {
    if (tblDati.getElementsByTagName("tr").length > 0) {
        tblDati.style.display = 'none';
        for (loop = tblDati.childNodes.length - 1; loop >= 0; loop--) {
            tblDati.removeChild(tblDati.childNodes[loop]);
        }
    }
}



function aggListaLibri() {
    //alert("entro in aggiornaListaLibri");
    tblDati =  document.getElementById("tblLibri");
    clearTable();

    if (req.readyState == 4) {
        if (req.status == 200) {
           //alert(req.responseText.trim());
           caricaTable(req.responseText.trim());
        }
    }


}

function caricaTable(response) {
    // no matches returned
    if (response == "VUOTO" ) {
        alert("vuoto");
        return false;
    } else {
//        alert (response);
        var dati = JSON.parse(response);

        var libri = dati.libri;
//        alert(JSON.stringify(libri));
        if (libri.length > 0) {
//            alert("in LIbri");
            tblDati.setAttribute("bordercolor", "black");
            tblDati.setAttribute("border", "1");

            for (loop = 0; loop < libri.length; loop++) {
                var titolo = libri[loop].titolo;
                var libroId = libri[loop].id;
                appendLibro(titolo, libroId);
            }
        } else {
            alert("NESSUN Libro");
        }
    }
}






